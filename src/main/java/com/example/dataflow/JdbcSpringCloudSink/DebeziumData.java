package com.example.dataflow.JdbcSpringCloudSink;

import org.apache.commons.codec.binary.StringUtils;

import java.util.*;

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
        HashMap<String, Object> afterTemp = null, beforeTemp = null, sourceTemp = null;
        List<String> afterArr = Arrays.asList("c","u");
        List<String> beforeArr = Arrays.asList("d","u");
        try {
            if (afterArr.contains(opTemp)) afterTemp = (HashMap<String, Object>) Optional.of(map.get("after")).orElseThrow();
            if (beforeArr.contains(opTemp)) beforeTemp = (HashMap<String, Object>) Optional.of(map.get("before")).orElseThrow();
            sourceTemp = (HashMap<String, Object>) Optional.of(map.get("source")).orElseThrow();
            ts_msTemp = Optional.ofNullable(map.get("ts_ms")).orElse(null).toString();
            transactionTemp = Optional.ofNullable(map.get("transaction")).orElse(null).toString();
        } catch (NullPointerException ignored) {
            throw new DebeziumDataNotMatchException();
        }
        return new DebeziumData(beforeTemp, afterTemp, sourceTemp, opTemp, ts_msTemp, transactionTemp);

    }
}
