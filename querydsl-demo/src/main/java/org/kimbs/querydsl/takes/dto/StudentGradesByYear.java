package org.kimbs.querydsl.takes.dto;

import lombok.Data;

// 년도별 학생들의 성적
@Data
public class StudentGradesByYear {
    private String studentName;
    private String courseTitle;
    private int classOpenYear;
    private String grade;
}
