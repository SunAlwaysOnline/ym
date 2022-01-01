package com.yang.ym.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qcy
 * @create 2021/11/12 22:59:15
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    int i = 0;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor.preHandle" + (i++));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor.postHandle" + (i++));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Interceptor.afterCompletion" + (i++));
        if (ex != null) {
            System.out.println(ex.toString());
        }
    }

//    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        HttpServletRequest processedRequest = request;
//        HandlerExecutionChain mappedHandler = null;
//
//        //....
//
//        ModelAndView mv = null;
//        Exception dispatchException = null;
//
//        processedRequest = checkMultipart(request);
//        multipartRequestParsed = (processedRequest != request);
//
//        //从处理器映射中中获取处理器执行链，包含一个主要的处理器以及拦截器
//        mappedHandler = getHandler(processedRequest);
//
//        //由处理器获得处理器适配器
//        HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
//
//        //调用拦截器的preHandle
//        if (!mappedHandler.applyPreHandle(processedRequest, response)) {
//            return;
//        }
//
//        //真正处理请求的逻辑，返回ModelAndView对象
//        mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
//
//        //调用拦截器的postHandle
//        mappedHandler.applyPostHandle(processedRequest, response, mv);
//
//        //将逻辑视图转化为物理视图，渲染视图后，调用拦截器的afterCompletion
//        processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
//    }

}
