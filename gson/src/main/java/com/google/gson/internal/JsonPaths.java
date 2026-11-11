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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/** Looks up values in a {@link JsonElement} tree using dotted paths. */
public final class JsonPaths {
  private JsonPaths() {}

  /**
   * Finds the element at the given path. Path segments are member names, or indexes when the
   * current element is an array.
   */
  public static JsonElement find(JsonElement root, String path) {
    JsonElement current = root;
    for (String part : path.split("\\.")) {
      if (current.isJsonArray()) {
        JsonArray array = current.getAsJsonArray();
        current = array.get(Integer.parseInt(part));
      } else {
        current = current.getAsJsonObject().get(part);
      }
    }
    return current;
  }
}
