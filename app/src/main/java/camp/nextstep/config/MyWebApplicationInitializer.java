package camp.nextstep.config;

import com.interface21.beans.factory.config.TransactionProxyPostProcessor;
import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.transaction.PlatformTransactionManager;
import com.interface21.web.WebApplicationInitializer;
import com.interface21.webmvc.servlet.mvc.DispatcherServlet;
import com.interface21.webmvc.servlet.mvc.asis.ControllerHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.asis.ManualHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.ControllerAdviceConverter;
import com.interface21.webmvc.servlet.mvc.tobe.ExceptionHandlerExceptionResolver;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerConverter;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecutionHandlerAdapter;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    @Override
    public void onStartup(final ServletContext container) {
        final var applicationContext = new AnnotationConfigWebApplicationContext(MyConfiguration.class);
        final var handlerConverter = applicationContext.getBean(HandlerConverter.class);
        final var controllerAdviceConverter = applicationContext.getBean(ControllerAdviceConverter.class);
        final var annotationHandlerMapping = new AnnotationHandlerMapping(applicationContext, handlerConverter, controllerAdviceConverter);

        final var platformTransactionManager = applicationContext.getBean(PlatformTransactionManager.class);
        applicationContext.addBeanPostProcessor(new TransactionProxyPostProcessor(platformTransactionManager));


        final var dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addHandlerMapping(new ManualHandlerMapping());
        dispatcherServlet.addHandlerMapping(annotationHandlerMapping);

        dispatcherServlet.addHandlerAdapter(new ControllerHandlerAdapter());
        dispatcherServlet.addHandlerAdapter(new HandlerExecutionHandlerAdapter());

        dispatcherServlet.addHandlerExceptionResolver(new ExceptionHandlerExceptionResolver(annotationHandlerMapping.getExceptionHandlers()));

        final var dispatcher = container.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start AppWebApplication Initializer");
    }
}
