package com.example.dataflow.JdbcSpringCloudSink.exception;

import java.rmi.NotBoundException;
import java.util.HashMap;

public class DebeziumDataNotMatchException extends NotBoundException {
    public DebeziumDataNotMatchException(HashMap<String, Object> map) {
        super(String.valueOf(map));
    }
}
