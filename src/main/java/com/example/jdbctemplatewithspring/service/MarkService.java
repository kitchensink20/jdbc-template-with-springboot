package com.example.jdbctemplatewithspring.service;

import com.example.jdbctemplatewithspring.dao.DAO;
import com.example.jdbctemplatewithspring.model.JournalRecord;
import com.example.jdbctemplatewithspring.model.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarkService {
    @Autowired
    DAO<Mark> markDAO;

    public List<Mark> findAll() {
        return markDAO.list();
    }

    public Mark getById(int id) {
        return markDAO.get(id).isPresent() ? markDAO.get(id).get() : null;
    }
    
    @Transactional
    public boolean createAndDelete(Mark newMark, int markIdForDelete) {
        try {
            markDAO.delete(markIdForDelete);
            markDAO.create(newMark);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int create(Mark mark) {
        return markDAO.create(mark);
    }

    public boolean update(Mark mark, int id) {
        return markDAO.update(mark, id);
    }

    public boolean delete(int id) {
        return markDAO.delete(id);
    }
}
