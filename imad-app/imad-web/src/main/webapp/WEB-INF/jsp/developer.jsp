<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>A2-Developer</title>
    <link rel="stylesheet" href="css/style.css" />
    <%
        String addModelMessage = (String) request.getAttribute("addModelMessage");
        String style = "display:none";
        if(addModelMessage != null){
            style = "display:block";
        }
    %>
  </head>
  <body>
    <div class="divTitle">
      <h1>Developer Page</h1>
      <button onclick="window.location.href='/stats-web/controller';" class="backbutton">Back to Login</button>
    </div>
  
    <div class="divAdmin">
      <form action="/stats-web/controller" method="post">
        <input type="hidden" name="command" value="SaveModel" />
        <div class="imgcontainer">
          <img src="image/img_avatar2.png" alt="Avatar" class="avatar2" />
        </div>
  
        <div class="divUserMessage" style="<%=style%>">
          <h3>${addModelMessage}</h3>
        </div>
  
        <div class="container">
          <label for="value"><b>Model name</b></label>
          <input type="text" placeholder="Enter model name" name="value" required />
  
          <button type="submit">Save New Model</button>
        </div>
      </form>
    </div>
  </body>
</html>