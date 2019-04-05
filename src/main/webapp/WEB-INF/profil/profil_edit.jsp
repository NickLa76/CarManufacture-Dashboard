<%-- 
    Document   : profil_edit
    Created on : 02.04.2019, 21:53:47
    Author     : justi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Mein Profil Bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/form.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/profil/"/>">zurück zum Profil</a>
        </div>

    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="profil_username">
                        Benutzername:
                    </label>
                    <input type="text" name="profil_username" value="${user.username}" readonly="readonly">
                    
                    <label for="profil_mitarbeiternummer">
                        Mitarbeiternummer:
                        <span class="required">*</span>
                    </label>
                    <c:choose>
                        <c:when test="${empty profil_form}">
                            <input type="text" name="profil_mitarbeiternummer" value="${user.mitarbeiternummer}">
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="profil_mitarbeiternummer" value="${profil_form.values['profil_mitarbeiternummer'][0]}">
                        </c:otherwise>
                    </c:choose>
                    
                    <label for="profil_vorname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <c:choose>
                        <c:when test="${empty profil_form}">
                            <input type="text" name="profil_vorname" value="${user.firstName}">
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="profil_vorname" value="${profil_form.values['profil_vorname'][0]}">
                        </c:otherwise>
                    </c:choose>
                    <label for="profil_nachname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <c:choose>
                        <c:when test="${empty profil_form}">
                            <input type="text" name="profil_nachname" value="${user.lastName}">
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="profil_nachname" value="${profil_form.values['profil_nachname'][0]}">
                        </c:otherwise>
                    </c:choose>
                    
                    <button type="submit" name="action" value="save">Änderung speichern</button>
                </div>
                
                <%-- Fehlermeldungen --%>
                <c:if test="${!empty profil_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${profil_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
                    
            <a href="<c:url value="/app/profil/"/>">Zurück zum Profil</a>  
            
        </div>
    </jsp:attribute>
</template:base>
