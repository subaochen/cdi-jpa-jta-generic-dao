/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.javafiction.common.persistence.resources;


import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Allows EntityManager to "auto join into JTA transaction"
 * See: https://community.jboss.org/message/850194
 */
public class JtaEntityManagerProxy implements java.lang.reflect.InvocationHandler {


    private EntityManager obj;

    public static Object newInstance(EntityManager obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),new Class<?>[] {EntityManager.class}, new JtaEntityManagerProxy(obj));
    }

    private JtaEntityManagerProxy(EntityManager obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Object result;
        try {
            obj.joinTransaction();
            result = m.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage(), e);
        } finally {
        }
        return result;
    }

}
