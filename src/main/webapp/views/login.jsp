<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif;
               background: linear-gradient(135deg, #1a237e, #0d47a1);
               min-height: 100vh; display: flex;
               align-items: center; justify-content: center; }
        .card { background: white; border-radius: 12px;
                padding: 48px 40px; width: 420px;
                box-shadow: 0 20px 60px rgba(0,0,0,0.3); }
        h2 { color: #1a237e; margin-bottom: 28px; text-align: center; }
        .form-group { margin-bottom: 18px; }
        label { display: block; font-size: 12px; font-weight: 700;
                color: #616161; text-transform: uppercase;
                letter-spacing: 0.8px; margin-bottom: 7px; }
        input { width: 100%; padding: 11px 14px;
                border: 2px solid #e0e0e0; border-radius: 8px;
                font-size: 15px; outline: none; }
        input:focus { border-color: #1a237e; }
        .btn { width: 100%; padding: 13px;
               background: linear-gradient(135deg, #1a237e, #0d47a1);
               color: white; border: none; border-radius: 8px;
               font-size: 15px; font-weight: 700; cursor: pointer;
               margin-top: 8px; }
        .alert { background: #ffebee; border-left: 4px solid #f44336;
                 padding: 12px 16px; border-radius: 6px;
                 color: #c62828; margin-bottom: 20px; font-size: 14px; }
        .info { margin-top: 20px; background: #e8eaf6;
                border-radius: 8px; padding: 14px; font-size: 13px; }
        .info h4 { color: #1a237e; margin-bottom: 8px; font-size: 11px;
                   text-transform: uppercase; letter-spacing: 0.5px; }
        .compte { display: flex; justify-content: space-between;
                  padding: 4px 0; border-bottom: 1px solid #c5cae9; }
        .compte:last-child { border-bottom: none; }
        .badge { padding: 2px 8px; border-radius: 10px;
                 font-size: 10px; font-weight: 700; }
        .admin { background: #ffcdd2; color: #b71c1c; }
        .user  { background: #c8e6c9; color: #1b5e20; }
    </style>
</head>
<body>
<div class="card">
    <h2>📦 Gestion des Produits</h2>
    <p style="text-align:center;color:#757575;font-size:13px;margin-bottom:24px;">
        MVC2 + Hibernate + MySQL
    </p>

    <c:if test="${not empty erreur}">
        <div class="alert">⚠️ ${erreur}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label>Nom d'utilisateur</label>
            <input type="text" name="username" value="${username}"
                   placeholder="Entrez votre identifiant" required autofocus/>
        </div>
        <div class="form-group">
            <label>Mot de passe</label>
            <input type="password" name="password"
                   placeholder="Entrez votre mot de passe" required/>
        </div>
        <button type="submit" class="btn">Se connecter →</button>
    </form>

    <div class="info">
        <h4>🔑 Comptes de test</h4>
        <div class="compte">
            <span><b>admin</b> / admin123</span>
            <span class="badge admin">ADMIN</span>
        </div>
        <div class="compte">
            <span><b>user</b> / user123</span>
            <span class="badge user">USER</span>
        </div>
    </div>
</div>
</body>
</html>