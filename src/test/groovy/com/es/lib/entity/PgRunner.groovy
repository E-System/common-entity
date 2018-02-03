package com.es.lib.entity

import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
class PgRunner extends Specification {

    @Shared
    EmbeddedPostgres postgres
    @Shared
    String url

    def setupSpec() {
        postgres = new EmbeddedPostgres()
        url = postgres.start("localhost", 6543, "test", "test", "test")
    }

    def cleanupSpec() {
        postgres.stop()
    }
}
