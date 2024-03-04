package com.example.dataflow.JdbcSpringCloudSink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundleEntityRepository extends JpaRepository<BundleEntity, Integer> {

}
