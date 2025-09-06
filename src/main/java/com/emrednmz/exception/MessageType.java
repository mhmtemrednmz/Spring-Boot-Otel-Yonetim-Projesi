package com.emrednmz.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    TOKEN_IS_EXPIRED("101", "Token is expired"),
    EMAIL_ALREADY_EXISTS("102", "Email already exists"),
    EMAIL_OR_PASSWORD_MISMATCH("103", "Email or password mismatch"),
    NOT_FOUND_REFRESH_TOKEN("104", "Refresh token not found"),
    NOT_FOUND_AMENITY("105", "Amenity not found"),
    NOT_FOUND_HOTEL("106", "Hotel not found"),
    NOT_FOUND_FAVORITE("107", "Favorite not found"),
    NOT_FOUND_USER("108", "User not found"),
    NOT_FOUND_LIKE("109", "Like not found"),
    NOT_FOUND_PAYMENT("110", "Payment not found"),
    NOT_FOUND_RESERVATION("111", "Reservation not found"),
    NOT_FOUND_ROOM("111", "Room not found"),
    INVALID_TOKEN("201", "Invalid token"),
    UNAUTHORIZED_TRANSACTION("202", "Unauthorized transaction"),
    GENERAL_EXCEPTION("999", "General exception");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
