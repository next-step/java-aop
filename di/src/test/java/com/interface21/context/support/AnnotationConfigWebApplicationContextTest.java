package com.interface21.context.support;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.DatasourceConfiguration;
import samples.HelloFactoryBeanConfiguration;
import samples.Hello;
import samples.componentscan.ComponentScanConfig;

class AnnotationConfigWebApplicationContextTest {

    @Test
    void configuration() {
        final var applicationContext = new AnnotationConfigWebApplicationContext(DatasourceConfiguration.class);
        final var dataSource = applicationContext.getBean(DataSource.class);
        assertThat(dataSource).isInstanceOf(DataSource.class);
        assertThat(dataSource).isInstanceOf(JdbcDataSource.class);
    }

    @DisplayName("FactoryBean을 @Configuration + @Bean을 이용해 bean으로 등록하면 getObject()로 생성된 객체를 빈으로 등록한다.")
    @Test
    void factoryBean() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(HelloFactoryBeanConfiguration.class);

        Hello hello = applicationContext.getBean(Hello.class);

        assertThat(hello.getName()).isEqualTo("kim");
    }

    @DisplayName("FactoryBean을 @Component 이용해 bean으로 등록하면 getObject()로 생성된 객체를 빈으로 등록한다.")
    @Test
    void factoryBean2() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(ComponentScanConfig.class);

        Hello hello = applicationContext.getBean(Hello.class);

        assertThat(hello.getName()).isEqualTo("park");
    }
}
