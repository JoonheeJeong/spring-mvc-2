package inflearn.kimyounghan.exception.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ServletErrorController {

    @GetMapping("/error-ex")
    public void occurException() throws RuntimeException {
        throw new RuntimeException("예외 발생 !!!");
    }

    @GetMapping("/error-404")
    public void sendError404(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "404 에러 발생!");
    }

    @GetMapping("/error-500")
    public void sendError500(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500 에러 발생!");
    }
}
