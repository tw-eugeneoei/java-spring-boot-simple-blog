/* (C)2022 */
package com.example.simpleblog.dto;

// public class ErrorDetails {
//    private Date date;
//    private String message;
//    private String details;
//
//    public ErrorDetails(Date date, String message, String details) {
//        this.date = date;
//        this.message = message;
//        this.details = details;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getDetails() {
//        return details;
//    }
// }

public class ErrorDetails {
    private String message;

    public ErrorDetails(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
