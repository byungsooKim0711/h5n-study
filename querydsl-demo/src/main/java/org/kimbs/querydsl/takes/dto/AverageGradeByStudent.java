package org.kimbs.querydsl.takes.dto;

import lombok.Data;

@Data
public class AverageGradeByStudent {
    private Long studentId;
    private String studentName;
    private double averageGrade;

}
