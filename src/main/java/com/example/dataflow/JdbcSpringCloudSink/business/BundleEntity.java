package com.example.dataflow.JdbcSpringCloudSink.business;

import jakarta.persistence.*;
import lombok.*;

import java.rmi.NotBoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TB_TEST")
public class BundleEntity {
    @Id
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "Cint1")
    private Integer Cint1;
    @Column(name = "Cstr1")
    private String CString1;
    @Column(name = "Cdate1")
    private Date CDate1;

    public BundleEntity updateAllParam(BundleEntity param) {
        this.seq = param.getSeq();
        this.Cint1 = param.getCint1();
        this.CString1 = param.getCString1();
        this.CDate1 = param.getCDate1();
        return this;
    }

    public BundleEntity(HashMap<String, Object> map) throws NotBoundException {

        List<String> columns = List.of(new String[]{"seq", "Cint1", "Cstr1", "Cdate1"});
        //validate
        for (String column : columns) {
            if (!map.containsKey(column)) throw new NotBoundException("columns : " + columns.toString() + ", failed column : " + column);
        }
        this.seq = (Integer) map.get("seq");
        this.Cint1 = (Integer) map.get("Cint1");
        this.CString1 = (String) map.get("Cstr1");
        this.CDate1 = new Date(Long.parseLong(map.get("Cdate1").toString()));
    }
}
