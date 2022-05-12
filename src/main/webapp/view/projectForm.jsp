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
                    <label for="name">Project name</label>
                    <input type="text" id="name" name="name" required placeholder="Project name.." />
                    <label for="description">Project description</label>
                    <input type="text" id="description" name="description" placeholder="Project description.." />
                    <label for="cost">Project cost</label>
                    <input type="number" min="1" step="0.01" id="cost" name="cost" required placeholder="Project cost.." />
                    <input type="submit" value="Create" />
                    </form>
                </c:when>

            <c:otherwise>
                <form method="post" action="update">
                <c:set var="project" value="${entity}" />

                    <input type="hidden" name="id" value='${project.id}' />
                    <label for="name">Project name</label>
                    <input type="text" id="name" name="name" required value="<c:out value='${project.name}' />" />
                    <label for="description">Project description</label>
                    <input type="text" id="description" name="description" value="<c:out value='${project.description}'/>" />
                    <label for="cost">Project cost</label>
                    <input type="number" min="1" step="0.01" id="cost" name="cost" required value="<c:out value='${project.cost}'/>" />

                    <label for="date">Project date</label>
                    <input type="date"  id="date" name="date" value="<c:out value='${project.date}'/>">

                    <label>Customers</label>
                    <div class="check">
                        <ul class="ks-cboxtags">
                            <c:set var="projectCustomers" value="${project.customers}"/>
                            <c:forEach var="customer" items="${customerList}">
                                <c:if test="${projectCustomers.contains(customer)}">
                                    <li>
                                        <input type="checkbox" id="customer${customer.id}" name="customers" checked value="${customer.id}">
                                        <label for="customer${customer.id}">${customer.name}</label>
                                    </li>
                                </c:if>
                                <c:if test="${!projectCustomers.contains(customer)}">
                                    <li>
                                        <input type="checkbox" id="customer${customer.id}" name="customers" value="${customer.id}">
                                        <label for="customer${customer.id}">${customer.name}</label>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>

                    <label>Companies</label>
                    <div class="check">
                        <ul class="ks-cboxtags">
                            <c:set var="projectCompanies" value="${project.companies}"/>
                            <c:forEach var="company" items="${companyList}">
                                <c:if test="${projectCompanies.contains(company)}">
                                    <li>
                                        <input type="checkbox" id="company${company.id}" name="companies" checked value="${company.id}">
                                        <label for="company${company.id}">${company.name}</label>
                                    </li>
                                </c:if>
                                <c:if test="${!projectCompanies.contains(company)}">
                                    <li>
                                        <input type="checkbox" id="company${company.id}" name="companies" value="${company.id}">
                                        <label for="company${company.id}">${company.name}</label>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>

                    <label>Developers</label>
                    <div class="check">
                        <ul class="ks-cboxtags">
                            <c:set var="projectDevelopers" value="${project.developers}"/>
                            <c:forEach var="developer" items="${developerList}">
                                <c:if test="${projectDevelopers.contains(developer)}">
                                    <li>
                                        <input type="checkbox" id="developer${developer.id}" name="developers" checked value="${developer.id}">
                                        <label for="developer${developer.id}">${developer.lastName}</label>
                                    </li>
                                </c:if>
                                <c:if test="${!projectDevelopers.contains(developer)}">
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
