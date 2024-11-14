package com.interface21.context.support;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.interface21.beans.BeanProxyInterceptor;
import com.interface21.beans.DefaultFactoryBean;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import samples.DatasourceConfiguration;
import samples.SampleController;
import samples.SampleService;
import samples.TestConfiguration;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnnotationConfigWebApplicationContextTest {
    private TestAppender testAppender;

    @BeforeEach
    public void setUp() {
        Logger logger = (Logger) LoggerFactory.getLogger(BeanProxyInterceptor.class);
        testAppender = new TestAppender();
        logger.addAppender(testAppender);
        testAppender.start();
    }

    @Test
    void configuration() {
        final var applicationContext = new AnnotationConfigWebApplicationContext(DatasourceConfiguration.class);
        final var dataSource = applicationContext.getBean(DataSource.class);
        assertThat(dataSource).isInstanceOf(DataSource.class);
        assertThat(dataSource).isInstanceOf(JdbcDataSource.class);
    }

    @Test
    void 프록시객체테스트() {
        final var applicationContext = new AnnotationConfigWebApplicationContext(TestConfiguration.class);
        final var sampleController = applicationContext.getBean(SampleController.class);
        assertThat(sampleController.getSampleService()).isInstanceOf(SampleService.class);
        sampleController.hello();

        assertTrue(testAppender.contains("프록시 메소드 동작"));
    }

    static class TestAppender extends AppenderBase<ILoggingEvent> {
        private StringBuilder logMessages = new StringBuilder();

        @Override
        protected void append(ILoggingEvent eventObject) {
            logMessages.append(eventObject.getFormattedMessage());
        }

        public boolean contains(String message) {
            return logMessages.toString().contains(message);
        }
    }
}
