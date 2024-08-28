package com.interface21.transaction;

import com.interface21.beans.factory.ProxyFactoryBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.JdbcTestBeanFactory;
import samples.FakeTransactionManager;
import samples.TxMethodTestService;
import samples.TxTypeTestService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TransactionBeanPostProcessorTest {

    @DisplayName("@Transaction 애너테이션이 붙은 클래스의 프록시로 정상적인 메서드를 호출 하면 트랜잭션이 시작 된 후 커밋 된다")
    @Test
    public void transactionCommitInTypeProxy() throws Exception {
        // given
        final JdbcTestBeanFactory beanFactory = new JdbcTestBeanFactory();
        final FakeTransactionManager transactionManager = (FakeTransactionManager) beanFactory.getBean(PlatformTransactionManager.class);
        final TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor();
        beanPostProcessor.injectBeanFactory(beanFactory);
        final TxTypeTestService bean = (TxTypeTestService) ((ProxyFactoryBean) beanPostProcessor.postInitialization(new TxTypeTestService())).getObject();

        // when
        bean.expectCommit();

        // then
        assertAll(
                () -> Assertions.assertThat(transactionManager.isBegan()).isTrue(),
                () -> Assertions.assertThat(transactionManager.isCommited()).isTrue(),
                () -> Assertions.assertThat(transactionManager.isRollBacked()).isFalse()
        );
    }

    @DisplayName("@Transaction 애너테이션이 붙은 클래스의 프록시로 예외가 발생하는 메서드를 호출 하면 트랜잭션이 시작 된 후 롤백 된다")
    @Test
    public void transactionRollBackInTypeProxy() throws Exception {
        // given
        final JdbcTestBeanFactory beanFactory = new JdbcTestBeanFactory();
        final FakeTransactionManager transactionManager = (FakeTransactionManager) beanFactory.getBean(PlatformTransactionManager.class);
        final TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor();
        beanPostProcessor.injectBeanFactory(beanFactory);
        final TxTypeTestService bean = (TxTypeTestService) ((ProxyFactoryBean) beanPostProcessor.postInitialization(new TxTypeTestService())).getObject();

        // when then
        assertAll(
                () -> assertThatThrownBy(bean::expectRollBack),
                () -> Assertions.assertThat(transactionManager.isBegan()).isTrue(),
                () -> Assertions.assertThat(transactionManager.isCommited()).isFalse(),
                () -> Assertions.assertThat(transactionManager.isRollBacked()).isTrue()
        );
    }

    @DisplayName("프록시의 @Transaction 애너테이션이 붙은 정상적인 메서드를 호출 하면 트랜잭션이 시작 된 후 커밋 된다")
    @Test
    public void transactionCommitInTypeMethod() throws Exception {
        // given
        final JdbcTestBeanFactory beanFactory = new JdbcTestBeanFactory();
        final FakeTransactionManager transactionManager = (FakeTransactionManager) beanFactory.getBean(PlatformTransactionManager.class);
        final TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor();
        beanPostProcessor.injectBeanFactory(beanFactory);
        final TxMethodTestService bean = (TxMethodTestService) ((ProxyFactoryBean) beanPostProcessor.postInitialization(new TxMethodTestService())).getObject();

        // when
        bean.expectCommit();

        // then
        assertAll(
                () -> Assertions.assertThat(transactionManager.isBegan()).isTrue(),
                () -> Assertions.assertThat(transactionManager.isCommited()).isTrue(),
                () -> Assertions.assertThat(transactionManager.isRollBacked()).isFalse()
        );
    }

    @DisplayName("프록시의 @Transaction 애너테이션이 붙은 예외가 발생하는 메서드를 호출 하면 트랜잭션이 시작 된 후 롤백 된다")
    @Test
    public void transactionRollBackInTypeMethod() throws Exception {
        // given
        final JdbcTestBeanFactory beanFactory = new JdbcTestBeanFactory();
        final FakeTransactionManager transactionManager = (FakeTransactionManager) beanFactory.getBean(PlatformTransactionManager.class);
        final TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor();
        beanPostProcessor.injectBeanFactory(beanFactory);
        final TxMethodTestService bean = (TxMethodTestService) ((ProxyFactoryBean) beanPostProcessor.postInitialization(new TxMethodTestService())).getObject();

        // when then
        assertAll(
                () -> assertThatThrownBy(bean::expectRollBack),
                () -> Assertions.assertThat(transactionManager.isBegan()).isTrue(),
                () -> Assertions.assertThat(transactionManager.isCommited()).isFalse(),
                () -> Assertions.assertThat(transactionManager.isRollBacked()).isTrue()
        );
    }

    @DisplayName("프록시의 @Transaction 애너테이션이 없는 메서드를 호출 하면 트랜잭션을 시작하지 않는다")
    @Test
    public void transactionNothingInTypeMethod() throws Exception {
        // given
        final JdbcTestBeanFactory beanFactory = new JdbcTestBeanFactory();
        final FakeTransactionManager transactionManager = (FakeTransactionManager) beanFactory.getBean(PlatformTransactionManager.class);
        final TransactionBeanPostProcessor beanPostProcessor = new TransactionBeanPostProcessor();
        beanPostProcessor.injectBeanFactory(beanFactory);
        final TxMethodTestService bean = (TxMethodTestService) ((ProxyFactoryBean) beanPostProcessor.postInitialization(new TxMethodTestService())).getObject();

        // when
        bean.expectNothing();

        // then
        assertAll(
                () -> Assertions.assertThat(transactionManager.isBegan()).isFalse(),
                () -> Assertions.assertThat(transactionManager.isCommited()).isFalse(),
                () -> Assertions.assertThat(transactionManager.isRollBacked()).isFalse()
        );
    }

}
