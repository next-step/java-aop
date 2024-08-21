package com.interface21.context.support;

import com.interface21.context.ApplicationContext;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import samples.DatasourceConfiguration;
import samples.NameService;
import samples.ProxyFactoryConfiguration;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationConfigWebApplicationContextTest {

    @Test
    void configuration() {
        final var applicationContext = new AnnotationConfigWebApplicationContext(DatasourceConfiguration.class);
        final var dataSource = applicationContext.getBean(DataSource.class);
        assertThat(dataSource).isInstanceOf(DataSource.class);
        assertThat(dataSource).isInstanceOf(JdbcDataSource.class);
    }

    @Test
    void FactoryBean으로_등록된_빈을_반환한다() {
        ApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(ProxyFactoryConfiguration.class);
        NameService actual = applicationContext.getBean(NameService.class);
        assertThat(actual.getName()).isEqualTo("JINYOUNG");
    }
}
