package com.interface21.webmvc.servlet.mvc;

import com.interface21.context.ApplicationContext;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegistry handlerMappingRegistry;
    private final HandlerAdapterRegistry handlerAdapterRegistry;
    private HandlerExceptionRegistry exceptionRegistry;
    private HandlerExecutor handlerExecutor;

    public DispatcherServlet() {
        this.handlerMappingRegistry = new HandlerMappingRegistry();
        this.handlerAdapterRegistry = new HandlerAdapterRegistry();
    }

    @Override
    public void init() {
        final ApplicationContext ac = (ApplicationContext) getServletContext().getAttribute("applicationContext");

        this.handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);
        this.exceptionRegistry = ac.getBean(HandlerExceptionRegistry.class);
        this.exceptionRegistry.initApplicationContext(ac);
    }

    public void addHandlerMapping(final HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    public void addHandlerAdapter(final HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        log.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());

        final var handler = handlerMappingRegistry.getHandler(request);

        try {
            doDispatch(request, response, handler);
        } catch (Throwable e) {
            processHandlerException(request, response, handler, e);
        }
    }

    private void doDispatch(final HttpServletRequest request, final HttpServletResponse response, final Optional<Object> handler) throws Exception {
        try {
            if (handler.isEmpty()) {
                response.setStatus(404);
                return;
            }

            final var modelAndView = handlerExecutor.handle(request, response, handler.get());
            render(modelAndView, request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw e;
        }
    }

    private void render(final ModelAndView modelAndView, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final var view = modelAndView.getView();
        view.render(modelAndView.getModel(), request, response);
    }

    private void processHandlerException(final HttpServletRequest req, final HttpServletResponse res, final Object handler, final Throwable ex) {
        final ModelAndView exMv = this.exceptionRegistry.resolveException(req, res, handler, ex);

        try {
            render(exMv, req, res);
        } catch (Exception e) {
            log.error("Exception in render exMv : {}", e.getMessage(), e);
            res.setStatus(500);
        }
    }

}
