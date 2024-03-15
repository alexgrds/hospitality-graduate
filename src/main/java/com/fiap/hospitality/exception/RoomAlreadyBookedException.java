package com.fiap.hospitality.exception;

public class RoomAlreadyBookedException extends Exception {
    public RoomAlreadyBookedException() {
        super("Room is already booked for this date");
    }
}
