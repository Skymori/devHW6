<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>PMS</title>
        <script src="https://kit.fontawesome.com/1121c369ff.js" crossorigin="anonymous"></script>
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/table.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/style.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/button.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/form.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/checkbox.css" TITLE="style" />
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <div>
            <c:choose>
                <c:when test="${endpoint == 'new'}">
                    <form method="post" action="create">
                    <label for="name">Customer name</label>
                    <input type="text" id="name" name="name" required placeholder="Customer name.." />
                    <label for="industry">Customer industry</label>
                    <input type="text" id="industry" name="industry" placeholder="Customer industry.." />
                    <input type="submit" value="Create" />
                    </form>
                </c:when>

            <c:otherwise>
                <form method="post" action="update">
                <c:set var="customer" value="${entity}" />
                    <input type="hidden" name="id" value='${customer.id}' />
                    <label for="name">Customer name</label>
                    <input type="text" id="name" name="name" required value="<c:out value='${customer.name}' />" />
                    <label for="industry">Customer industry</label>
                    <input type="text" id="industry" name="industry" value="<c:out value='${customer.industry}'/>" />

                    <label>Projects</label>
                    <div class="check">
                        <ul class="ks-cboxtags">
                            <c:set var="customerProjects" value="${customer.projects}"/>
                            <c:forEach var="project" items="${projectList}">
                                <c:if test="${customerProjects.contains(project)}">
                                    <li>
                                        <input type="checkbox" id="project${project.id}" name="projects" checked value="${project.id}">
                                        <label for="project${project.id}">${project.name}</label>
                                    </li>
                                </c:if>
                                <c:if test="${!customerProjects.contains(project)}">
                                    <li>
                                        <input type="checkbox" id="project${project.id}" name="projects" value="${project.id}">
                                        <label for="project${project.id}">${project.name}</label>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>

                    <input type="submit" value="Update" />
                </form>
            </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
