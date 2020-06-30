/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Getter
@ToString
@RequiredArgsConstructor
public class QueryContext {

    private final String query;
    private final Map<String, Object> params;

    public static <T extends Query> T fill(T query, Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query;
    }

    public static <T, PK> Query deleteCriteria(EntityManager entityManager, Class<T> typeClass, Class<PK> pkClass) {
        return deleteCriteria(
            entityManager,
            typeClass,
            pkClass,
            v -> v.get("id")
        );
    }

    public static <T, PK> Query deleteCriteria(
        EntityManager entityManager,
        Class<T> typeClass,
        Class<PK> pkClass,
        Function<Root<T>, Expression<?>> pathFunction
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        ParameterExpression<PK> idParameter = cb.parameter(pkClass, "id");

        CriteriaDelete<T> delete = cb.createCriteriaDelete(typeClass);
        Root<T> e = delete.from(typeClass);
        delete.where(
            cb.equal(
                pathFunction.apply(e), idParameter
            )
        );
        return entityManager.createQuery(delete);
    }
}
