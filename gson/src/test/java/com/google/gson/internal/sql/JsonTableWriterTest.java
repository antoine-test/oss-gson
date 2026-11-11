/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gson.internal.sql;

import static com.google.common.truth.Truth.assertThat;

import com.google.gson.JsonObject;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class JsonTableWriterTest {
  private static Connection recordingConnection(List<String> statements) {
    Statement statement =
        (Statement)
            Proxy.newProxyInstance(
                JsonTableWriterTest.class.getClassLoader(),
                new Class<?>[] {Statement.class},
                (proxy, method, args) -> {
                  if (method.getName().equals("executeUpdate")) {
                    statements.add((String) args[0]);
                    return 1;
                  }
                  return null;
                });
    return (Connection)
        Proxy.newProxyInstance(
            JsonTableWriterTest.class.getClassLoader(),
            new Class<?>[] {Connection.class},
            (proxy, method, args) -> statement);
  }

  @Test
  public void testInsert() throws SQLException {
    List<String> statements = new ArrayList<>();
    JsonTableWriter writer = new JsonTableWriter(recordingConnection(statements));
    JsonObject row = new JsonObject();
    row.addProperty("id", 7);
    row.addProperty("name", "gson");

    assertThat(writer.insert("libs", row)).isEqualTo(1);
    assertThat(statements).containsExactly("INSERT INTO libs (id, name) VALUES ('7', 'gson')");
  }
}
