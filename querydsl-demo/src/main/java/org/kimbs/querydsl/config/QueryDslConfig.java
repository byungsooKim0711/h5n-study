package org.kimbs.querydsl.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class QueryDslConfig {

    private final EntityManager em;

    public QueryDslConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    /*********************************************************************************************************/
    @Bean
    public com.querydsl.sql.Configuration configuration() {
        SQLTemplates templates = MySQLTemplates.builder().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory(DataSource dataSource) {
        Provider<Connection> provider = new SpringConnectionProvider(dataSource);
        return new SQLQueryFactory(configuration(), provider);
    }
    /*********************************************************************************************************/
}
