package camp.nextstep.config;

import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.dao.DataAccessException;
import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;

@ControllerAdvice
public class MyAdvice {

    @ExceptionHandler(DataAccessException.class)
    public ModelAndView dataAccessException() {
        return new ModelAndView(new JspView("redirect:/index.jsp"));
    }
}
