package com.example.case_study_m4_team1.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException (String mess){
        super(mess);
    }
}
