package controller.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import util.ReadProperties;

import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {

    private static String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        encoding = ReadProperties.getPropertyByKey("SERVLET_ENCODING");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
