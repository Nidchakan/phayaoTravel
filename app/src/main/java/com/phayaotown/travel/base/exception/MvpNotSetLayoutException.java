package com.phayaotown.travel.base.exception;

public class MvpNotSetLayoutException extends RuntimeException {
    public MvpNotSetLayoutException() {
        super("getLayoutView() not return 0");
    }
}

