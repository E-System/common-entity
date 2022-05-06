package com.es.lib.entity.dialect;

import com.es.lib.entity.dialect.function.ArrayCast;
import com.es.lib.entity.dialect.function.ArrayOperatorContain;
import com.es.lib.entity.dialect.function.ArrayOperatorContained;
import com.es.lib.entity.dialect.function.ArrayOperatorOverlap;
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
