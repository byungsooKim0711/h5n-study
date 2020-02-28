package org.kimbs.querydsl.clazz.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * id:
 * year: 개설 년도
 * semester: 학년
 * division: 분반
 * classRoom: 수업 위치
 * enroll: 수용 인원수
 * professorId: 담당 교수 아이디
 * courseId: 개설강좌 아이디
 */
@Entity
@Getter
@NoArgsConstructor
public class Clazz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "SEMESTER")
    private int semester;

    @Column(name = "DIVISION", length = 1)
    private String division;

    @Column(name = "CLASS_ROOM", length = 9)
    private String classRoom;

    @Column(name = "ENROLL")
    private int enroll;

    @Builder
    public Clazz(int year, int semester, String division, String classRoom, int enroll) {
        this.year = year;
        this.semester = semester;
        this.division = division;
        this.classRoom = classRoom;
        this.enroll = enroll;
    }

    private Long professorId;

    private Long courseId;
}
