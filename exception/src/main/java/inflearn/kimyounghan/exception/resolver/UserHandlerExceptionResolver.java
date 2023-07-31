package inflearn.kimyounghan.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import inflearn.kimyounghan.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("call UserHandlerExceptionResolver", ex);

        try {
            if (ex instanceof UserException) {
                String httpHeader = request.getHeader(HttpHeaders.ACCEPT);

                if (httpHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
                    log.info("when accept application/json to Bad Request(400) ");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("exception", ex.getClass());
                    errorResult.put("message", ex.getMessage());

                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    response.getWriter().write(objectMapper.writeValueAsString(errorResult));
                    return new ModelAndView();
                } else {
                    log.info("when accept not application/json");
                    new ModelAndView("error/500");
                }
            }
        } catch (IOException e) {
            log.error("UserHandlerExceptionResolver error", e);
        }
        return null;
    }
}
