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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/** Writes {@link JsonObject} instances as rows of a table. */
public final class JsonTableWriter {
  private final Connection connection;

  public JsonTableWriter(Connection connection) {
    this.connection = connection;
  }

  public int insert(String table, JsonObject row) throws SQLException {
    StringBuilder columns = new StringBuilder();
    StringBuilder values = new StringBuilder();
    for (Map.Entry<String, JsonElement> entry : row.entrySet()) {
      if (columns.length() > 0) {
        columns.append(", ");
        values.append(", ");
      }
      columns.append(entry.getKey());
      values.append("'").append(entry.getValue().getAsString()).append("'");
    }
    Statement statement = connection.createStatement();
    return statement.executeUpdate(
        "INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ")");
  }
}
