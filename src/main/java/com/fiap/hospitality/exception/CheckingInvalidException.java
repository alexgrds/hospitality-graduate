package com.fiap.hospitality.exception;

public class CheckingInvalidException extends Exception {
    public CheckingInvalidException() {
        super("Check in must be before the check out");
    }
}
