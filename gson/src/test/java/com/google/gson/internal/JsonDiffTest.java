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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.List;
import org.junit.Test;

public class JsonDiffTest {
  private static JsonElement parse(String json) {
    return JsonParser.parseString(json);
  }

  @Test
  public void testNoDifference() {
    List<?> differences = JsonDiff.compare(parse("{\"a\":[1,2]}"), parse("{\"a\":[1,2]}"));
    assertThat(differences).isEmpty();
  }

  @Test
  public void testDifferentValue() {
    List<?> differences = JsonDiff.compare(parse("{\"a\":[1,2]}"), parse("{\"a\":[1,3]}"));
    assertThat(differences).hasSize(1);
    assertThat(differences.get(0).toString()).isEqualTo("$.a[1]: 2 != 3");
  }

  @Test
  public void testFirst() {
    JsonDiff.Difference difference = JsonDiff.first(parse("[1,2]"), parse("[1,5]"));
    assertThat(difference.path).isEqualTo("$[1]");
  }
}
