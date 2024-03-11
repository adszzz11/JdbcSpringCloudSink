package com.example.dataflow.JdbcSpringCloudSink;

import com.example.dataflow.JdbcSpringCloudSink.exception.DebeziumDataNotMatchException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public record DebeziumData(
        HashMap<String, Object> before,
        HashMap<String, Object> after,
        HashMap<String, Object> source,
        String op,
        String ts_ms,
        String transaction
) {
    public static DebeziumData createDebeziumData(HashMap<String, Object> map) throws DebeziumDataNotMatchException {
        String opTemp, ts_msTemp, transactionTemp;
        opTemp = Optional.of(map.get("op").toString()).orElseThrow();
        HashMap<String, Object> afterTemp = null, beforeTemp = null, sourceTemp;
        List<String> afterArr = Arrays.asList("c", "u");
        List<String> beforeArr = Arrays.asList("d", "u");

        try {
            if (afterArr.contains(opTemp))
                afterTemp = (HashMap<String, Object>) Optional.ofNullable(map.get("after")).orElseThrow(() -> new NoSuchElementException("AFTER"));
            if (beforeArr.contains(opTemp))
                beforeTemp = (HashMap<String, Object>) Optional.ofNullable(map.get("before")).orElseThrow(() -> new NoSuchElementException("BEFORE"));
            sourceTemp = (HashMap<String, Object>) Optional.ofNullable(map.get("source")).orElseThrow(() -> new NoSuchElementException("SOURCE"));
            ts_msTemp = Optional.ofNullable(map.get("ts_ms")).orElse(null).toString();
            transactionTemp = (String) Optional.ofNullable(map.get("transaction")).orElse(null);
        } catch (NoSuchElementException ignored) {
            log.error("data not match : {}", ignored.getMessage());
            throw new DebeziumDataNotMatchException(map);
        }
        return new DebeziumData(beforeTemp, afterTemp, sourceTemp, opTemp, ts_msTemp, transactionTemp);
    }
}

