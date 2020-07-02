package com.es.lib.entity

import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import java.sql.DriverManager

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 03.02.2018
 */
@Testcontainers
abstract class PgRunner extends Specification {

    @Shared
    String dbName = "test"
    @Shared
    String dbUser = "test"
    @Shared
    String dbPassword = "test"
    @Shared
    PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:11")
    .withDatabaseName(dbName)
    .withUsername(dbUser)
    .withPassword(dbPassword)

    def setupSpec() {
        println postgres.jdbcUrl
        def conn = DriverManager.getConnection(postgres.jdbcUrl, dbUser, dbPassword)
        conn.createStatement().execute("CREATE EXTENSION hstore;")
        conn.close()
    }

    protected SessionFactory newSessionFactory() {
        Properties properties = new Properties()
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect")
        properties.put("hibernate.hbm2ddl.auto", "update")
        properties.put("hibernate.show_sql", "true")
        properties.put("hibernate.connection.url", postgres.jdbcUrl)
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
