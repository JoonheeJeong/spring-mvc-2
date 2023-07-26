package inflearn.kimyounghan.exception.servlet.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    public static final String LOG_ID = "logID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String logId = UUID.randomUUID().toString();
        String requestURI = httpRequest.getRequestURI();

        try {
            request.setAttribute(LOG_ID, logId);
            log.info("Request: [{}][{}][{}]", logId, httpRequest.getDispatcherType(), requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("Exception: {}", e.getMessage());
            throw e;
        } finally {
            log.info("Response: [{}][{}][{}]", logId, httpRequest.getDispatcherType(), requestURI);
        }
    }
}
