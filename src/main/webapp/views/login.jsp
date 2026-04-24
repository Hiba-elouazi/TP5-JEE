<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
    <style>
        body {
            display: flex; align-items: center; justify-content: center;
        }
        .card {
            padding: 48px 40px; width: 100%; max-width: 420px;
        }
        .header { text-align: center; margin-bottom: 36px; }
        .header h1 { font-size: 24px; color: #1a237e; font-weight: 800; }

        .form-group { margin-bottom: 18px; }
        label {
            display: block; font-size: 11px; font-weight: 700; text-transform: uppercase;margin-bottom: 7px;}
        input[type="text"], input[type="password"] {
            width: 100%; padding: 11px 14px; font-size: 12px;}
        .btn-login {
            width: 100%; padding: 13px;
            background: linear-gradient(135deg, #1a237e, #0d47a1);
            color: white; font-size: 15px; font-weight: 700; margin-top: 6px;}
        .alert-error {
            background: #ffebee; padding: 12px 16px;color: #c62828; font-size: 14px; margin-bottom: 22px;}
    </style>
</head>
<body>
<div class="card">
    <div class="header">
        <h1> Gestion des Produits</h1>
    </div>

    <c:if test="${not empty erreur}">
        <div class="alert-error"> ${erreur}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Nom d'utilisateur</label>
            <input type="text" id="username" name="username"
                   value="${username}" placeholder="Entrez votre identifiant"
                   required autofocus />
        </div>
        <div class="form-group">
            <label for="password">Mot de passe</label>
            <input type="password" id="password" name="password"
                   placeholder="Entrez votre mot de passe" required />
        </div>
        <button type="submit" class="btn-login">Se connecter </button>
    </form>
</div>
</body>
</html>