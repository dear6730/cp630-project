<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>A2-Login</title>
    <link rel="stylesheet" href="css/style.css" />
    <%
        String loginMessage = (String) request.getAttribute("loginMessage");
        String style = "display:none";
        if(loginMessage != null){
            style = "display:block";
        }
    %>
  </head>
<body class="body1">
    <form action="/stats-web/controller" method="post">
        <input type="hidden" name="command" value="Login" />
        <div class="imgcontainer">
            <img src="image/img_avatar2.png" alt="Avatar" class="avatar" />
        </div>

        <div class="divUserMessage" style="<%=style%>">
            <h3>${loginMessage}</h3>
        </div>

        <div class="container">
            <label for="uname"><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="uname" required />

            <label for="psw"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="psw" required />

            <button type="submit">Login</button>
        </div>
    </form>
</body>
</html>
