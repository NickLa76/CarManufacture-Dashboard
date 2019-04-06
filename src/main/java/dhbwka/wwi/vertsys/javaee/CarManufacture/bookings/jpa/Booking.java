/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.CarManufacture.bookings.jpa;

import dhbwka.wwi.vertsys.javaee.CarManufacture.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "booking_ids")
    @TableGenerator(name = "booking_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Die Aufgabe muss einem Benutzer geordnet werden.")
    private User owner;

    @ManyToOne
    private Category category;

    @Column(length = 50)
    @NotNull(message = "Das Modell darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Das Modell muss zwischen ein und 50 Zeichen lang sein.")
    private String shortText;
    
    @Column(length = 50)
    @NotNull(message = "Die Farbe darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Die Farbe muss zwischen ein und 50 Zeichen lang sein.")
    private String farbe;
     
    @Column(length = 50)
    @NotNull(message = "Das Werk darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Das Modell muss zwischen ein und 50 Zeichen lang sein.")
    private String werk;
    
    @Column(length = 50)
    @NotNull(message = "Die Motorisierung darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Die Motorisierung muss zwischen ein und 50 Zeichen lang sein.")
    private String motor; 

    @Lob
    @NotNull
    private String longText;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date dueDate;

    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    private Time dueTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BookingStatus status = BookingStatus.OPEN;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Booking() {
    }

    public Booking(String shortText, Category category, String motor, BookingStatus status, Date dueDate, Time dueTime, User owner, String farbe, String longText, String werk) {
        this.owner = owner;
        this.category = category;
        this.shortText = shortText;
        this.longText = longText;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.motor = motor;
        this.status = status;
        this.werk = werk;
        this.farbe = farbe;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    
    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }
    
   public String getWerk() {
        return werk;
    }

    public void setWerk(String werk) {
        this.werk = werk;
    }
    
    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    
    
    //</editor-fold>

}
