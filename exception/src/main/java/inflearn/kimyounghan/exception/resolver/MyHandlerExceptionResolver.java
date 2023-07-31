package inflearn.kimyounghan.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("call MyHandlerExceptionResolver", ex);
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolve");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                ModelAndView mv = new ModelAndView();
                return mv;
            }
        } catch (IOException e) {
            log.error("exception resolve error", e);
        }
        return null;
    }
}
