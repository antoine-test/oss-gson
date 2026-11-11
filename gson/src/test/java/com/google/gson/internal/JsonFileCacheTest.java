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

import com.google.gson.JsonPrimitive;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class JsonFileCacheTest {
  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void testPutAndGet() throws Exception {
    JsonFileCache cache = new JsonFileCache(folder.getRoot());
    cache.put("key", new JsonPrimitive("value"));
    assertThat(cache.get("key").getAsString()).isEqualTo("value");
  }

  @Test
  public void testMissingKey() throws Exception {
    JsonFileCache cache = new JsonFileCache(folder.getRoot());
    assertThat(cache.get("missing")).isNull();
  }

  @Test
  public void testEvict() throws Exception {
    JsonFileCache cache = new JsonFileCache(folder.getRoot());
    cache.put("key", new JsonPrimitive("value"));
    cache.evict("key");
    assertThat(cache.get("key")).isNull();
  }
}
