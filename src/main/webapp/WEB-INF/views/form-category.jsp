<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <div class="bs-callout centered">
        <form:form  method="post" modelAttribute="category">
            <div class="form-group">
                <label for="name"><h4>Nazwa Kategorii</h4></label>
                <form:input path="name" class="form-control" id="name" placeholder="podaj nazwę kategorii"/>
            </div>
            <form:errors path="name" cssClass="error" element="div"/>
            <div class="form-group">
                <label for="description"><h4>Opis</h4></label>
                <form:textarea path="description" class="form-control" id="description" placeholder="opis kategorii..."/>
            </div>
            <button type="submit" class="btn btn-secodary btn-block">Wyślij</button>
            <button class="btn btn-secodary btn-block"><a href="${pageContext.request.contextPath}/">Rezygnuj</a></button>
        </form:form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
</script>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
</script>
</body>
</html>
