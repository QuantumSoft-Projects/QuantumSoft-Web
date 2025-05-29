package com.QuantomSoft.Exception;

public class NewsNotFound extends RuntimeException{
    public NewsNotFound(String message){
        super(message);
    }
}