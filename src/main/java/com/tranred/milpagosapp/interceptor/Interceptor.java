package com.tranred.milpagosapp.interceptor;

import com.tranred.milpagosapp.domain.Usuario;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *  Clase actua mecanismo para interceptar una solicitud http.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */
public class Interceptor implements HandlerInterceptor  {
    
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println("Pre-handle");
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
                HttpSession misession = request.getSession(true);             
                List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
                String vista = modelAndView.getViewName();
                
                if(!"login".equals(vista) && !"finSesion".equals(vista)){
                    if(misession.getAttribute("usuario.datos") == null){                    
                        response.sendRedirect("finSesion.htm");
                    }
                }                
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("After completion handle");
	}
}
