package com.example.dataflow.JdbcSpringCloudSink;

import java.rmi.NotBoundException;

public class OpNotBoundException extends NotBoundException {

    public OpNotBoundException(String op) {
        super(op);
    }
}
