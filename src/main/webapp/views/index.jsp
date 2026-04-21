<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Produits - MVC2</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif; background: #f0f2f5; color: #212121; }
        .navbar {
            background: blue;
            color: white; padding: 0 32px; height: 60px;
            display: flex; justify-content: space-between; align-items: center; position: sticky; top: 0; z-index: 100;}
        .badge { padding: 3px 10px; border-radius: 20px; font-size: 9px; font-weight: 800; text-transform: uppercase; }
        .badge-admin { background: #ffcdd2; color: black; }
        .badge-user  { background: #c8e6c9; color: black; }
        .btn-logout {
            background: green; color: white;padding: 6px 16px;cursor: pointer;font-size: 13px;}
        .container { max-width: 1200px; margin: 28px auto; padding: 0 24px; }
        .alert { padding: 13px 18px; border-radius: 8px; margin-bottom: 22px; font-size: 14px; }
        .alert-success { background: #e8f5e9; border-left: 4px solid #43a047; color: #2e7d32; }
        .alert-error   { background: #ffebee; border-left: 4px solid #e53935; color: #c62828; }
        .alert-info    { background: #e3f2fd; border-left: 4px solid #1e88e5; color: #0d47a1; }
        .layout { display: grid; grid-template-columns: 320px 1fr; gap: 24px; align-items: start; }
        .card { background: white; border-radius: 12px; padding: 28px;}
        .card-title {
            font-size: 16px; font-weight: 700; color: #1a237e;margin-bottom: 22px; padding-bottom: 14px;border-bottom: 2px solid #e8eaf6; }
        .form-group { margin-bottom: 16px; }
        .form-group label {
            display: block; font-size: 11px; font-weight: 700; margin-bottom: 7px;}
        .form-group input {
            width: 100%; padding: 10px 13px;font-size: 14px;   
        }
        .btn {
            width: 100%; padding: 11px; font-size: 14px; font-weight: 700; cursor: pointer; 
        }
        .btn-primary { background: blue; color: white; }
        .btn-update  { background: linear-gradient(135deg, #e65100, #f57c00); color: white; }
        .btn-cancel  {
            display: block; text-align: center; margin-top: 8px;padding: 10px; background: #f5f5f5; color: #616161;border: 2px solid #e0e0e0; font-weight: 600; font-size: 14px;
        }
        .info-panel { background: #e8eaf6;; padding: 18px;font-size: 14px; color: #3949ab; line-height: 1.7;}
        .search-bar { display: flex; gap: 8px; margin-bottom: 18px; flex-wrap: wrap; }
        .search-bar input { flex: 1; min-width: 140px; padding: 9px 14px;font-size: 14px;  }
        .btn-search {
            padding: 9px 18px; background: blue; color: white; cursor: pointer; font-size: 14px; font-weight: 600;
        }
        .btn-reset {
            padding: 9px 14px; background: #eeeeee; color: #424242;cursor: pointer;font-size: 14px;
            display: inline-flex; align-items: center;}
        .result-count {
            font-size: 13px; margin-bottom: 14px;padding: 5px 12px; background: #f5f5f5; display: inline-block; }
   
        thead tr { background: blue; color: white; }
        thead th {
            padding: 13px 16px; text-align: left;
            font-size: 12px; font-weight: 700;
            text-transform: uppercase; letter-spacing: 0.6px;
        }
        tbody tr { border-bottom: 1px solid #f0f0f0;}
        tbody td { padding: 13px 16px; font-size: 14px; color: #424242; }
        .td-id   {font-size: 12px; font-weight: 600; }
        .td-nom  { font-weight: 700; color: #212121; }
        .td-desc { font-size: 13px; }
        .td-prix { font-weight: 800; white-space: nowrap; }
        .actions { display: flex; gap: 6px; }
        .btn-edit {
            padding: 5px 12px; background: #ff9800; color: white; border-radius: 5px; cursor: pointer;
            font-size: 12px; font-weight: 600; }
        .btn-delete {
            padding: 5px 12px; background: #f44336; color: white; border-radius: 5px; cursor: pointer;
            font-size: 12px; font-weight: 600;}
        .empty-state { text-align: center; padding: 52px 24px; color: #bdbdbd; }
        @media (max-width: 768px) { .layout { grid-template-columns: 1fr; } }
    </style>
</head>
<body>
<nav class="navbar">
    <div class="navbar-brand">Gestion des Produits</div>
    <div class="navbar-user">
         <strong>${sessionScope.userConnecte.username}</strong>
        <c:choose>
            <c:when test="${sessionScope.role == 'ADMIN'}">
                <span class="badge badge-admin">Admin</span>
            </c:when>
            <c:otherwise>
                <span class="badge badge-user">User</span>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/login?logout=true" class="btn-logout">Déconnexion</a>
    </div>
</nav>

<div class="container">
 
    <c:if test="${not empty param.messageSucces}">
        <div class="alert alert-success">${param.messageSucces}</div>
    </c:if>
    <c:if test="${not empty messageErreur}">
        <div class="alert alert-error">${messageErreur}</div>
    </c:if>
    <c:if test="${not empty messageInfo}">
        <div class="alert alert-info">${messageInfo}</div>
    </c:if>

    <div class="layout">

        <div>
            <c:choose>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <div class="card">
                        <div class="card-title">
                            <c:choose>
                                <c:when test="${produitEdit != null}">Modifier le produit</c:when>
                                <c:otherwise>Ajouter un produit</c:otherwise>
                            </c:choose>
                        </div>
                        <form action="${pageContext.request.contextPath}/produits" method="post">
                            <input type="hidden" name="action" value="${produitEdit != null ? 'update' : 'add'}" />
                            <div class="form-group">
                                <label>Nom du produit *</label>
                                <input type="text" name="nom" value="${produitEdit.nom}" required />
                            </div>
                            <div class="form-group">
                                <label>Description *</label>
                                <input type="text" name="description" value="${produitEdit.description}" required />
                            </div>
                            <div class="form-group">
                                <label>Prix (MAD) *</label>
                                <input type="number" name="prix" step="0.01" min="0" required />
                            </div>
                            <button type="submit" class="btn ${produitEdit != null ? 'btn-update' : 'btn-primary'}">
                                <c:choose>
                                    <c:when test="${produitEdit != null}">Mettre à jour</c:when>
                                    <c:otherwise>Ajouter le produit</c:otherwise>
                                </c:choose>
                            </button>
                            <c:if test="${produitEdit != null}">
                                <a href="${pageContext.request.contextPath}/listProduits" class="btn-cancel">Annuler</a>
                            </c:if>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="card">
                        <div class="card-title">mode consultation</div>
                        <div class="info-panel">
                            Les actions d'ajout, modification et suppression sont reservees aux <strong>ADMIN</strong>.
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="card">
            <div class="card-title">Liste des produits</div>

            <form action="${pageContext.request.contextPath}/produits" method="get">
                <div class="search-bar">
                    <input type="number" name="idProduit" placeholder="Recherche par ID..." min="1" />
                    <input type="text"   name="motCle"    value="${motCle}" placeholder="Nom ou description..." />
                    <button type="submit" class="btn-search">Chercher</button>
                    <a href="${pageContext.request.contextPath}/listProduits" class="btn-reset">Reset</a>
                </div>
            </form>

            <c:if test="${not empty listeProduits}">
                <span class="result-count">${listeProduits.size()} produit(s)</span>
            </c:if>

            <c:choose>
                <c:when test="${not empty listeProduits}">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th><th>Nom</th><th>Description</th><th>Prix</th>
                                <c:if test="${sessionScope.role == 'ADMIN'}"><th>Actions</th></c:if>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${listeProduits}">
                                <tr>
                                    <td class="td-id">#${p.idProduit}</td>
                                    <td class="td-nom">${p.nom}</td>
                                    <td class="td-desc">${p.description}</td>
                                    <td class="td-prix">${p.prix} MAD</td>
                                    <c:if test="${sessionScope.role == 'ADMIN'}">
                                        <td>
                                            <div class="actions">
                                                <a href="${pageContext.request.contextPath}/produits?action=edit&id=${p.idProduit}">
                                                    <button class="btn-edit">Modifier</button>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/produits?action=delete&id=${p.idProduit}"
                                                   onclick="return confirm('Supprimer « ${p.nom} » ?')">
                                                    <button class="btn-delete">Supprimer</button>
                                                </a>
                                            </div>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <p>Aucun produit a afficher.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>