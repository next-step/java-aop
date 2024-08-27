package com.interface21.context.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.DatasourceConfiguration;
import samples.Hello;
import samples.HelloFactoryBeanConfiguration;
import samples.NoneTransactional;
import samples.TransactionalOnClass;
import samples.TransactionalOnMethod;
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

    @DisplayName("@Transactional 애노테이션이 클래스 수준에 달린 빈을 등록하면 프록시로 등록된다.")
    @Test
    void transactional() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(DatasourceConfiguration.class);

        TransactionalOnClass transactionalOnClass = applicationContext.getBean(TransactionalOnClass.class);

        assertThat(transactionalOnClass.getClass().getName()).contains("$$EnhancerByCGLIB$$");
    }

    @DisplayName("@Transactional 애노테이션이 메서드 수준에 달린 빈을 등록하면 프록시로 등록된다.")
    @Test
    void transactional2() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(DatasourceConfiguration.class);

        TransactionalOnMethod transactionalOnMethod = applicationContext.getBean(TransactionalOnMethod.class);

        assertThat(transactionalOnMethod.getClass().getName()).contains("$$EnhancerByCGLIB$$");
    }

    @DisplayName("@Transactional 애노테이션이 없는 빈은 프록시로 등록되지 않는다.")
    @Test
    void transactional3() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(DatasourceConfiguration.class);

        NoneTransactional noneTransactional = applicationContext.getBean(NoneTransactional.class);

        assertAll(
                () -> assertThat(noneTransactional.getClass()).isEqualTo(NoneTransactional.class),
                () -> assertThat(noneTransactional.getClass().getName()).isEqualTo("samples.NoneTransactional")
        );
    }
}
