package edu.pnu.service;

public class MemberNotFoundException extends RuntimeException{
 public MemberNotFoundException(String message) {
        super(message);
    }
}

