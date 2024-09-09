package camp.nextstep.controller.advice;

import com.interface21.dao.DataAccessException;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.ControllerAdvice;
import com.interface21.webmvc.servlet.mvc.tobe.ExceptionHandler;
import com.interface21.webmvc.servlet.view.JsonView;
import com.interface21.webmvc.servlet.view.JspView;

@ControllerAdvice
public class MyAdvice {
    @ExceptionHandler(DataAccessException.class)
    public ModelAndView dataAccessException() {
        return new ModelAndView(new JspView("redirect:/index.jsp"));
    }

    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView arithmeticException() {
        return message("ArithmeticException occurred");
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeException() {
        return message("RuntimeException occurred");
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView throwable() {
        return message("Throwable occurred");
    }

    private static ModelAndView message(String message) {
        JsonView view = new JsonView();
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
