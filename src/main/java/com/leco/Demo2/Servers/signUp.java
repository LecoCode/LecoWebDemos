package com.leco.Demo2.Servers;

import com.leco.Base.ServletBase;
import com.leco.Demo2.Bean.UserBean;
import com.leco.SQLUtil.DataBaseConncetion;
import org.junit.Test;
import sun.rmi.server.Dispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUp extends ServletBase{
    private String [] inputstr = {"id","password","passwordCopy","username","age"}; //表单字段数组
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        super.doPost(request,response);
        System.out.println(request.getParameter("id"));
        UserBean analysis = analysis(request);
        //判断返回的用户对象是否为空
        if (analysis!=null){
            //如果不等于空检查两个密码是否一致
            if (analysis.getPassword()!=analysis.getPasswordCopy()){
                System.out.println(analysis.getPassword()+":"+analysis.getPasswordCopy());
                String ErrorMessage = "两个密码不一致";
                Dispatcher(request,response,ErrorMessage);
            }else{
            try {
                //向数据库存入数据
                Connection conn = DataBaseConncetion.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement("INSERT User VALUE(?,?,?,?)");
                ps.setString(1,analysis.getId());
                ps.setInt(2,analysis.getPassword());
                ps.setString(3,analysis.getUsername());
                ps.setString(4,analysis.getAge());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
                PrintWriter out = response.getWriter();
            out.println("恭喜"+analysis.getId()+"用户");
            out.println("注册成功");
            }
        }else {
            //如果对象等于空返回错误信息
            String ErrorMessage = "输入为空";
            Dispatcher(request,response,ErrorMessage);
        }
 }

    /**
     * 跳转
     * @param request
     * @param response
     * @param ErrorMessage //跳转的信息
     * @throws ServletException
     * @throws IOException
     */
        public void Dispatcher
                (HttpServletRequest request,
                 HttpServletResponse response,String ErrorMessage)
                throws ServletException, IOException {
            request.setAttribute("error",ErrorMessage);
            RequestDispatcher rd =
                    request.getRequestDispatcher("/views/view_2/index.jsp");
            rd.forward(request,response);
        }
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doPost(request, response);
    }

    /**
     * 解析传入数据
     */
    public UserBean analysis(HttpServletRequest request){
        UserBean userBean = new UserBean();
        //通过表单字段数据遍历输入数据
        for (String input : inputstr){
            //检查表单数据是否等于空
            if (request.getParameter(input)==null){
                System.out.println(1);
                return null;
            }
            //找查密码字段
            if (input.substring(0,2).equals("pa")){
                //判断密码是否全都是数字组成
                if (!matcher("\\d+",request.getParameter(input))){
                    return null;
                }
            }
        }

        userBean.setId(request.getParameter("id"));
        userBean.setPassword(Integer.parseInt(request.getParameter("password")));
        userBean.setPasswordCopy(Integer.parseInt(request.getParameter("passwordCopy")));
        userBean.setUsername(request.getParameter("username"));
        userBean.setAge(request.getParameter("age"));
        return userBean;
    }

    /**
     * 正则校验
     */

    public boolean matcher(String patternstr,String str){
        Pattern pattern = Pattern.compile(patternstr);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /*--------------------------测试-----------------------------*/


    public void toUserBean(UserBean userBean){

        System.out.println(userBean.getId());
        System.out.println(userBean.getPassword());
        System.out.println(userBean.getPasswordCopy());

    }

    @Test
    public void isMatcher(){
        System.out.println(matcher("\\d+", "123123"));
    }

}
