package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String logId = UUID.randomUUID().toString();
        String requestURI = httpRequest.getRequestURI();
        httpRequest.setAttribute("logId", logId);

        try {
            log.info("Request: [{}][{}]", logId, requestURI);
            chain.doFilter(request, response);
        } finally {
            log.info("Response: [{}][{}]", logId, requestURI);
        }
    }
}
