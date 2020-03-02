package org.kimbs.querydsl.takes.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kimbs.querydsl.clazz.domain.Clazz;
import org.kimbs.querydsl.student.domain.Student;

import javax.persistence.*;

/*
 * id: 학생 아이디, 클래스 아이디 복합키
 * grade: 학점
 */
@Entity(name = "TAKES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Takes {

    @EmbeddedId
    private TakesId id;

    @Column(name = "GRADE", length = 2)
    private String grade;

    @Builder
    public Takes(TakesId id, String grade) {
        this.id = id;
        this.grade = grade;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID", foreignKey = @ForeignKey(name = "FK_TAKES_STUDENT"), insertable = false, updatable = false)
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLAZZ_ID", foreignKey = @ForeignKey(name = "FK_TAKES_CLAZZ"), insertable = false, updatable = false)
    private Clazz clazz;

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
}
