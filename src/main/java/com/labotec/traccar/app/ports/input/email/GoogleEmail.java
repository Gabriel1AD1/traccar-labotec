package com.labotec.traccar.app.ports.input.email;

public interface GoogleEmail {
    void sendEmail(String to, String subject, String body);
}
