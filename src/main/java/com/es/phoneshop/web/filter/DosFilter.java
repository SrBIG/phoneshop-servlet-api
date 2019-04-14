package com.es.phoneshop.web.filter;

import com.es.phoneshop.model.defence.dos.DosService;
import com.es.phoneshop.model.defence.dos.DosServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private DosService dosService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dosService = DosServiceImpl.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (dosService.isAllowed(request.getRemoteAddr())){
            chain.doFilter(request, response);
        } else{
            ((HttpServletResponse)response).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
