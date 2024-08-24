package com.interface21.context.support;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.DatasourceConfiguration;
import samples.ProxyConfiguration;
import samples.SayCounter;
import samples.TestService;

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

    @DisplayName("FactoryBean 으로 빈을 생성 한다")
    @Test
    public void factoryBeanConfiguration() throws Exception {
        // given
        final AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(ProxyConfiguration.class);
        final TestService bean = applicationContext.getBean(TestService.class);

        // when
        bean.sayHello();

        // then
        assertThat(SayCounter.currentCount()).isEqualTo(1);
    }
}
