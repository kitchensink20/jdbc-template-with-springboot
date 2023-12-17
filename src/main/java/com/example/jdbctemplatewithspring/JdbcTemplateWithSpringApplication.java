package com.example.jdbctemplatewithspring;

import com.example.jdbctemplatewithspring.dao.DAO;
import com.example.jdbctemplatewithspring.dao.JournalRecordJdbcDAO;
import com.example.jdbctemplatewithspring.model.JournalRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class JdbcTemplateWithSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcTemplateWithSpringApplication.class, args);
    }

}
