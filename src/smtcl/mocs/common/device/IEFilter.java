package smtcl.mocs.common.device;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
/**
 * IE������汾��֤
 * @����: JiangFeng
 * @����ʱ�䣺2013-05-10
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @�汾��V1.0
 */
@SuppressWarnings("serial")
public class IEFilter extends HttpServlet implements Filter {
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=EmulateIE8");
        String accept = ((HttpServletRequest) request).getHeader("Accept");
        if ("text/css".equals(accept)) {
            chain.doFilter(new IE9HttpServletRequestWrapper((HttpServletRequest) request), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private class IE9HttpServletRequestWrapper extends HttpServletRequestWrapper {
        public IE9HttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }
        @Override
        public String getHeader(String name) {
            String header = super.getHeader(name);
            if ("text/css".equalsIgnoreCase(header)) {
                header = "text/css, */*";
            }
            return header;
        }
    }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
