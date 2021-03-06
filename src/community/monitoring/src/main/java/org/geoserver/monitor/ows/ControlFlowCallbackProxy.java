/* Copyright (c) 2001 - 2011 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.monitor.ows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.geoserver.monitor.Monitor;
import org.geoserver.monitor.RequestData;
import org.geoserver.monitor.RequestData.Status;
import org.geoserver.ows.DispatcherCallback;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ControlFlowCallbackProxy implements InvocationHandler, BeanPostProcessor {

    Object target;
    Monitor monitor;
    
    public ControlFlowCallbackProxy(Monitor monitor) {
        this.monitor = monitor;
    }
    
    public ControlFlowCallbackProxy(Monitor monitor, Object target) {
        this.monitor = monitor;
        this.target = target;
    }
    
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if ("ControlFlowCallback".equals(bean.getClass().getSimpleName())) {
            //wrap the control flow in a proxy
            bean = Proxy.newProxyInstance(bean.getClass().getClassLoader(), 
                new Class[]{DispatcherCallback.class}, new ControlFlowCallbackProxy(monitor, bean));
        }
        
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("operationDispatched".equals(method.getName()) && monitor.current() != null) {
            RequestData data = monitor.current();
            if (data == null) {
                // means monitor is configured but inactive
                return method.invoke(target, args); 
            }

            data.setStatus(Status.WAITING);
            monitor.update();
            
            Object result = method.invoke(target, args);
            data.setStatus(Status.RUNNING);
            monitor.update();
            return result;
        }
        else {
            return method.invoke(target, args);
        }
        
    }

}
