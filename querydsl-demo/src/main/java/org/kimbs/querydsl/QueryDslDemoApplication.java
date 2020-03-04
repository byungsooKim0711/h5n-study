package org.kimbs.querydsl;

import lombok.Getter;
import lombok.ToString;
import org.kimbs.querydsl.clazz.domain.Clazz;
import org.kimbs.querydsl.clazz.domain.Course;
import org.kimbs.querydsl.clazz.repository.ClazzRepository;
import org.kimbs.querydsl.clazz.repository.CourseRepository;
import org.kimbs.querydsl.department.domain.Department;
import org.kimbs.querydsl.department.repository.DepartmentRepository;
import org.kimbs.querydsl.professor.domain.Professor;
import org.kimbs.querydsl.professor.repository.ProfessorRepository;
import org.kimbs.querydsl.student.domain.Student;
import org.kimbs.querydsl.student.repository.StudentRepository;
import org.kimbs.querydsl.takes.domain.Takes;
import org.kimbs.querydsl.takes.domain.TakesId;
import org.kimbs.querydsl.takes.repository.TakesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

import java.util.Arrays;

@SpringBootApplication
public class QueryDslDemoApplication {

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ClazzRepository clazzRepository;

	@Autowired
	ProfessorRepository professorRepository;

	@Autowired
	TakesRepository takesRepository;

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(QueryDslDemoApplication.class, args);
		ac.publishEvent(new MyEventObject(ac, "My custom event"));
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		System.out.println("ApplicationReadyEvent!!");

		Department d1 = Department.builder().name("컴퓨터공학과").office("201호").build();
		Department d2 = Department.builder().name("산업공학과").office("207호").build();
		Department d3 = Department.builder().name("전자공학과").office("308호").build();
		Department d4 = Department.builder().name("응용통계학과").office("404호").build();

		Student s1 = Student.builder().name("김광식").address("서울").year(3).build();
		Student s2 = Student.builder().name("김정현").address("서울").year(3).build();
		Student s3 = Student.builder().name("김현정").address("대전").year(4).build();
		Student s4 = Student.builder().name("김현정").address("대구").year(2).build();
		Student s5 = Student.builder().name("박광수").address("광주").year(3).build();
		Student s6 = Student.builder().name("김우주").address("부산").year(4).build();
		Student s7 = Student.builder().name("박철수").address("대전").year(3).build();
		Student s8 = Student.builder().name("백태성").address("서울").year(3).build();

		d1.addStudents(Arrays.asList(s1, s2, s3));
		d2.addStudents(Arrays.asList(s4, s5, s6));
		d3.addStudents(Arrays.asList(s7, s8));

		s1.setDepartment(d1);
		s2.setDepartment(d1);
		s3.setDepartment(d1);
		s4.setDepartment(d2);
		s5.setDepartment(d2);
		s6.setDepartment(d2);
		s7.setDepartment(d3);
		s8.setDepartment(d3);

		departmentRepository.saveAll(Arrays.asList(d1, d2, d3, d4));
		studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8));

		Professor p1 = Professor.builder().name("이태규").position("교수").yearEmp(1997).build();
		Professor p2 = Professor.builder().name("고희석").position("부교수").yearEmp(2003).build();
		Professor p3 = Professor.builder().name("최성희").position("부교수").yearEmp(2005).build();
		Professor p4 = Professor.builder().name("김태석").position("교수").yearEmp(1999).build();
		Professor p5 = Professor.builder().name("박철재").position("조교수").yearEmp(2007).build();
		Professor p6 = Professor.builder().name("장민석").position("부교수").yearEmp(2005).build();

		p1.setDepartment(d1);
		p2.setDepartment(d1);
		p3.setDepartment(d2);
		p4.setDepartment(d2);
		p5.setDepartment(d3);
		p6.setDepartment(d3);

		professorRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));

		Course course1 = Course.builder().title("전산개론").credit(3).build();
		Course course2 = Course.builder().title("자료구조").credit(3).build();
		Course course3 = Course.builder().title("운영체제").credit(3).build();
		Course course4 = Course.builder().title("이산수학").credit(4).build();
		Course course5 = Course.builder().title("인공지능").credit(3).build();
		Course course6 = Course.builder().title("알고리즘").credit(2).build();
		Course course7 = Course.builder().title("데이타베이스").credit(4).build();
		Course course8 = Course.builder().title("컴퓨터구조").credit(3).build();
		Course course9 = Course.builder().title("객체지향언어").credit(4).build();

		courseRepository.saveAll(Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course8, course9));

		Clazz clazz1 = Clazz.builder().year(2020).semester(1).division("A").classRoom("301호").enroll(40).build();
		Clazz clazz2 = Clazz.builder().year(2020).semester(1).division("A").classRoom("209호").enroll(30).build();
		Clazz clazz3 = Clazz.builder().year(2020).semester(1).division("A").classRoom("208호").enroll(30).build();
		Clazz clazz4 = Clazz.builder().year(2020).semester(1).division("B").classRoom("301호").enroll(30).build();
		Clazz clazz5 = Clazz.builder().year(2020).semester(1).division("A").classRoom("103호").enroll(45).build();
		Clazz clazz6 = Clazz.builder().year(2020).semester(2).division("B").classRoom("204호").enroll(25).build();
		Clazz clazz7 = Clazz.builder().year(2020).semester(2).division("A").classRoom("301호").enroll(30).build();
		Clazz clazz8 = Clazz.builder().year(2020).semester(2).division("A").classRoom("209호").enroll(45).build();
		Clazz clazz9 = Clazz.builder().year(2020).semester(2).division("A").classRoom("209호").enroll(30).build();
		Clazz clazz10 = Clazz.builder().year(2020).semester(2).division("B").classRoom("103호").enroll(26).build();

		clazz1.setCourse(course1);  clazz1.setProfessor(p3);
		clazz2.setCourse(course2);  clazz2.setProfessor(p1);
		clazz3.setCourse(course7);  clazz3.setProfessor(p5);
		clazz4.setCourse(course7);  clazz4.setProfessor(p3);
		clazz5.setCourse(course5);  clazz5.setProfessor(p5);
		clazz6.setCourse(course5);  clazz6.setProfessor(p6);
		clazz7.setCourse(course3);  clazz7.setProfessor(p6);
		clazz8.setCourse(course8);  clazz8.setProfessor(p5);
		clazz9.setCourse(course6);  clazz9.setProfessor(p1);
		clazz10.setCourse(course6); clazz10.setProfessor(p3);

		clazzRepository.saveAll(Arrays.asList(clazz1, clazz2, clazz3, clazz4, clazz5, clazz6, clazz7, clazz8, clazz9, clazz10));

		/*
		 * grade >= 95 A+
		 * grade >= 90 A
		 * grade >= 85 B+
		 * grade >= 80 B
		 * grade >= 75 C+
		 * grade >= 70 C
		 * grade >= 65 D+
		 * grade >= 60 D
		 * grade < 60 F
		 */
		Takes t1 = Takes.builder().id(new TakesId(s1.getId(), clazz1.getId())).grade(87).build();
		Takes t2 = Takes.builder().id(new TakesId(s1.getId(), clazz3.getId())).grade(99).build();
		Takes t3 = Takes.builder().id(new TakesId(s1.getId(), clazz7.getId())).grade(90).build();
		Takes t4 = Takes.builder().id(new TakesId(s2.getId(), clazz2.getId())).grade(90).build();
		Takes t5 = Takes.builder().id(new TakesId(s2.getId(), clazz3.getId())).grade(88).build();
		Takes t6 = Takes.builder().id(new TakesId(s2.getId(), clazz9.getId())).grade(76).build();
		Takes t7 = Takes.builder().id(new TakesId(s3.getId(), clazz4.getId())).grade(83).build();
		Takes t8 = Takes.builder().id(new TakesId(s3.getId(), clazz6.getId())).grade(99).build();
		Takes t9 = Takes.builder().id(new TakesId(s4.getId(), clazz2.getId())).grade(76).build();
		Takes t10 = Takes.builder().id(new TakesId(s5.getId(), clazz2.getId())).grade(70).build();
		Takes t11 = Takes.builder().id(new TakesId(s5.getId(), clazz4.getId())).grade(89).build();
		Takes t12 = Takes.builder().id(new TakesId(s5.getId(), clazz5.getId())).grade(12).build();

		takesRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
	}

	@EventListener
	public void onCustomEvent(MyEventObject event) {
		System.out.println("Custom event: " + event);
	}


	@Getter
	@ToString
	static class MyEventObject extends ApplicationEvent {
		private String message;
		public MyEventObject(Object source, String message) {
			super(source);
			this.message = message;
		}
	}

}
