package com.example.movie_ticket_seller.exception;

public class NotBookedException extends RuntimeException{
    public NotBookedException(String message) {
        super(message);
    }
}
