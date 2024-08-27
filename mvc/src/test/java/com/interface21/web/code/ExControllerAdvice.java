package com.interface21.web.code;

import com.interface21.web.bind.annotation.ControllerAdvice;
import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;

@ControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception e) {
        return new ModelAndView(new JspView("redirect:/500.jsp"));
    }
}
