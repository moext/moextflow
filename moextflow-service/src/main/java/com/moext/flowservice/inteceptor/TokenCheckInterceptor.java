package com.moext.flowservice.inteceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.moext.flowservice.common.RspUtils;
import com.moext.flowservice.common.WebConstant;

/**
 * Token检查拦截器，通过HTTP头检查token的方式提供简单的安全检查
 * token配置在application.yml中，留空则表示不启用HTTP头token安全检查
 * 
 * @author PengPeng
 *
 */
public class TokenCheckInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(TokenCheckInterceptor.class);

	private String token;

	public TokenCheckInterceptor(String token) {
		this.token = token;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 不配置Token，则无Token安全检查
		if (StringUtils.isBlank(token)) {
			return true;
		}

		String token = request.getHeader("ACCESS_TOKEN");
		if (StringUtils.equals(token, this.token)) {
			return true;
		} else {
			response.setHeader("Content-Type", WebConstant.PRODUCES_TYPE_JSON);
			String msg = RspUtils.errorStr("无权限调用API");
			PrintWriter out = response.getWriter();
			out.print(msg);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 不处理
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (ex != null) {
			logger.error(request.getRequestURI(), ex);
		}
	}

}
