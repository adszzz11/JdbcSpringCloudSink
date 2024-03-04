package com.example.dataflow.JdbcSpringCloudSink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class ProcessService {

    @Autowired
    private BundleEntityRepository bundleEntityRepository;
    private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public String process(String message) {
        try {
            DebeziumData debeziumData;
            HashMap<String, Object> jsonObj = (HashMap<String, Object>) mapper.readValue(message, HashMap.class);
            if (jsonObj.containsKey("payload")) {
                HashMap<String, Object> payload = (HashMap<String, Object>) jsonObj.get("payload");
                debeziumData = DebeziumData.createDebeziumData(payload);
            } else {
                debeziumData = DebeziumData.createDebeziumData(jsonObj);
            }

            switch (debeziumData.op()) {
                case "c": // insert
                    // before = null
                    // after = not null
                    BundleEntity insert = new BundleEntity(debeziumData.after());
                    bundleEntityRepository.save(insert);
                    break;
                case "d": // delete
                    // before = not null
                    // after = null
                    BundleEntity delete = new BundleEntity(debeziumData.before());
                    bundleEntityRepository.delete(
                            bundleEntityRepository.findById(delete.getSeq())
                            .orElseThrow(() -> new NoSuchElementException("there is no row to delete"))
                    );

                    break;
                case "u": // update
                    // before = not null
                    // after = not null
                    BundleEntity next = new BundleEntity(debeziumData.after());
                    BundleEntity previous = new BundleEntity(debeziumData.before());
                    bundleEntityRepository.save(
                            bundleEntityRepository.findById(previous.getSeq())
                            .orElseThrow(() -> new NoSuchElementException("there is no row to update"))
                                    .updateAllParam(next)
                    );
                    break;
                case "t": // truncate
                    // before = null (no key)
                    // after = null (no key)
                case "r": // read
                    // before = null
                    // after = not null
                default:
                    throw new OpNotBoundException(debeziumData.op());

            }

        } catch (JsonProcessingException e) {
            log.info("JSON DATA NOT MATCH : {}", e);
            throw new RuntimeException(e);
        } catch (OpNotBoundException e) {
            log.error("OP NOT MATCH : OP INPUT [{}]", e.getMessage());
        } catch (DebeziumDataNotMatchException e) {
            throw new RuntimeException(e);
        } catch (NoSuchElementException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
        return "true";
    }
}
