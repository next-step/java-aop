package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandlerMapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegistry handlerMappingRegistry;
    private final HandlerAdapterRegistry handlerAdapterRegistry;
    private HandlerExecutor handlerExecutor;

    private final ExceptionHandlerMapping exceptionHandlerMapping;

    public DispatcherServlet(ExceptionHandlerMapping exceptionHandlerMapping) {
        this.handlerMappingRegistry = new HandlerMappingRegistry();
        this.handlerAdapterRegistry = new HandlerAdapterRegistry();
        this.exceptionHandlerMapping = exceptionHandlerMapping;
    }

    @Override
    public void init() {
        this.handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);
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

        try {
            final var handler = handlerMappingRegistry.getHandler(request);
            if (handler.isEmpty()) {
                response.setStatus(404);
                return;
            }

            final var modelAndView = handlerExecutor.handle(request, response, handler.get());
            render(modelAndView, request, response);
        } catch (Exception e) {
            handleException(request, response, e);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException {
        log.error("Exception : {}", e.getMessage(), e);

        try {
            ExceptionHandlerExecution exceptionHandlerExecution = exceptionHandlerMapping.getHandler(e);
            ModelAndView modelAndView = exceptionHandlerExecution.handle(e);
            render(modelAndView, request, response);
        } catch (Exception ex) {
            throw new ServletException(ex.getMessage());
        }
    }

    private void render(final ModelAndView modelAndView, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final var view = modelAndView.getView();
        view.render(modelAndView.getModel(), request, response);
    }
}
