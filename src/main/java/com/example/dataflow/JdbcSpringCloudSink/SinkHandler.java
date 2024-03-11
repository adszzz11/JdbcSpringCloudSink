package com.example.dataflow.JdbcSpringCloudSink;

import com.example.dataflow.JdbcSpringCloudSink.business.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.ErrorMessage;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class SinkHandler {

    @Autowired
    private ProcessService processService;

    @Bean
    public Consumer<String> messageSink() {
        return this::accept;
    }

    private void accept(String message) {
        log.info("[INPUT] {}", message);
        processService.process(message);
    }

    @ServiceActivator(inputChannel = "errorChannel")
    public void errorNoti(ErrorMessage errorMessage) {
        final Throwable errPayload = errorMessage.getPayload();
        log.error(errPayload.getMessage());
    }


}
