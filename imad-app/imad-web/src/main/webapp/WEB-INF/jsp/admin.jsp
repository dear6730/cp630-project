<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="ec.stats.jpa.model.User"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>A2-Administrators</title>
    <link rel="stylesheet" href="css/style.css" />
    <%
        String addUserMessage = (String) request.getAttribute("addUserMessage");
        String style = "display:none";
        if(addUserMessage != null){
            style = "display:block";
        }

        String userNotFound = (String) request.getAttribute("userNotFound");
        String styleUserNotFound = "display:none";
        if(userNotFound != null){
            styleUserNotFound = "display:block";
        }
    %>

  </head>
  <body>
    <div class="divTitle">
      <h1>Admin Page</h1>
      <button onclick="window.location.href='/stats-web/controller';" class="backbutton">Back to Login</button>
    </div>

    <div class="divAdmin">
      <form action="/stats-web/controller" method="post">
        <input type="hidden" name="command" value="AddUser" />
        <div class="imgcontainer">
          <img src="image/img_avatar2.png" alt="Avatar" class="avatar2" />
        </div>

        <div class="divUserMessage" style="<%=style%>">
            <h3>${addUserMessage}</h3>
        </div>

        <div class="container">
          <label for="uname"><b>Username</b></label>
          <input
            type="text"
            placeholder="Enter Username"
            name="uname"
            required
          />

          <label for="psw"><b>Password</b></label>
          <input
            type="password"
            placeholder="Enter Password"
            name="psw"
            required
          />

          <label for="role"><b>Choose a role:</b></label>
          <select name="role" id="role" required>
            <option></option>
            <option value="1">Administrator</option>
            <option value="2">Developer</option>
            <option value="3">Guest</option>
          </select>

          <button type="submit">Save New User</button>
        </div>
      </form>

      <form action="/stats-web/controller" method="post">
        <input type="hidden" name="command" value="FindUser" />
        <div class="imgcontainer">
          <img src="image/img_avatar2.png" alt="Avatar" class="avatar2" />
        </div>

        <div class="divUserMessage" style="<%=styleUserNotFound%>">
            <h3>${userNotFound}</h3>
        </div>

        <div class="container">
          <label for="uname"><b>Username</b></label>
          <input
            type="text"
            placeholder="Enter Username"
            name="uname"
            required
          />

          <button type="submit">Search User By Name</button>
        </div>
      </form>

      <form action="/stats-web/controller" method="post">
        <input type="hidden" name="command" value="ListAllUsers" />
        <div class="imgcontainer">
          <img src="image/img_avatar2.png" alt="Avatar" class="avatar2" />
        </div>

        <div class="container">
          <button type="submit">List All Users</button>
        </div>
      </form>
    </div>

    <div>
        <% 
        List<User> users = (ArrayList<User>) request.getAttribute("users");
        int usersSize = 0;
        if(users != null && users.size() > 0){
          usersSize = users.size();
        }
        %>
        <h3> Users (<%=usersSize%>)</h3>
        <table>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Role</th>
          </tr>
          <% 
         
            if(users != null) {
                for(User user : users)
                {    
          %>      
          <tr>
            <td><%=user.getId()%></td>
            <td><%=user.getName()%></td>
            <td><%=user.getRoleFormatted()%></td>
          </tr>
          <%
                }
              }
          
          %>
        </table>
    </div>
  </body>
</html>