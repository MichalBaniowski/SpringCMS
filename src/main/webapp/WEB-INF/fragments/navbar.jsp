<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Spring CMS</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown">
                <button class="nav-link btn-dark dropdown-toggle" type="button" id="dropdownAllMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Wszystkie
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownAllMenuButton">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/categories">Kategorie</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/articles">Artykuły</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/drafts">Szkice artykułów</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/authors">Autorzy</a>
                </div>
            </li>

            <li class="nav-item dropdown">
                <button class="nav-link btn-dark dropdown-toggle" type="button" id="dropdownAddMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Dodaj
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownAddMenuButton">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/author/add">Dodaj autora</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/category/add">Dodaj kategorię</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/article/form">Dodaj artykuł</a>
                </div>
            </li>

            <c:if test="${not empty categories}">
                <li class="nav-item dropdown">
                    <button class="nav-link btn-dark dropdown-toggle" type="button" id="dropdownCategoryMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Wyszukaj arytkuły po kategorii
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownCategoryMenuButton">
                        <c:forEach items="${categories}" var="author">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/articles/by-category/${author.id}">${author.name}</a>
                        </c:forEach>
                    </div>
                </li>
            </c:if>
            <c:if test="${not empty authors}">
                <li class="nav-item dropdown">
                    <button class="nav-link btn-dark dropdown-toggle" type="button" id="dropdownAuthorMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Wyszukaj artykuły po autorze
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownAuthorMenuButton">
                        <c:forEach items="${authors}" var="author">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/articles/by-author/${author.id}">${author.firstName} ${author.lastName}</a>
                        </c:forEach>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
