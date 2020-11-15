package captura.core;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.UUID;

public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var id = request.getHeader("X-Request-Id");
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        MDC.put("X-Request-Id", id);
        var myResponse = (HttpServletResponse) servletResponse;
        var responseWrapper = new MyResponseRequestWrapper(myResponse);
        responseWrapper.addHeader("X-Request", id);
        filterChain.doFilter(servletRequest, myResponse);
    }

    @Override
    public void destroy() {

    }

    static class MyResponseRequestWrapper extends HttpServletResponseWrapper {
        public MyResponseRequestWrapper(HttpServletResponse response) {
            super(response);
        }
    }
}
