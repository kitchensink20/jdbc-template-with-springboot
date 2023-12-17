package com.example.jdbctemplatewithspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lessonId;
    private String subjectName;
    private int professorId;
    private String weekDay;
    private int classNumber;

    public Lesson(int lessonId, String subjectName, int professorId, String weekDay, int classNumber) {
        this.lessonId = lessonId;
        this.professorId = professorId;
        this.subjectName = subjectName;
        this.weekDay = weekDay;
        this.classNumber = classNumber;
    }

    public Lesson() { }

    public int getLessonId() {
        return lessonId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", subjectName='" + subjectName + '\'' +
                ", professorId=" + professorId +
                ", weekDay='" + weekDay + '\'' +
                ", classNumber=" + classNumber +
                '}';
    }
}
