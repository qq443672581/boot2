package cn.dlj1.cms.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * request请求工具类
 * 
 * @author fivewords(443672581@qq.com)
 * @date 2017年12月6日
 */
public class HttpServletRequestUtils {

	/**
	 * 判断一个请求是不是Ajax
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return (null != request.getHeader("X-Requested-With") 
				&& "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString())
				)
				|| null != request.getParameter("isAjax");
	}
	
}
