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

package com.google.gson.internal;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;

public class JsonPathsTest {
  private static final JsonElement DOCUMENT =
      JsonParser.parseString("{\"a\":{\"b\":[10,20,{\"c\":\"x\"}]},\"n\":null}");

  @Test
  public void testFind() {
    assertThat(JsonPaths.find(DOCUMENT, "a.b.1").getAsInt()).isEqualTo(20);
    assertThat(JsonPaths.find(DOCUMENT, "a.b.2.c").getAsString()).isEqualTo("x");
  }

  @Test
  public void testAsInt() {
    assertThat(JsonPaths.asInt(DOCUMENT, "a.b.0")).isEqualTo(10);
  }

  @Test
  public void testAsStringOfNull() {
    assertThat(JsonPaths.asString(DOCUMENT, "n")).isNull();
  }

  @Test
  public void testIndexOutOfRange() {
    assertThrows(IndexOutOfBoundsException.class, () -> JsonPaths.find(DOCUMENT, "a.b.7"));
  }
}
