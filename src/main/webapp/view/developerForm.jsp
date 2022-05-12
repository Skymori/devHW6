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
                        <label for="firstName">First name</label>
                        <input type="text" minlength="3" id="firstName" name="firstName" required placeholder="First name.." />
                        <label for="lastName">Last name</label>
                        <input type="text" id="lastName" name="lastName" required placeholder="Last name.." />
                        <label for="sex">Gender</label>
                        <select id="sex" name="sex">
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                        </select>
                        <label for="salary">Salary</label>
                        <input type="number" min="1" step="0.01" id="salary" name="salary" required placeholder="Salary.." />
                        <input type="submit" value="Create" />
                    </form>
                </c:when>

            <c:otherwise>
                <form method="post" action="update">
                    <c:set var="developer" value="${entity}" />
                    <input type="hidden" name="id" value='${developer.id}' />
                    <label for="firstName">First name</label>
                    <input type="text" id="firstName" name="firstName" required value="<c:out value='${developer.firstName}' />" />
                    <label for="lastName">Last name</label>
                    <input type="text" id="lastName" name="lastName" required value="<c:out value='${developer.lastName}'/>" />
                    <label for="sex">Gender</label>
                    <select id="sex" name="sex">
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                    </select>
                    <label for="salary">Salary</label>
                    <input type="number" min="1" step="0.01" id="salary" name="salary" required value="<c:out value='${developer.salary}'/>" />

                    <label>Projects</label>
                    <div class="check">
                        <ul class="ks-cboxtags">
                            <c:set var="developerProjects" value="${developer.projects}"/>
                            <c:forEach var="project" items="${projectList}">
                                <c:if test="${developerProjects.contains(project)}">
                                    <li>
                                        <input type="checkbox" id="project${project.id}" name="projects" checked value="${project.id}">
                                        <label for="project${project.id}">${project.name}</label>
                                    </li>
                                </c:if>
                                <c:if test="${!developerProjects.contains(project)}">
                                    <li>
                                        <input type="checkbox" id="project${project.id}" name="projects" value="${project.id}">
                                        <label for="project${project.id}">${project.name}</label>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>

                    <label>Skills</label>
                    <div class="check">
                        <ul class="ks-cboxtags">
                            <c:set var="developerSkills" value="${developer.skills}"/>
                            <c:forEach var="skill" items="${skillList}">
                                <c:if test="${developerSkills.contains(skill)}">
                                    <li>
                                        <input type="checkbox" id="skill${skill.id}" name="skills" checked value="${skill.id}">
                                        <label for="skill${skill.id}">${skill.branch.name} (${skill.level.name})</label>
                                    </li>
                                </c:if>
                                <c:if test="${!developerSkills.contains(skill)}">
                                    <li>
                                        <input type="checkbox" id="skill${skill.id}" name="skills" value="${skill.id}">
                                        <label for="skill${skill.id}">${skill.branch.name} (${skill.level.name})</label>
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
