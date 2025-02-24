package com.lloyds.classroom_service.exception;

public class ClassroomNotFoundException extends Exception{

    private static final long serialVersionUID = -4847377249947206599L;

    public ClassroomNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}