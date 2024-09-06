package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExceptionHandlerMappingRegistry {
    private final List<ExceptionHandlerMapping> exceptionHandlerMappings = new ArrayList<>();

    public void addExceptionHandlerMapping(final ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMapping.initialize();
        exceptionHandlerMappings.add(exceptionHandlerMapping);
    }

    public Optional<Object> getExceptionHandler(final Throwable exception) {
        return exceptionHandlerMappings.stream()
                .map(hm -> hm.getHandler(exception))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
