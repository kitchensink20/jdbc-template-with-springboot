package com.example.jdbctemplatewithspring.service;

import com.example.jdbctemplatewithspring.dao.DAO;
import com.example.jdbctemplatewithspring.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    DAO<Lesson> lessonDAO;

    public List<Lesson> findAll() {
        return lessonDAO.list();
    }

    public Lesson getById(int id) {
        return lessonDAO.get(id).isPresent() ? lessonDAO.get(id).get() : null;
    }

    public int create(Lesson lesson) {
        return lessonDAO.create(lesson);
    }

    public boolean update(Lesson lesson, int id) {
        return lessonDAO.update(lesson, id);
    }

    public boolean delete(int id) {
        return lessonDAO.delete(id);
    }
}
