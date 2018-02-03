package com.es.lib.entity.type

import com.es.lib.entity.PgRunner

import java.sql.DriverManager

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
class HStoreTypeSpec extends PgRunner {

    def "Test"() {
        setup:
        def conn = DriverManager.getConnection(url)
        conn.createStatement().execute("CREATE TABLE films (code CHAR(5));")
        conn.createStatement().execute("INSERT INTO films VALUES ('movie');")
        when:
        def stmt = conn.createStatement()
        then:
        stmt
        stmt.execute("SELECT * FROM films;")
        stmt.getResultSet().next()
        stmt.getResultSet().getString("code") == "movie"
    }
}
