package me.jun.guest.application.exception;

public class GuestNotFoundException extends RuntimeException {

    public GuestNotFoundException() {
        super("There is no account");
    }
}
