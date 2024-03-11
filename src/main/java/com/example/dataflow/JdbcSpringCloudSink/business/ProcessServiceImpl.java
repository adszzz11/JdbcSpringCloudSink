package com.example.dataflow.JdbcSpringCloudSink.business;

import com.example.dataflow.JdbcSpringCloudSink.DebeziumData;
import org.springframework.stereotype.Component;

import java.rmi.NotBoundException;
import java.util.NoSuchElementException;

@Component
public class ProcessServiceImpl extends ProcessService{

    @Override
    void updateLogic(DebeziumData debeziumData) throws NotBoundException {
        BundleEntity next = new BundleEntity(debeziumData.after());
        BundleEntity previous = new BundleEntity(debeziumData.before());
        bundleEntityRepository.save(
                bundleEntityRepository.findById(previous.getSeq())
                        .orElseThrow(() -> new NoSuchElementException("there is no row to update"))
                        .updateAllParam(next)
        );
    }

    @Override
    void insertLogic(DebeziumData debeziumData) throws NotBoundException {
        BundleEntity insert = new BundleEntity(debeziumData.after());
        bundleEntityRepository.save(insert);
    }

    @Override
    void deleteLogic(DebeziumData debeziumData) throws NotBoundException {
        BundleEntity delete = new BundleEntity(debeziumData.before());
        bundleEntityRepository.delete(
                bundleEntityRepository.findById(delete.getSeq())
                        .orElseThrow(() -> new NoSuchElementException("there is no row to delete"))
        );
    }
}
