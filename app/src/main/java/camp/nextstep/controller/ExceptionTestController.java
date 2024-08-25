package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionTestController {

    @RequestMapping("/exception")
    public void exception() {
        throw new RuntimeException("exception");
    }
}
