package Filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//注解/过滤器名称/该过滤器对所有请求有效
@WebFilter(filterName = "Filter 00",urlPatterns = "/*")
public class Filter00 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //打印过滤器开始
        System.out.println("Filter 0 - encoding begins");
        //强制类型转换成HttpServletRequest类型
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        //强制类型转换成HttpServletResponse类型
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //获取请求路径
        String path = request.getRequestURI();
        //获取请求的方法
        String method = request.getMethod();
        //如果路径不包含login、js和angular-login2则设置任何编码格式
        if (!path.contains("js") && !path.contains("html")){
            System.out.println(method);
            //设置响应编码格式为utf8
            response.setContentType("text/html;charset=UTF-8");
            //如果是添加和修改方法则设置请求请求对象的编码格式
            if (method.equals("POST")||method.equals("PUT")){
                request.setCharacterEncoding("UTF-8");
            }
        }
        //放行
        filterChain.doFilter(request,response);
        System.out.println("Filter 0 - encoding ends");
    }
    @Override
    public void destroy() {}
}
