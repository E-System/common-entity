package com.es.lib.entity

import com.es.lib.entity.type.entity.TestEntity
import com.es.lib.entity.type.entity.TestJsonEntity
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres
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

    def setupSpec() {
        postgres = new EmbeddedPostgres()
        url = postgres.start("localhost", 6543, "test", "test", "test")
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
        //log settings
        properties.put("hibernate.hbm2ddl.auto", "update")
        properties.put("hibernate.show_sql", "true")
        //driver settings
        //properties.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
        properties.put("hibernate.connection.url", url)
        properties.put("hibernate.connection.username", "test")
        properties.put("hibernate.connection.password", "test")

        def conf = new Configuration()
                .addProperties(properties)

        for (Class<?> cls : getConfigClasses()) {
            conf.addAnnotatedClass(cls)
        }

        return conf.buildSessionFactory(
                new StandardServiceRegistryBuilder()
                        .applySettings(properties)
                        .build()
        )
    }

    protected abstract Collection<Class<?>> getConfigClasses()
}
