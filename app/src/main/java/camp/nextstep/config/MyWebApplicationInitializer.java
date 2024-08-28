package camp.nextstep.config;

import com.interface21.context.support.AnnotationConfigWebApplicationContext;
import com.interface21.web.WebApplicationInitializer;
import com.interface21.webmvc.servlet.mvc.DispatcherServlet;
import com.interface21.webmvc.servlet.mvc.asis.ControllerHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.asis.ManualHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerConverter;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecutionHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandlerConverter;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandlerMapping;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    @Override
    public void onStartup(final ServletContext container) {
        final var applicationContext = new AnnotationConfigWebApplicationContext(MyConfiguration.class);
        final var handlerConverter = applicationContext.getBean(HandlerConverter.class);
        final var annotationHandlerMapping = new AnnotationHandlerMapping(applicationContext, handlerConverter);
        annotationHandlerMapping.initialize();
        final var exceptionHandlerConverter = applicationContext.getBean(ExceptionHandlerConverter.class);

        final var dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addHandlerMapping(new ManualHandlerMapping());
        dispatcherServlet.addHandlerMapping(new AnnotationHandlerMapping(applicationContext, handlerConverter));

        dispatcherServlet.addHandlerAdapter(new ControllerHandlerAdapter());
        dispatcherServlet.addHandlerAdapter(new HandlerExecutionHandlerAdapter());

        dispatcherServlet.addExceptionHandlerMapping(new ExceptionHandlerMapping(applicationContext, exceptionHandlerConverter));

        final var dispatcher = container.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start AppWebApplication Initializer");
    }
}
