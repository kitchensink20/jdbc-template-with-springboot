package com.example.jdbctemplatewithspring.service;

import com.example.jdbctemplatewithspring.dao.DAO;
import com.example.jdbctemplatewithspring.model.JournalRecord;
import com.example.jdbctemplatewithspring.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
    public boolean createAndDelete(Lesson newlesson, int lessonIdForDelete) {
        try {
        	System.out.println(newlesson.getWeekDay());
            lessonDAO.create(newlesson);
            lessonDAO.delete(lessonIdForDelete);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int create(Lesson lesson) {
        return lessonDAO.create(lesson);
    }
    
    public boolean update(Lesson lesson, int id) {
    	System.out.println("piss1");
        return lessonDAO.update(lesson, id);
    }

    public boolean delete(int id) {
        return lessonDAO.delete(id);
    }
}
