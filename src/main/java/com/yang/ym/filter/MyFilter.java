package com.yang.ym.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * @author qcy
 * @create 2021/11/12 22:09:13
 */

@Component
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter.init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter.doFilter.pre");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Filter.doFilter.post");
    }

    @Override
    public void destroy() {
        System.out.println("Filter.destroy");
    }

}

//    public final void invoke(Request request, Response response) throws IOException, ServletException {
//
//        //为当前请求创建filterChain
//        ApplicationFilterChain filterChain =
//                ApplicationFilterFactory.createFilterChain(request, wrapper, servlet);
//
//        //执行filterChain的doFilter方法
//        if ((servlet != null) && (filterChain != null)) {
//            filterChain.doFilter(request.getRequest(), response.getResponse());
//        }
//
//    }

//    public static ApplicationFilterChain createFilterChain(ServletRequest request, Wrapper wrapper, Servlet servlet) {
//        //初始化ApplicationFilterChain实例
//        ApplicationFilterChain filterChain = new ApplicationFilterChain();
//        //设置Servlet
//        filterChain.setServlet(servlet);
//
//        //获取容器中所有的过滤器
//        StandardContext context = (StandardContext) wrapper.getParent();
//        FilterMap filterMaps[] = context.findFilterMaps();
//
//        String servletName = wrapper.getName();
//
//        //按照路径匹配，匹配成功则添加到filterChain中
//        for (int i = 0; i < filterMaps.length; i++) {
//            if (!matchDispatcher(filterMaps[i], dispatcher)) {
//                continue;
//            }
//            if (!matchFiltersURL(filterMaps[i], requestPath))
//                continue;
//            ApplicationFilterConfig filterConfig = (ApplicationFilterConfig)
//                    context.findFilterConfig(filterMaps[i].getFilterName());
//            filterChain.addFilter(filterConfig);
//        }
//
//        //按servlet名称匹配，匹配成功则添加到filterChain中
//        for (int i = 0; i < filterMaps.length; i++) {
//            if (!matchDispatcher(filterMaps[i], dispatcher)) {
//                continue;
//            }
//            if (!matchFiltersServlet(filterMaps[i], servletName))
//                continue;
//            ApplicationFilterConfig filterConfig = (ApplicationFilterConfig)
//                    context.findFilterConfig(filterMaps[i].getFilterName());
//            filterChain.addFilter(filterConfig);
//        }
//
//        return filterChain;
//    }


//    private void internalDoFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
//        //pos为filterChain中正在执行的过滤器的下标
//        //n为filterChain中过滤器的个数
//        if (pos < n) {
//            ApplicationFilterConfig filterConfig = filters[pos++];
//            Filter filter = filterConfig.getFilter();
//            //执行过滤器的doFilter方法，并传入当前filterChain实例
//            filter.doFilter(request, response, this);
//            return;
//        }
//
//        //filterChain中所有的过滤器执行完毕后，执行servlet的service方法
//        servlet.service(request, response);
//    }
