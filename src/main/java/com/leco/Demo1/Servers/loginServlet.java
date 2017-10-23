package com.leco.Demo1.Servers;

import com.leco.Base.ServletBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class loginServlet extends ServletBase {
    public PrintWriter out;

    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            super.doPost(request,response);
            out = response.getWriter();
            String name = request.getParameter("name");
            String pass = request.getParameter("pass");
            out.print("用户名："+name+"<br/>");
            out.print("密码："+pass);

    }
}
