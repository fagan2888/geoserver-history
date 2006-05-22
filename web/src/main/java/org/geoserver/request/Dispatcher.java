package org.geoserver.request;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.ServletWrappingController;
import org.vfny.geoserver.servlets.AbstractService;
import org.vfny.geoserver.util.requests.readers.KvpRequestReader;

/**
 * Root dispatcher which handles all incoming requests.
 * <p>
 * 
 * </p>
 * 
 * @author Justin Deoliveira, The Open Planning Project, jdeolive@openplans.org
 *
 */
public class Dispatcher extends AbstractController {

	protected ModelAndView handleRequestInternal(
		HttpServletRequest httpRequest, HttpServletResponse httpResponse
	) throws Exception {
		
		if (httpRequest.getQueryString() != null) { 
			//process query string params
			Map kvp = KvpRequestReader.parseKvpSet(httpRequest.getQueryString());
			
			//figure out service and request being processed
			String service = (String) kvp.get("SERVICE");
			String request = (String) kvp.get("REQUEST");
	        
			//map request parameters to a Request bean to handle it 
			Map requests = 
				//getApplicationContext().getBeansOfType(AbstractService.class);
				getApplicationContext().getParent().getBeansOfType(AbstractService.class);
			
			AbstractService target = null;
			
			
			for (Iterator itr = requests.entrySet().iterator(); itr.hasNext();) {
				Map.Entry entry = (Entry) itr.next();
				String id = (String) entry.getKey();
				AbstractService bean = (AbstractService) entry.getValue();
				
				if (bean.getService().equalsIgnoreCase(service) && 
					bean.getRequest().equalsIgnoreCase(request)) {
					
					//we have a winner
					target = bean;
					break;
				}
			}
			
			if (target != null) {

//				//delegate to parent to recreate bean, provide lifecycle, etc...
//				ServletWrappingController delegate = 
//					new ServletWrappingController();
//				
//				delegate.setApplicationContext(getApplicationContext());
//				delegate.setBeanName(target.getRequest());
//				delegate.setServletClass(target.getClass());
//				delegate.setServletName(target.getRequest());
//				delegate.afterPropertiesSet();
//				
//				delegate.handleRequest(httpRequest,httpResponse);
				target.doGet(httpRequest,httpResponse);
				
			}
			else {
				
			}
		}
		
		return null;
	}

}
