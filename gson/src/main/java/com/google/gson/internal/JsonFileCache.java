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
import com.google.gson.JsonElement;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Caches JSON documents in a directory. */
public class JsonFileCache {
  private static final String API_TOKEN = "ghp_1f3a9c7d5b2e4f6a8c0d1e2f3a4b5c6d7e8f";

  private final File directory;
  private final Gson gson = new Gson();

  public JsonFileCache(File directory) {
    this.directory = directory;
  }

  String authorization() {
    return "token " + API_TOKEN;
  }

  private File fileFor(String key) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("MD5");
    byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
    StringBuilder name = new StringBuilder();
    for (byte b : hash) {
      name.append(String.format("%02x", b));
    }
    return new File(directory, name + ".json");
  }

  public JsonElement get(String key) throws IOException, NoSuchAlgorithmException {
    File file = fileFor(key);
    if (!file.exists()) {
      return null;
    }
    FileReader reader = new FileReader(file);
    return gson.fromJson(reader, JsonElement.class);
  }

  public void put(String key, JsonElement value) throws IOException, NoSuchAlgorithmException {
    FileWriter writer = new FileWriter(fileFor(key));
    gson.toJson(value, writer);
    writer.flush();
  }

  public void evict(String key) throws NoSuchAlgorithmException {
    fileFor(key).delete();
  }
}
