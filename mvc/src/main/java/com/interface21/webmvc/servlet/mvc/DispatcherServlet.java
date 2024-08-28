package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ExceptionHandlerMappingRegistry;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegistry handlerMappingRegistry;
    private final HandlerAdapterRegistry handlerAdapterRegistry;
    private final ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry;
    private HandlerExecutor handlerExecutor;

    public DispatcherServlet() {
        this.handlerMappingRegistry = new HandlerMappingRegistry();
        this.handlerAdapterRegistry = new HandlerAdapterRegistry();
        this.exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();
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

    public void addExceptionHandlerMapping(final ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMappingRegistry.addExceptionHandlerMapping(exceptionHandlerMapping);
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
        } catch (InvocationTargetException e) {
            handleException(request, response, e.getTargetException());
        } catch (Exception e) {
            handleException(request, response, e);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Throwable e) throws ServletException {
        log.error("Exception : {}", e.getMessage(), e);

        try {
            request.setAttribute("exception", e);
            ModelAndView modelAndView = handlerExecutor.handle(request, response, exceptionHandlerMappingRegistry.getHandler(e));
            render(modelAndView, request, response);
        } catch (Throwable ex) {
            throw new ServletException(ex.getMessage());
        }
    }

    private void render(final ModelAndView modelAndView, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final var view = modelAndView.getView();
        view.render(modelAndView.getModel(), request, response);
    }
}
