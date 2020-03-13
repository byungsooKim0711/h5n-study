package org.kimbs.querydsl.clazz.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kimbs.querydsl.config.BaseTimeEntity;
import org.kimbs.querydsl.professor.domain.Professor;

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
@Entity(name = "CLAZZ")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clazz extends BaseTimeEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFESSOR_ID", foreignKey = @ForeignKey(name = "FK_CLASS_PROFESSOR"))
    private Professor professor;

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_ID", foreignKey = @ForeignKey(name = "FK_CLAZZ_COURSE"))
    private Course course;

    public void setCourse(Course course) {
        this.course = course;
    }
}
