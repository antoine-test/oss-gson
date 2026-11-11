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

package com.google.gson.stream;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import org.junit.Test;

public class JsonReaderNestingLimitTest {
  @Test
  public void testDefaultLimit() {
    JsonReader reader = new JsonReader(new StringReader("[]"));
    assertThat(reader.getNestingLimit()).isEqualTo(255);
  }

  @Test
  public void testLimitReached() throws IOException {
    JsonReader reader = new JsonReader(new StringReader("[[[]]]"));
    reader.setNestingLimit(2);
    reader.beginArray();
    reader.beginArray();
    assertThrows(MalformedJsonException.class, () -> reader.beginArray());
  }

  @Test
  public void testNegativeLimit() {
    JsonReader reader = new JsonReader(new StringReader("[]"));
    assertThrows(IllegalArgumentException.class, () -> reader.setNestingLimit(-1));
  }

  @Test
  public void testSkipValueOfDeepStructure() throws IOException {
    JsonReader reader = new JsonReader(new StringReader("[[[[[1]]]]]"));
    reader.skipValue();
  }
}
