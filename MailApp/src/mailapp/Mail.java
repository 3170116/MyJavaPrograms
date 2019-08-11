/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailApp;

/**
 *
 * @author Jimis
 */
public class Mail {
    private String writer;
    private String subject;
    private String date;
    private String time;
    private String mail;
    private boolean isReceived;

    public Mail(String writer, String subject, String date, String time, String mail, boolean isReceived) {
        this.writer = writer;
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.mail = mail;
        this.isReceived = isReceived;
    }

    public String getWriter() {
        return writer;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMail() {
        return mail;
    }

    public boolean isIsReceived() {
        return isReceived;
    }
    
    @Override
    public String toString() {
        return "From: " + this.writer + " \nAt: " + this.date + "   " + this.time;
    }
}
