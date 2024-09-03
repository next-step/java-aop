package camp.nextstep.config;

import com.interface21.dao.DataAccessException;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.ControllerAdvice;
import com.interface21.webmvc.servlet.mvc.ExceptionHandler;
import com.interface21.webmvc.servlet.view.JspView;

@ControllerAdvice
public class MyAdvice {
    @ExceptionHandler(DataAccessException.class)
    public ModelAndView dataAccessException() {
        return new ModelAndView(new JspView("redirect:/index.jsp"));
    }
}
