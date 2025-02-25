package com.lloyds.classroom_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CLASSROOM")
public class Classroom {

    public Classroom(int id, String name, Integer code, Integer teacherId) {
        super();
        this.code = code;
        this.teacherId = teacherId;
        this.name = name;
        this.id = id;
    }

    public Classroom() {
        super();
    }

    @Id
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private Integer code;

    @Column
    private Integer teacherId;

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
