<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur </title>
  
</head>
<body>
<div class="error-card">
    <%
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
         if (statusCode == null) {
            statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        }
        String errorMsg = (String) request.getAttribute("jakarta.servlet.error.message");

        String title = "Une erreur est survenue";
        if (statusCode != null) {
            if (statusCode == 404) { title = "Page introuvable"; }
            else if (statusCode == 403) { title = "Acces interdit"; }
            else if (statusCode == 500) { title = "Erreur interne"; }
        }
    %>

    <c:choose> 
        <c:when test="${not empty messageErreur}">
            <div class="error-code">403</div>
            <div class="divider"></div>
            <div class="error-title">Acces refuse</div>
            <div class="alert-denied">${messageErreur}</div>
        </c:when>
 
        <c:otherwise>
            <% if (statusCode != null) { %>
                <div class="error-code"><%= statusCode %></div>
            <% } %>
            <div class="divider"></div>
            <div class="error-title"><%= title %></div>
            <% if (errorMsg != null && !errorMsg.isEmpty()) { %>
                <div class="detail-box"><strong>Detail :</strong> <%= errorMsg %></div>
            <% } else { %>
                <div class="error-message">
                    La page demandee est introuvable ou une erreur interne s'est produite.
                </div>
            <% } %>
        </c:otherwise>
    </c:choose>

    <div class="btns">
        <a href="${pageContext.request.contextPath}/listProduits" class="btn-home">]Accueil</a>
        <a href="javascript:history.back()" class="btn-back">Retour</a>
    </div>
</div>
</body>
</html>