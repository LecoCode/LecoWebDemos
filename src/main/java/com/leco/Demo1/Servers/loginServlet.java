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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if (isName(name)==-1||match("\\w{1,15}",name)){
                Message="请输入1-15个字符的用户名";
                Dispatcher(request,response,Message);
            }
            int pass = isPassword(password);
            if (pass==-1){
                Message="请输入1-6个数字的密码";
                Dispatcher(request,response,Message);
            }
            userBean=getUserMassage(name);
            //判断用户名是否存在
            System.out.println(userBean);
            if (userBean.getUsername()==null){
                Message="没有这个用户名";
                Dispatcher(request,response,Message);
            }
            //判断密码是否正确
            System.out.println(pass+":"+userBean.getPassword());
            if (pass!=userBean.getPassword()){
                Message="密码错误";
                Dispatcher(request,response,Message);
            }

            out.println("登录成功");
            out.println("欢迎："+userBean.getUsername()+"<br/>");
            out.println("年龄："+userBean.getAge());

    }
    /**
     * 跳转
     *
     * @param request  请求
     * @param response 响应
     * @param Message  传输的信息
     * @throws ServletException
     * @throws IOException
     */
    public void Dispatcher
            (HttpServletRequest request,HttpServletResponse response,String Message)
            throws ServletException, IOException {
        request.setAttribute("error",Message);
        RequestDispatcher rd = request.getRequestDispatcher("/views/view_1/index.jsp");
        rd.forward(request,response);
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
          if (match("^\\d+$",password)){
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


    /**
     * 字符串校验
     * @param patterStr 正则表达式
     * @param str       输入的字符串
     * @return          是否符合校验
     */
    public boolean match(String patterStr,String str){
        Pattern pattern = Pattern.compile(patterStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    /*---------------------------测试------------------------------*/


    @Test
    public void getUserMassageTest(){
        UserBean leco = getUserMassage("lecolifei");
        System.out.println(leco.getId());
        System.out.println(leco.getPassword());
    }


    @Test
    public void testMattern(){
           String regex = "^(\\d{3,4}-)?\\d{6,8}$";
           if ( match("\\d+", "3123123"))
               System.out.println(1);
           else
               System.out.println(0);

    }

}
