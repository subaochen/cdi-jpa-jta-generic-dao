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

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerProducer {

    @Produces
    public EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
        return (EntityManager) JtaEntityManagerProxy.newInstance(entityManagerFactory.createEntityManager());
    }

    public void close(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

// OWB supports resources injection, so the code below works fine under OWB

//    @PersistenceContext(unitName = "cdi-presistence-unit")
//    private EntityManager entityManager;
//
//    @Produces
//    protected EntityManager createEntityManager() {
//        return (EntityManager) JtaEntityManagerProxy.newInstance(entityManager);
//    }
//
//    protected void closeEntityManager(@Disposes EntityManager entityManager) {
//        if (entityManager.isOpen()) {
//            entityManager.close();
//        }
//    }
}
