<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Bestellungen
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/booking_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/bookings/booking/new/"/>">Bestellung aufgeben</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/bookings/categories/"/>">Neue Fahrzeugkategorie hinzufügen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Modell"/>

            <select name="search_category">
                <option value="">Alle Kategorien</option>

                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}" ${param.search_category == category.id ? 'selected' : ''}>
                        <c:out value="${category.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty bookings}">
                <p>
                    Es wurden keine Besstelung gefunden. 🐈
                    Also bestell etwas! 
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.CarManufacture.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Modell</th>
                            <th>Fahrzeugkategorie</th>
                            <th>Motorisierung</th>
                            <th>Status</th>
                            <th>Ersteller</th>
                            <th>Farbe</th>
                            <th>Erstellungsdatum</th>
                            <th>Werk</th>
                            <th>Ausstattung</th>
                        </tr>
                    </thead>
                    <c:forEach items="${bookings}" var="booking">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/bookings/booking/${booking.id}/"/>">
                                    <c:out value="${booking.shortText}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${booking.category.name}"/>
                            </td>
                            <td>
                                <a href="<c:url value="/app/bookings/booking/${booking.id}/"/>">
                                    <c:out value="${booking.motor}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${booking.status.label}"/>
                            </td>
                            <td>
                                <c:out value="${booking.owner.username}"/>
                            </td>
                            <td>
                                <a href="<c:url value="/app/bookings/booking/${booking.id}/"/>">
                                    <c:out value="${booking.farbe}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(booking.dueDate)}"/>
                                <c:out value="${utils.formatTime(booking.dueTime)}"/>
                            </td>
                            <td>
                                <a href="<c:url value="/app/bookings/booking/${booking.id}/"/>">
                                    <c:out value="${booking.werk}"/>
                                </a>
                            </td>
                            <td>
                                <a href="<c:url value="/app/bookings/booking/${booking.id}/"/>">
                                    <c:out value="${booking.longText}"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>