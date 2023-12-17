package com.example.jdbctemplatewithspring.service;

import com.example.jdbctemplatewithspring.dao.DAO;
import com.example.jdbctemplatewithspring.dao.JournalRecordJdbcDAO;
import com.example.jdbctemplatewithspring.model.JournalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JournalRecordService {
    @Autowired
    DAO<JournalRecord> journalRecordDAO;

    public List<JournalRecord> findAll() {
        return journalRecordDAO.list();
    }

    public JournalRecord getById(int id) {
        return journalRecordDAO.get(id).isPresent() ? journalRecordDAO.get(id).get() : null;
    }

    public List<JournalRecord> getByEducationForm(boolean isFullTimeEducationForm) {
        return ((JournalRecordJdbcDAO) journalRecordDAO).getByEducationForm(isFullTimeEducationForm);
    }

    @Transactional
    public boolean createAndDelete(JournalRecord newJournalRecord, int journalRecordIdForDelete) {
        try {
            journalRecordDAO.create(newJournalRecord);
            journalRecordDAO.delete(journalRecordIdForDelete);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int create(JournalRecord journalRecord) {
        return journalRecordDAO.create(journalRecord);
    }

    public boolean update(JournalRecord journalRecord, int id) {
        return journalRecordDAO.update(journalRecord, id);
    }

    public boolean delete(int id) {
        return journalRecordDAO.delete(id);
    }
}
