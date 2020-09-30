package com.servlet;

/**
 * @author halfOfGame
 * @create 2020-03-19,14:46
 */
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyServlet",urlPatterns = {"/MyServlet"})

public class MyServlet implements Servlet{
    private transient ServletConfig servletConfig;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String servletName = servletConfig.getServletName();
        //网页响应类型，浏览器将其渲染为HTML格式
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html><head></head>" + "<body> Hello from " + servletName + "</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "My Servlet";
    }

    @Override
    public void destroy() {

    }
}