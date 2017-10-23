<%--
  Created by IntelliJ IDEA.
  User: leco
  Date: 17-10-23
  Time: 下午2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%!
    String error="";
%>
<%
    if (request.getAttribute("error")!=null){
        error= (String)request.getAttribute("error");
        request.setAttribute("error","");
    }

%>
<head>
    <title>登录</title>
</head>
<body>
   <form action="/demo1/login" method="post">
       用户名:<input type="text" name="name"/><br/>
       密码：<input type="password" name="pass"><br/>
       <input type="submit" value="登录">
   </form>
   <h4 style="color: crimson"><%=error%></h4>


</body>
</html>
