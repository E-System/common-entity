package com.es.lib.entity.dialect;

import org.hibernate.dialect.PostgreSQL10Dialect;

public class ESPostgresDialect extends PostgreSQL10Dialect {

    public ESPostgresDialect() {
        super();
        registerFunction(ArrayOperatorContain.NAME, new ArrayOperatorContain());
        registerFunction(ArrayOperatorContained.NAME, new ArrayOperatorContained());
        registerFunction(ArrayOperatorOverlap.NAME, new ArrayOperatorOverlap());
        registerFunction(ArrayCast.NAME, new ArrayCast());
    }
}
