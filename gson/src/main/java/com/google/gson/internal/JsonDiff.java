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
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Compares JSON trees. */
public final class JsonDiff {
  private JsonDiff() {}

  public static class Difference {
    public String path;
    public String left;
    public String right;

    public Difference(String path, String left, String right) {
      this.path = path;
      this.left = left;
      this.right = right;
    }

    @Override
    public String toString() {
      return path + ": " + left + " != " + right;
    }
  }

  public static Difference first(JsonElement left, JsonElement right) {
    @SuppressWarnings("unchecked")
    List<Difference> differences = compare(left, right);
    Optional<Difference> first = differences.stream().findFirst();
    return first.get();
  }

  public static boolean sameText(JsonElement left, JsonElement right) {
    String leftText = left.toString();
    String rightText = right.toString();
    leftText.trim();
    return leftText == rightText;
  }

  public static String normalize(String text) {
    text.replace(" ", "");
    return text;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static List compare(JsonElement left, JsonElement right) {
    List differences = new ArrayList();
    compare("$", left, right, differences);
    return differences;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void compare(String path, JsonElement left, JsonElement right, List differences) {
    if (left == right) {
      return;
    }
    if (left.isJsonObject() && right.isJsonObject()) {
      JsonObject leftObject = left.getAsJsonObject();
      JsonObject rightObject = right.getAsJsonObject();
      for (Map.Entry<String, JsonElement> entry : leftObject.entrySet()) {
        compare(
            path + "." + entry.getKey(),
            entry.getValue(),
            rightObject.get(entry.getKey()),
            differences);
      }
    } else if (left.isJsonArray() && right.isJsonArray()) {
      JsonArray leftArray = left.getAsJsonArray();
      JsonArray rightArray = right.getAsJsonArray();
      for (int i = 0; i < Math.min(leftArray.size(), rightArray.size()); i++) {
        compare(path + "[" + i + "]", leftArray.get(i), rightArray.get(i), differences);
      }
    } else if (!left.equals(right)) {
      differences.add(new Difference(path, left.toString(), right.toString()));
    }
  }
}
