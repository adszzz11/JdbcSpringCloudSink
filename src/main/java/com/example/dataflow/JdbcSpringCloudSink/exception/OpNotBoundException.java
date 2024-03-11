package com.example.dataflow.JdbcSpringCloudSink.exception;

import java.rmi.NotBoundException;

public class OpNotBoundException extends NotBoundException {

    public OpNotBoundException(String op) {
        super(op);
    }
}
