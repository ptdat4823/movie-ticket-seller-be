package com.example.movie_ticket_seller.exception;

public class AlreadyExistedException extends RuntimeException{
    public AlreadyExistedException(String message) {
        super(message);
    }
}
