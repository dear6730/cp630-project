<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="ec.stats.jpa.model.Model"%>
<%@page import="ec.stats.sb.StatsSummary"%>
<%@page import="org.apache.commons.lang3.SerializationUtils"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>A2-Guest</title>
    <link rel="stylesheet" href="css/style.css" />
    <%
        String modelNotFound = (String) request.getAttribute("modelNotFound");
        String styleModelNotFound = "display:none";
        if(modelNotFound != null){
          styleModelNotFound = "display:block";
        }
    %>

  </head>
  <body>
    <div class="divTitle">
      <h1>Guest Page</h1>
      <button onclick="window.location.href='/stats-web/controller';" class="backbutton">Back to Login</button>
    </div>

    <div class="divAdmin">
      <form action="/stats-web/controller" method="post">
        <input type="hidden" name="command" value="FindModel" />
        <div class="imgcontainer">
          <img src="image/img_avatar2.png" alt="Avatar" class="avatar2" />
        </div>

        <div class="divUserMessage" style="<%=styleModelNotFound%>">
            <h3>${modelNotFound}</h3>
        </div>

        <div class="container">
          <label for="uname"><b>Model name</b></label>
          <input
            type="text"
            placeholder="Enter model name"
            name="modelname"
            required
          />

          <button type="submit">Search Model By Name</button>
        </div>
      </form>
    </div>
    <div>
      <% 
      List<Model> models = (ArrayList<Model>) request.getAttribute("models");
      int modelsSize = 0;
      if(models != null && models.size() > 0){
        modelsSize = models.size();
      }
      %>
      <h3> Models (<%=modelsSize%>)</h3>
      <table>
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Classname</th>
          <th>Object</th>
          <th>Date</th>
        </tr>
        <% 
       
          if(models != null) {
              for(Model model : models)
              {    
                StatsSummary statsSummary = SerializationUtils.deserialize(model.getObject());
                String message = statsSummary.toString();
        %>      
        <tr>
          <td><%=model.getId()%></td>
          <td><%=model.getName()%></td>
          <td><%=model.getClassname()%></td>
          <td><%=message%></td>
          <td><%=model.getDate()%></td>
        </tr>
        <%
              }
            }
        
        %>
      </table>
  </div>
  </body>
</html>