/*
 * Copyright 2016 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.entity;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class IdConstructor {

    private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_HASH_MAP = new ConcurrentHashMap<>();

    private IdConstructor() {}

    public static Constructor<?> get(Class<?> objectClass, Supplier<RuntimeException> exceptionSupplier) {
        return CONSTRUCTOR_HASH_MAP.computeIfAbsent(
            objectClass,
            aClass -> {
                try {
                    return objectClass.getMethod("getId").getReturnType().getConstructor(String.class);
                } catch (NoSuchMethodException e) {
                    throw exceptionSupplier.get();
                }
            }
        );
    }
}
