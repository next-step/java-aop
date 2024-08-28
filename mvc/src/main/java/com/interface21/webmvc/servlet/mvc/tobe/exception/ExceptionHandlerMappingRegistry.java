package com.interface21.webmvc.servlet.mvc.tobe.exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionHandlerMappingRegistry {

    private final List<ExceptionHandlerMapping> exceptionHandlerMappings = new ArrayList<>();

    public void addExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMapping.initialize();
        exceptionHandlerMappings.add(exceptionHandlerMapping);
    }

    public Object getHandler(Throwable exception) throws Throwable {
        return exceptionHandlerMappings.stream()
                .filter(exceptionHandlerMapping -> exceptionHandlerMapping.accept(exception))
                .map(exceptionHandlerMapping -> exceptionHandlerMapping.getHandler(exception))
                .findFirst()
                .orElseThrow(() -> exception);
    }
}
