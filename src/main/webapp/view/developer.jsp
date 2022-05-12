<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>PMS</title>
        <script src="https://kit.fontawesome.com/1121c369ff.js" crossorigin="anonymous"></script>
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/table.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/style.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/tooltip.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/button.css" TITLE="style" />
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <c:set var="developer" value="${entity}" />
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>firstName</th>
        				<th>lastName</th>
        				<th>sex</th>
        				<th>salary</th>
        				<th>projects</th>
        				<th>skills</th>
        				<th colspan="2" align="center"></th>
        			</tr>
        		</thead>
        		<tbody>
                     <tr>
                         <td>${developer.id}</td>
                         <td>${developer.firstName}</td>
                         <td>${developer.lastName}</td>
                         <td>${developer.sex}</td>
                         <td>${developer.salary}</td>
                         <td>${developer.projects}</td>
                         <td>${developer.skills}</td>
                         <td> <a href="/developers/edit?id=${developer.id}"><button>Update</button></a></td>
                         <td> <a href="/developers/delete?id=${developer.id}"><button>Delete</button></a></td>
                     </tr>
        		</tbody>
        	</table>
        </div>
    </body>
</html>
