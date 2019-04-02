<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Mein Profil
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/dashboard.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/profil/edit/"/>">Profil bearbeiten</a>
        </div>
        
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <div>
                Benutzername:
                <span>${user.username}</span>
            </div>
            <div>
                Mitarbeiternummer:
                <span>${user.mitarbeiternummer}</span>
            </div>
            <div>
                Vorname:
                <span>${user.firstName}</span>
            </div>
            <div>
                Nachname:
                <span>${user.lastName}</span>
            </div>
        </div>
    </jsp:attribute>
</template:base>