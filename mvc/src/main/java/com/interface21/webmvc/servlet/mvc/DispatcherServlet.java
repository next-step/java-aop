package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExceptionResolver;
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
    private final HandlerExceptionRegistry handlerExceptionRegistry;
    private final HandlerAdapterRegistry handlerAdapterRegistry;
    private HandlerExecutor handlerExecutor;

    public DispatcherServlet() {
        this.handlerMappingRegistry = new HandlerMappingRegistry();
        this.handlerAdapterRegistry = new HandlerAdapterRegistry();
        this.handlerExceptionRegistry = new HandlerExceptionRegistry();
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

    public void addHandlerExceptionResolver(final HandlerExceptionResolver handlerExceptionResolver) {
        handlerExceptionRegistry.addHandlerExceptionResolver(handlerExceptionResolver);
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

    private void render(final ModelAndView modelAndView, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final var view = modelAndView.getView();
        view.render(modelAndView.getModel(), request, response);
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException {
        log.error("Exception : {}", e.getMessage(), e);
        final var modelAndView = handlerExceptionRegistry.handle(request, response, e)
            .orElseThrow(() -> new ServletException("Error handling exception: " + e.getMessage()));

        try {
            render(modelAndView, request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
