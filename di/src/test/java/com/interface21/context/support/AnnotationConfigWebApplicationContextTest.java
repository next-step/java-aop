package com.interface21.context.support;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.DatasourceConfiguration;
import samples.FactoryBeanCreatingConfiguration;
import samples.SampleObject;
import samples.World;

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
    @DisplayName("FactoryBean 으로 정의된 빈을 생성할 수 있다.")
    void factoryBeanTest() {
        final var applicationContext = new AnnotationConfigWebApplicationContext(DatasourceConfiguration.class);
        final SampleObject bean = applicationContext.getBean(SampleObject.class);
        assertThat(bean).isInstanceOf(SampleObject.class);
        assertThat(bean.name()).isEqualTo("hello");
    }

    @Test
    void autowiredProxyFactoryBeanTest() {
        final var applicationContext = new AnnotationConfigWebApplicationContext(FactoryBeanCreatingConfiguration.class);
        final World world = applicationContext.getBean(World.class);
        assertThat(world.getMessage()).isEqualTo("Hello, World!");
    }
}
