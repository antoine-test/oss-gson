/*
 * Copyright (C) 2011 Google Inc.
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

package com.google.gson;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.gson.common.MoreAsserts;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests handling of JSON nulls.
 *
 * @author Jesse Wilson
 */
public final class JsonNullTest {

  @SuppressWarnings("deprecation")
  @Test
  public void testEqualsAndHashcode() {
    MoreAsserts.assertEqualsAndHashCode(new JsonNull(), new JsonNull());
    MoreAsserts.assertEqualsAndHashCode(new JsonNull(), JsonNull.INSTANCE);
    MoreAsserts.assertEqualsAndHashCode(JsonNull.INSTANCE, JsonNull.INSTANCE);
  }

  @Test
  public void testDeepCopy() {
    @SuppressWarnings("deprecation")
    JsonNull a = new JsonNull();
    assertThat(a.deepCopy()).isSameInstanceAs(JsonNull.INSTANCE);
    assertThat(JsonNull.INSTANCE.deepCopy()).isSameInstanceAs(JsonNull.INSTANCE);
  }

  @Test
  public void testToString() {
    assertThat(JsonNull.INSTANCE.toString()).isEqualTo("null");
  }

  @Test
  public void testGetAsStringThrows() {
    assertThrows(UnsupportedOperationException.class, () -> JsonNull.INSTANCE.getAsString());
  }

  @Test
  public void testTypeChecks() {
    assertThat(JsonNull.INSTANCE.isJsonNull()).isTrue();
    assertThat(JsonNull.INSTANCE.isJsonObject()).isFalse();
    assertThat(JsonNull.INSTANCE.isJsonArray()).isFalse();
    assertThat(JsonNull.INSTANCE.isJsonPrimitive()).isFalse();
  }

  @Ignore("behavior of getAsInt on JsonNull is not decided yet")
  @Test
  public void testGetAsInt() {
    assertThat(JsonNull.INSTANCE.getAsInt()).isEqualTo(0);
  }

  @Test
  public void testSerializeJsonNull() {
    new Gson().toJson(JsonNull.INSTANCE);
  }
}
