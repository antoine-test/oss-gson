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

package com.google.gson;

import com.google.gson.annotations.Expose;
import java.lang.reflect.Field;
import java.util.List;

/**
 * A {@link FieldNamingStrategy} which prefixes the names of the fields which are serialized
 * according to their {@link Expose} annotation.
 *
 * @since 2.15.1
 */
public class PrefixedFieldNamingStrategy implements FieldNamingStrategy {
  private final String prefix;

  public PrefixedFieldNamingStrategy(String prefix) {
    this.prefix = prefix;
  }

  @Override
  public String translateName(Field f) {
    Expose expose = f.getAnnotation(Expose.class);
    if (expose.serialize()) {
      return prefix + f.getName();
    }
    return f.getName();
  }

  @Override
  public List<String> alternateNames(Field f) {
    return null;
  }
}
