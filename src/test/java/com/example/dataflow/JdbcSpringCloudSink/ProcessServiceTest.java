package com.example.dataflow.JdbcSpringCloudSink;

import com.example.dataflow.JdbcSpringCloudSink.business.BundleEntityRepository;
import com.example.dataflow.JdbcSpringCloudSink.business.ProcessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class ProcessServiceTest {

    @Autowired
    private ProcessService processService;

//    @Test
//    void transferTest() {
//        testService.extracted(message);
//    }

    @Test
    @Transactional
    void insertTest() throws JsonProcessingException {
        String message = "{\"payload\":{\"before\":null,\"after\":{\"seq\":1,\"Cint1\":13,\"Cstr1\":\"test\",\"Cdate1\":1708339981000},\"source\":{\"version\":\"1.7.1.Final\",\"connector\":\"mysql\",\"name\":\"my-src\",\"ts_ms\":1708339981000,\"snapshot\":\"false\",\"db\":\"test\",\"sequence\":null,\"table\":\"TB_TEST\",\"server_id\":1,\"gtid\":null,\"file\":\"binlog.000003\",\"pos\":5184,\"row\":0,\"thread\":null,\"query\":null},\"op\":\"c\",\"ts_ms\":1708339981565,\"transaction\":null}}";
        processService.process(message);
    }

    @Test
    @Transactional
    void insert_update_delete_test() {
        String message = "{\"payload\":{\"before\":null,\"after\":{\"seq\":1,\"Cint1\":2,\"Cstr1\":\"updateTest\",\"Cdate1\":1708339981000},\"source\":{\"version\":\"1.7.1.Final\",\"connector\":\"mysql\",\"name\":\"my-src\",\"ts_ms\":1708339981000,\"snapshot\":\"false\",\"db\":\"test\",\"sequence\":null,\"table\":\"TB_TEST\",\"server_id\":1,\"gtid\":null,\"file\":\"binlog.000003\",\"pos\":5184,\"row\":0,\"thread\":null,\"query\":null},\"op\":\"c\",\"ts_ms\":1708339981565,\"transaction\":null}}";
        processService.process(message);
        message = "{\"payload\":{\"before\":{\"seq\":1,\"Cint1\":2,\"Cstr1\":\"updateTest\",\"Cdate1\":1708339981000},\"after\":{\"seq\":1,\"Cint1\":3,\"Cstr1\":\"updateDone\",\"Cdate1\":1708339981000},\"source\":{\"version\":\"1.7.1.Final\",\"connector\":\"mysql\",\"name\":\"my-src\",\"ts_ms\":1708339981000,\"snapshot\":\"false\",\"db\":\"test\",\"sequence\":null,\"table\":\"TB_TEST\",\"server_id\":1,\"gtid\":null,\"file\":\"binlog.000003\",\"pos\":5184,\"row\":0,\"thread\":null,\"query\":null},\"op\":\"u\",\"ts_ms\":1708339981565,\"transaction\":null}}";
        processService.process(message);
        message = "{\"payload\":{\"before\":{\"seq\":1,\"Cint1\":3,\"Cstr1\":\"updateDone\",\"Cdate1\":1708339981000},\"after\":null,\"source\":{\"version\":\"1.7.1.Final\",\"connector\":\"mysql\",\"name\":\"my-src\",\"ts_ms\":1708339981000,\"snapshot\":\"false\",\"db\":\"test\",\"sequence\":null,\"table\":\"TB_TEST\",\"server_id\":1,\"gtid\":null,\"file\":\"binlog.000003\",\"pos\":5184,\"row\":0,\"thread\":null,\"query\":null},\"op\":\"u\",\"ts_ms\":1708339981565,\"transaction\":null}}";


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