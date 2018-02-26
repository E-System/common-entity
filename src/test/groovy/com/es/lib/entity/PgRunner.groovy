package com.es.lib.entity

import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres
import ru.yandex.qatools.embed.postgresql.util.SocketUtil
import spock.lang.Shared
import spock.lang.Specification

import java.sql.DriverManager

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
abstract class PgRunner extends Specification {

    @Shared
    EmbeddedPostgres postgres
    @Shared
    String url
    @Shared
    String dbName = "test"
    @Shared
    String dbUser = "test"
    @Shared
    String dbPassword = "test"

    def setupSpec() {
        postgres = new EmbeddedPostgres()
        url = postgres.start("localhost", SocketUtil.findFreePort(), dbName, dbUser, dbPassword)
        def conn = DriverManager.getConnection(url)
        conn.createStatement().execute("CREATE EXTENSION hstore;")
        conn.close()
    }

    def cleanupSpec() {
        postgres.stop()
    }

    protected SessionFactory newSessionFactory() {
        Properties properties = new Properties()
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect")
        properties.put("hibernate.hbm2ddl.auto", "update")
        properties.put("hibernate.show_sql", "true")
        properties.put("hibernate.connection.url", url)
        properties.put("hibernate.connection.username", dbUser)
        properties.put("hibernate.connection.password", dbPassword)

        def conf = new Configuration()
                .addProperties(properties)

        for (Class<?> cls : entityClasses) {
            conf.addAnnotatedClass(cls)
        }

        return conf.buildSessionFactory(
                new StandardServiceRegistryBuilder()
                        .applySettings(properties)
                        .build()
        )
    }

    protected abstract Collection<Class<?>> getEntityClasses()
}
