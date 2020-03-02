package org.kimbs.querydsl.department.domain;

import lombok.*;
import org.kimbs.querydsl.student.domain.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * id: 부서 아이디
 * name: 부서 이름
 * office: 부서 건물 위치
 */
@Entity(name = "DEPARTMENT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 10, nullable = false)
    private String name;

    @Column(name = "OFFICE", length = 20)
    private String office;

    @Builder
    public Department(String name, String office) {
        this.name = name;
        this.office = office;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "department")
    private List<Student> students = new ArrayList<>();

    public void addStudents(List<Student> students) {
        for (Student student : students) {
            this.addStudent(student);
        }
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.setDepartment(this);
    }
}
