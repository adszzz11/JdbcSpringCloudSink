package com.example.dataflow.JdbcSpringCloudSink.exception;

import java.rmi.NotBoundException;

public class PayloadNotBoundException extends NotBoundException {
    public PayloadNotBoundException(String s) {
        super(s);
    }

    public PayloadNotBoundException() {
        super();
    }
}
