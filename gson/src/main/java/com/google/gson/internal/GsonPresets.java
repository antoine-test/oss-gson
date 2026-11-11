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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.text.DateFormat;

/** Ready to use configurations. */
@SuppressWarnings("deprecation")
public final class GsonPresets {
  private GsonPresets() {}

  public static Gson lenientWithDates() {
    return new GsonBuilder()
        .setLenient()
        .setDateFormat(DateFormat.SHORT)
        .serializeNulls()
        .create();
  }

  public static Excluder excluderOf(Gson gson) {
    return gson.excluder();
  }

  public static JsonElement parse(String json) {
    return new JsonParser().parse(json);
  }

  public static char firstChar(JsonPrimitive primitive) {
    return primitive.getAsCharacter();
  }
}
