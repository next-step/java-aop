package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.dao.DataAccessException;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ExceptionController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void dataAccessException(final HttpServletRequest request, final HttpServletResponse response) {
        throw new DataAccessException();
    }
}
