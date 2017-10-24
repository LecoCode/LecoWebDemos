<%--
  Created by IntelliJ IDEA.
  User: leco
  Date: 17-10-24
  Time: 上午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <%!String error = "";%>
    <%
        if (request.getAttribute("error")!=null){
            error = (String)request.getAttribute("error");
        }
    %>
</head>
<body>

    <form action="/demo2/lignup" method="post">
        用户名：<input type="text" name="id"/><br/>
        密 码：<input type="password" name="password"/><br/>
        确认密码：<input type="password" name="passwordCopy"/><br/>
        姓 名：<input type="text" name="username"/><br/>
        年 龄：<input type="test" name="age"/><br/>
        <input type="submit" value="注册"/>
    </form>
    <h4 style="color: red"><%=error%></h4>

</body>
</html>
