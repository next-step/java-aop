package com.interface21.webmvc.servlet.mvc.tobe;

public interface ExceptionHandlerMapping {
    void initialize();

    Object getHandler(Throwable exception);
}
