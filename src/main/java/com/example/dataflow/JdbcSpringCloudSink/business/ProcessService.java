package com.example.dataflow.JdbcSpringCloudSink.business;

import com.example.dataflow.JdbcSpringCloudSink.DebeziumData;
import com.example.dataflow.JdbcSpringCloudSink.exception.DebeziumDataNotMatchException;
import com.example.dataflow.JdbcSpringCloudSink.exception.OpNotBoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
@Slf4j
public abstract class ProcessService {

    @Autowired
    protected BundleEntityRepository bundleEntityRepository;
    private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    public void process(String message) {
        try {
            DebeziumData debeziumData = parseDebeziumData(message);
            branchProcess(debeziumData);
        } catch (OpNotBoundException e) {
            log.error("OP NOT MATCH : OP INPUT [{}]", e.getMessage());
            throw new RuntimeException(e);
        } catch (NoSuchElementException e) {
            log.error("Exception catch : {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            log.error("ENTITY MATCH COLUMN FAILED : Desc[ {} ]", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void branchProcess(DebeziumData debeziumData) throws NotBoundException {
        switch (debeziumData.op()) {
            case "c" -> // insert
                // before = null
                // after = not null
                    insertLogic(debeziumData);
            case "d" -> // delete
                // before = not null
                // after = null
                    deleteLogic(debeziumData);
            case "u" -> // update
                // before = not null
                // after = not null
                    updateLogic(debeziumData);
//            case "t" -> // truncate
            // before = null (no key)
            // after = null (no key)
//            case "r" -> // read
            // before = null
            // after = not null
            default -> throw new OpNotBoundException(debeziumData.op());
        }
    }


    private DebeziumData parseDebeziumData(String message) {
        try {
            HashMap<String, Object> jsonObj = (HashMap<String, Object>) mapper.readValue(message, HashMap.class);
            if (jsonObj.containsKey("payload")) {
                HashMap<String, Object> payload = (HashMap<String, Object>) jsonObj.get("payload");
                return DebeziumData.createDebeziumData(payload);
            } else {
                return DebeziumData.createDebeziumData(jsonObj);
            }
        } catch (DebeziumDataNotMatchException e) {
            log.error("CANNOT PARSE DEBEZIUM DATA. MAP : {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            log.error("CANNOT PARSE JSON");
            throw new RuntimeException(e);
        }

    }

    abstract void updateLogic(DebeziumData debeziumData) throws NotBoundException;
    abstract void insertLogic(DebeziumData debeziumData) throws NotBoundException;
    abstract void deleteLogic(DebeziumData debeziumData) throws NotBoundException;

}
