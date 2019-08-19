<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring CMS home</title>
    <meta name="viewport" content="width=device-width, initioal-scale=1.0">


    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">

</head>
<body>
<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
<div class="container">
    <c:choose>
        <c:when test="${empty article}">
            <p>brak artykułu</p>
        </c:when>
        <c:otherwise>
            <table class="table table-striped table-dark">
                <thead>
                <tr>
                    <th colspan="4">Szczegóły artykułu</th>
                </tr>
                <tr>
                    <th scope="col">Tytuł</th>
                    <th scope="col">Autor</th>
                    <th scope="col">Kategorie</th>
                    <th scope="col">Data dodania</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${article.title}</td>
                        <td>${article.author.firstName} ${article.author.lastName}</td>
                        <td>
                            <c:forEach items="${article.categories}" var="author">
                                - ${author.name}<br>
                            </c:forEach>
                        </td>
                        <td>${article.created}</td>
                    </tr>
                    <tr>
                        <th colspan="4">Treść artykułu</th>
                    </tr>
                    <tr>
                        <td colspan="4">${article.content}</td>
                    </tr>
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/article/edit/${article.id}"><button class="btn btn-dark">edytuj jako artykuł</button></a></td>
                        <td><a href="${pageContext.request.contextPath}/draft/edit/${article.id}"><button class="btn btn-dark">edytuj jako szkic</button></a></td>
                        <td><a href="${pageContext.request.contextPath}/article/delete/${article.id}"><button class="btn btn-dark">usuń</button></a></td>
                    </tr>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
</script>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
</script>
</body>
</html>

