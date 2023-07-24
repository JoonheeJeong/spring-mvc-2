package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String logId = UUID.randomUUID().toString();
        String requestURI = request.getRequestURI();
        request.setAttribute(LOG_ID, logId);

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            // @RequestMapping 메소드 관련 작업
        }
        else if (handler instanceof ResourceHttpRequestHandler) {
            ResourceHttpRequestHandler resourceHandler = (ResourceHttpRequestHandler) handler;
            // 정적 리소스 관련 작업
        }

        log.info("Request: [{}][{}]", logId, requestURI);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle: [{}]", request.getAttribute(LOG_ID));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Response: [{}]", request.getAttribute(LOG_ID));
        if (ex != null)
            log.error("afterCompletion has exception", ex);
    }
}
