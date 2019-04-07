/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.CarManufacture.bookings.web;

import dhbwka.wwi.vertsys.javaee.CarManufacture.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.CarManufacture.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.CarManufacture.bookings.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.CarManufacture.bookings.ejb.BookingBean;
import dhbwka.wwi.vertsys.javaee.CarManufacture.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.CarManufacture.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.CarManufacture.bookings.jpa.Booking;
import dhbwka.wwi.vertsys.javaee.CarManufacture.bookings.jpa.BookingStatus;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/bookings/booking/*")
public class BookingEditServlet extends HttpServlet {

    @EJB
    BookingBean bookingBean;

    @EJB
    CategoryBean categoryBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("statuses", BookingStatus.values());

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Booking booking = this.getRequestedBooking(request);
        request.setAttribute("edit", booking.getId() != 0);
                                
        if (session.getAttribute("booking_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("booking_form", this.createBookingForm(booking));
           
        }
        request.setAttribute("Modell", "C-Klasse");
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/bookings/booking_edit.jsp").forward(request, response);
        
        session.removeAttribute("booking_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveBooking(request, response);
                break;
            case "delete":
                this.deleteBooking(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String bookingCategory = request.getParameter("booking_category");
        String bookingDueDate = request.getParameter("booking_due_date");
        String bookingDueTime = request.getParameter("booking_due_time");
        String bookingStatus = request.getParameter("booking_status");
        String bookingShortText = request.getParameter("booking_short_text");
        String bookingLongText = request.getParameter("booking_long_text");
        String bookingWerk = request.getParameter("booking_werk");
        String bookingFarbe = request.getParameter("booking_farbe");
        String bookingMotor = request.getParameter("booking_motor");
        

        Booking booking = this.getRequestedBooking(request);

        if (bookingCategory != null && !bookingCategory.trim().isEmpty()) {
            try {
                booking.setCategory(this.categoryBean.findById(Long.parseLong(bookingCategory)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(bookingDueDate);
        Time dueTime = WebUtils.parseTime(bookingDueTime);

        if (dueDate != null) {
            booking.setDueDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            booking.setDueTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        try {
            booking.setStatus(BookingStatus.valueOf(bookingStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }

        booking.setShortText(bookingShortText);
        booking.setLongText(bookingLongText);
        booking.setMotor(bookingMotor);
        booking.setFarbe(bookingFarbe);
        booking.setWerk(bookingWerk);

        this.validationBean.validate(booking, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.bookingBean.update(booking);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/bookings/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("booking_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    private void deleteBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Booking booking = this.getRequestedBooking(request);
        this.bookingBean.delete(booking);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/bookings/list/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Booking getRequestedBooking(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Booking booking = new Booking();
        booking.setOwner(this.userBean.getCurrentUser());
        booking.setDueDate(new Date(System.currentTimeMillis()));
        booking.setDueTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String bookingId = request.getPathInfo();

        if (bookingId == null) {
            bookingId = "";
        }

        bookingId = bookingId.substring(1);

        if (bookingId.endsWith("/")) {
            bookingId = bookingId.substring(0, bookingId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            booking = this.bookingBean.findById(Long.parseLong(bookingId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return booking;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param booking Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createBookingForm(Booking booking) {
        Map<String, String[]> values = new HashMap<>();

        values.put("booking_owner", new String[]{
            booking.getOwner().getUsername()
        });

        if (booking.getCategory() != null) {
            values.put("booking_category", new String[]{
                "" + booking.getCategory().getId()
            });
        }

        values.put("booking_due_date", new String[]{
            WebUtils.formatDate(booking.getDueDate())
        });

        values.put("booking_due_time", new String[]{
            WebUtils.formatTime(booking.getDueTime())
        });

        values.put("booking_status", new String[]{
            booking.getStatus().toString()
        });

        values.put("booking_short_text", new String[]{
            booking.getShortText()
        });

        values.put("booking_long_text", new String[]{
            booking.getLongText()
        });
        
        values.put("booking_werk", new String[]{
            booking.getWerk()
        });
        
        values.put("booking_farbe", new String[]{
            booking.getFarbe()
        });
        
        values.put("booking_motor", new String[]{
            booking.getMotor()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
