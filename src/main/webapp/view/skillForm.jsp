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

                    <label for="branch">Skill branch</label>
                    <select id="branch" name="branch">
                        <option value="JAVA">Java</option>
                        <option value="CPLUS">C++</option>
                        <option value="CSHARP">C#</option>
                        <option value="JS">JS</option>
                    </select>

                    <label for="level">Skill level</label>
                    <select id="level" name="level">
                        <option value="JUNIOR">Junior</option>
                        <option value="MIDDLE">Middle</option>
                        <option value="SENIOR">Senior</option>
                    </select>

                    <input type="submit" value="Create" />

                    </form>
                </c:when>

            <c:otherwise>
                <form method="post" action="update">
                <c:set var="skill" value="${entity}" />
                    <input type="hidden" name="id" value='${skill.id}' />
                    <label for="branch">Skill branch</label>
                    <select id="branch" name="branch">
                        <option value="JAVA">Java</option>
                        <option value="CPLUS">C++</option>
                        <option value="CSHARP">C#</option>
                        <option value="JS">JS</option>
                    </select>

                    <label for="level">Skill level</label>
                    <select id="level" name="level">
                        <option value="JUNIOR">Junior</option>
                        <option value="MIDDLE">Middle</option>
                        <option value="SENIOR">Senior</option>
                    </select>

                    <label>Developers</label>
                    <div class="check">
                        <ul class="ks-cboxtags">
                            <c:set var="skillDevelopers" value="${skill.developers}"/>
                            <c:forEach var="developer" items="${developerList}">
                                <c:if test="${skillDevelopers.contains(developer)}">
                                    <li>
                                        <input type="checkbox" id="developer${developer.id}" name="developers" checked value="${developer.id}">
                                        <label for="developer${developer.id}">${developer.lastName}</label>
                                    </li>
                                </c:if>
                                <c:if test="${!skillDevelopers.contains(developer)}">
                                    <li>
                                        <input type="checkbox" id="developer${developer.id}" name="developers" value="${developer.id}">
                                        <label for="developer${developer.id}">${developer.lastName}</label>
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
