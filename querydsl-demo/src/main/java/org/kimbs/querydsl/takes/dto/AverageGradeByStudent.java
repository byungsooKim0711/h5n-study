package org.kimbs.querydsl.takes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AverageGradeByStudent {
    private Long studentId;
    private String studentName;
    private double averageGrade;

}
