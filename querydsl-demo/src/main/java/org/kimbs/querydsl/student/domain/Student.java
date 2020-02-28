package org.kimbs.querydsl.student.domain;

import lombok.*;
import org.kimbs.querydsl.department.domain.Department;

import javax.persistence.*;

/*
 * id: 학생 번호
 * name: 학생 이름
 * year: -
 * address: 학생 주소
 * departmentId: 학생이 속한 부서 아이디
 */
@Entity(name = "STUDENT")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 10, nullable = false)
    private String name;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "ADDRESS", length = 10)
    private String address;

    @Builder
    public Student(String name, int year, String address) {
        this.name = name;
        this.year = year;
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID", foreignKey = @ForeignKey(name = "FK_STUDENT_MEMBER"))
    private Department department;

    public void setDepartment(Department department) {
        this.department = department;
    }

}
