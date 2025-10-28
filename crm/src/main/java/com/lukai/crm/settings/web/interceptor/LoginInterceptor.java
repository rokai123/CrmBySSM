package com.lukai.crm.settings.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lukai.crm.commons.contants.Contants;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			//セッションからユーザーオブジェクトを取得します。
			Object user = request.getSession().getAttribute(Contants.SESSION_USER);
			/*Userオブジェクトが存在するかどうかを確認し、
			それによってユーザーが既にログイン済みであるかどうかを検証します。*/
			if (user==null) {
				/*プロジェクトのパスを動的に取得するために、request.getContextPath()を使用します。*/
				response.sendRedirect(request.getContextPath());
				return false;
			}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
}
