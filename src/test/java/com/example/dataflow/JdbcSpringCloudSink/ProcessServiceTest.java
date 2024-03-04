package com.example.dataflow.JdbcSpringCloudSink;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ProcessServiceTest {

    @Autowired
    private ProcessService processService;
    @Autowired
    private BundleEntityRepository bundleEntityRepository;
    private final String message = "{\"payload\":{\"before\":null,\"after\":{\"seq\":1,\"Cint1\":13,\"Cstr1\":\"test\",\"Cdate1\":1708339981000},\"source\":{\"version\":\"1.7.1.Final\",\"connector\":\"mysql\",\"name\":\"my-src\",\"ts_ms\":1708339981000,\"snapshot\":\"false\",\"db\":\"test\",\"sequence\":null,\"table\":\"TB_TEST\",\"server_id\":1,\"gtid\":null,\"file\":\"binlog.000003\",\"pos\":5184,\"row\":0,\"thread\":null,\"query\":null},\"op\":\"c\",\"ts_ms\":1708339981565,\"transaction\":null}}";

//    @Test
//    void transferTest() {
//        testService.extracted(message);
//    }

    @Test
    void objMapperTest() throws JsonProcessingException {
        processService.process(message);
    }

}
//
//  "payload":{
//       "before":null,
//       "after":{
//           "seq":31,
//           "Cint1":13,
//           "Cstr1":"test",
//           "Cdate1":1708339981000
//       },
//       "source":{
//           "version":"1.7.1.Final",
//           "connector":"mysql",
//           "name":"my-src",
//           "ts_ms":1708339981000,
//           "snapshot":"false",
//           "db":"test",
//           "sequence":null,
//           "table":"TB_TEST",
//           "server_id":1,
//           "gtid":null,
//           "file":"binlog.000003",
//           "pos":5184,
//           "row":0,
//           "thread":null,
//           "query":null
//       },
//       "op":"c",
//       "ts_ms":1708339981565,
//       "transaction":null
// }