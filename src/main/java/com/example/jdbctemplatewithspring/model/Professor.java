package com.example.jdbctemplatewithspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int professorId;
    private String fullName;
    private String position;
    private String password;

    public Professor(int professorId, String fullName, String position, String password) {
        this.professorId = professorId;
        this.fullName = fullName;
        this.position = position;
        this.password = password;
    }

    public Professor() { }

    public int getProfessorId() {
        return professorId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPosition() {
        return position;
    }

    public String getPassword() { return password; }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Professor{" +
                "professorId=" + professorId +
                ", fullName='" + fullName + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
