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
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>branch</th>
        				<th>level</th>
        				<th>developers</th>
        				<th colspan="3" align="center"></th>
        			</tr>
        		</thead>
        		<tbody>
                 <c:forEach var="skill" items="${entities}">
                     <tr>
                         <td>${skill.id}</td>
                         <td>${skill.branch}</td>
                         <td>${skill.level}</td>
                         <td>
                             <div class="tooltip">${skill.developers.size()}
                                <span class="tooltiptext">${skill.developers}</span>
                             </div>
                         </td>
                         <td> <a href="/skills/details?id=${skill.id}"><button>Details</button></a></td>
                         <td> <a href="/skills/edit?id=${skill.id}"><button>Update</button></a></td>
                         <td> <a href="/skills/delete?id=${skill.id}"><button>Delete</button></a></td>
                     </tr>
                 </c:forEach>
        		</tbody>
        	</table>
        </div>
    </body>
</html>
