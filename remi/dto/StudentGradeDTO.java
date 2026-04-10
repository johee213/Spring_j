package com.example.remi.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentGradeDTO {
    private long id;

    private String name;
    private int korScore;
    private int engScore;
    private int mathScore;

    public int getTotal() {
        return korScore + engScore + mathScore;
    }

    public double getAverage() {
        return getTotal() / 3.0;
    }
}
