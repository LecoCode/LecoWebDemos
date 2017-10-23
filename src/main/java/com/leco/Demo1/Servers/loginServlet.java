package com.leco.Demo1.Servers;

import com.leco.Base.ServletBase;
import com.leco.Demo1.Bean.UserBean;
import com.leco.Demo1.SQLUtil.DataBaseConncetion;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 登录处理类
 */
public class loginServlet extends ServletBase {
    private PrintWriter out;   //输出
    private static  Connection conn; //数据库连接对象
    private String Message; //错误信息

//测试用
//    static {
//        try {
//            conn  = DataBaseConncetion.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            conn  = DataBaseConncetion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            new SQLException("获取数据库连接失败！");
        }

    }

    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            super.doPost(request,response);  //调用基类的doPost
            out = response.getWriter();      //获取输出对象
            UserBean userBean =null;

            String name = request.getParameter("name");//获取输入的用户名
            String password = request.getParameter("pass");//获取输入的密码
            if (isName(name)==-1){
                Message="请输入1-15个字符的用户名";
                request.setAttribute("error",Message);
                RequestDispatcher rd = request.getRequestDispatcher("/views/view_1/index.jsp");
                rd.forward(request,response);
            }
            int pass = isPassword(password);
            if (pass==-1){
                Message="请输入1-6个数字的密码";
                request.setAttribute("error",Message);
                RequestDispatcher rd = request.getRequestDispatcher("/views/view_1/index.jsp");
                rd.forward(request,response);
            }

            userBean=getUserMassage(name);
            //判断用户名是否存在
            System.out.println(userBean);
            if (userBean.getUsername()==null){
                Message="没有这个用户名";
                request.setAttribute("error",Message);
                RequestDispatcher rd = request.getRequestDispatcher("/views/view_1/index.jsp");
                rd.forward(request,response);
            }

            //判断密码是否正确
            System.out.println(pass+":"+userBean.getPassword());
            if (pass!=userBean.getPassword()){
                System.out.println("0");
                Message="密码错误";
                request.setAttribute("error",Message);
                RequestDispatcher rd = request.getRequestDispatcher("/views/view_1/index.jsp");
                rd.forward(request,response);
            }

            out.println("登录成功");
            out.println("欢迎："+userBean.getUsername()+"<br/>");
            out.println("年龄："+userBean.getAge());

    }

    /**
     * 用户名校验
     */
      public int isName(String name){
          int code = 1;
          if (name.length()>15)code=-1;
          return code;
      }

    /**
     * 密码校验
     */
    public int isPassword(String password){
          int pass= -1;
          if (password.matches("$[0-9]^")){
               pass = Integer.parseInt(password);
          }
          return pass;
      }

    /**
     * 获取数据库信息
     * @param id 用户名
     */
    public UserBean getUserMassage(String id){
        PreparedStatement ps = null;
        UserBean userBean = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
            ps.setString(1,id);//通过用户名查询
            ResultSet rs = ps.executeQuery();
            userBean = new UserBean(); //新建用户信息对象
            //迭代赋值
            while (rs.next()){
                userBean.setId(rs.getString("id"));
                userBean.setPassword(rs.getInt("password"));
                userBean.setUsername(rs.getString("username"));
                userBean.setAge(rs.getString("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new SQLException("查询失败");
        }

        return userBean;
    }

    /*---------------------------测试------------------------------*/


    @Test
    public void getUserMassageTest(){
        UserBean leco = getUserMassage("lecolifei");
        System.out.println(leco.getId());
        System.out.println(leco.getPassword());
    }




}