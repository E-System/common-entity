@TypeDefs({
    @TypeDef(name = "DateList", typeClass = DateArrayListType.class),
    @TypeDef(name = "IntegerList", typeClass = IntegerArrayListType.class),
    @TypeDef(name = "LongList", typeClass = LongArrayListType.class),
    @TypeDef(name = "StringList", typeClass = StringArrayListType.class),

    @TypeDef(name = "DateSet", typeClass = DateSetType.class),
    @TypeDef(name = "IntegerSet", typeClass = IntegerSetType.class),
    @TypeDef(name = "LongSet", typeClass = LongSetType.class),
    @TypeDef(name = "ShortSet", typeClass = ShortSetType.class),
    @TypeDef(name = "StringSet", typeClass = StringSetType.class),

    @TypeDef(name = "DateArray", typeClass = DateArrayType.class),
    @TypeDef(name = "IntegerArray", typeClass = IntegerArrayType.class),
    @TypeDef(name = "IntegerPrimitiveArray", typeClass = IntegerPrimitiveArrayType.class),
    @TypeDef(name = "LongArray", typeClass = LongArrayType.class),
    @TypeDef(name = "LongPrimitiveArray", typeClass = LongPrimitiveArrayType.class),
    @TypeDef(name = "StringArray", typeClass = StringArrayType.class),

    @TypeDef(name = "Geo", typeClass = GeoPointType.class),

    @TypeDef(name = "HStore", typeClass = HStoreType.class),
})
package com.es.lib.entity;

import com.es.lib.entity.type.HStoreType;
import com.es.lib.entity.type.array.*;
import com.es.lib.entity.type.array.list.DateArrayListType;
import com.es.lib.entity.type.array.list.IntegerArrayListType;
import com.es.lib.entity.type.array.list.LongArrayListType;
import com.es.lib.entity.type.array.list.StringArrayListType;
import com.es.lib.entity.type.array.set.*;
import com.es.lib.entity.type.geo.GeoPointType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
