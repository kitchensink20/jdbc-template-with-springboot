package com.example.jdbctemplatewithspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class JournalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int journalRecordId;
    private String fullName;
    private LocalDate birthday;
    private boolean isFullTimeEducationForm;
    private String password;

    public JournalRecord(int journalRecordId, String fullName, LocalDate birthday, boolean fullTimeEducationForm, String password) {
        this.journalRecordId = journalRecordId;
        this.fullName = fullName;
        this.birthday = birthday;
        this.isFullTimeEducationForm = fullTimeEducationForm;
        this.password = password;
    }

    public JournalRecord(String fullName, LocalDate birthday, boolean fullTimeEducationForm, String password) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.isFullTimeEducationForm = fullTimeEducationForm;
        this.password = password;
    }

    public JournalRecord() { }

    public int getJournalRecordId() {
        return journalRecordId;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public boolean isFullTimeEducationForm() {
        return isFullTimeEducationForm;
    }

    public String getPassword() { return password; }

    public void setJournalRecordId(int journalRecordId) { this.journalRecordId = journalRecordId; }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public void setFullTimeEducationForm(boolean isFullTimeEducationForm) {
        this.isFullTimeEducationForm = isFullTimeEducationForm;
    }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "JournalRecord{" +
                "studentId=" + journalRecordId +
                ", fullName='" + fullName + '\'' +
                ", birthday=" + birthday +
                ", isFullTimeEducationForm=" + isFullTimeEducationForm +
                '}';
    }
}

