package filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Role;
import services.UserContext;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter(urlPatterns = { "/rest/admin/*", "/adminPanel.html",
		"/rest/task/admin/*", "/rest/user/admin/*" })
public class AdminFilter implements Filter {

	@Inject
	private UserContext userContext;

	public AdminFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		if (userContext.getCurrentUser() != null) {
			String role = userContext.getCurrentUser().getRole();
			if (role != "" && role == Role.ADMINISTRATOR) {
				chain.doFilter(req, resp);
			}
		}

		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
		String redirectURL = httpServletRequest.getContextPath()
				+ "/index.html";
		httpServletResponse.sendRedirect(redirectURL);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
