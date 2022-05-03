package com.tranred.milpagosapp.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  Clase actua como controlador de la página en mantenimiento de la aplicación.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class SinServicioController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(value="/mantenimiento.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    

        return new ModelAndView("mantenimiento");
    }
    
}
