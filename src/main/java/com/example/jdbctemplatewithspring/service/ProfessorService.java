package com.example.jdbctemplatewithspring.service;

import com.example.jdbctemplatewithspring.dao.DAO;
import com.example.jdbctemplatewithspring.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    DAO<Professor> professorDAO;

    public List<Professor> findAll() {
        return professorDAO.list();
    }

    public Professor getById(int id) {
        return professorDAO.get(id).isPresent() ? professorDAO.get(id).get() : null;
    }

    public int create(Professor professor) {
        return professorDAO.create(professor);
    }

    public boolean update(Professor professor, int id) {
        return professorDAO.update(professor, id);
    }

    public boolean delete(int id) {
        return professorDAO.delete(id);
    }
}
