package info.luckydog.craft.filter;

import info.luckydog.craft.util.CommTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HttpLogFilter
 *
 * @author eric
 * @since 2019/6/6
 */
@Component
@Slf4j
public class HttpLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("httpLogFilter-init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        String method = "GET";
        String reqStr = "";
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            String requestUrl = ((HttpServletRequest) request).getRequestURI();
            method = ((HttpServletRequest) request).getMethod().toUpperCase();

            reqStr = String.format("%s %s req-params: %s req-header: ", method, requestUrl, CommTools.objectToJson(request.getParameterMap()));
            requestWrapper = new ReqReaderWrapper((HttpServletRequest) request);
        }

        ResReaderWrapper cresponse = new ResReaderWrapper((HttpServletResponse) response);

        if ("POST".equals(method) || "PUT".equals(method)) {
            reqStr = String.format("%s req-body: %s", reqStr, requestWrapper.getReader().readLine());
        }

        filterChain.doFilter(requestWrapper, cresponse);

        int status = -1;
        if (response != null) {
            status = ((HttpServletResponse) response).getStatus();
        }

        String resStr = new String(cresponse.getCopy());

        long end = System.currentTimeMillis();
        log.info("{}ms http-log {} {} res: {}", (end - start), status, reqStr, resStr);
    }

    @Override
    public void destroy() {
        log.info("httpLogFilter-destroy");
    }
}
