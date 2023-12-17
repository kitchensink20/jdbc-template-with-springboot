package com.example.jdbctemplatewithspring.dao;

import com.example.jdbctemplatewithspring.model.JournalRecord;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class JournalRecordJdbcDAO implements DAO<JournalRecord> {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<JournalRecord> rowMapper = (rs, rowNum) -> {
        JournalRecord journalRecord = new JournalRecord();
        journalRecord.setJournalRecordId(rs.getInt("journalRecordId"));
        journalRecord.setFullName(rs.getString("fullName"));
        journalRecord.setBirthday(rs.getDate("birthday").toLocalDate());
        journalRecord.setFullTimeEducationForm(rs.getBoolean("isFullTimeEducationForm"));
        journalRecord.setPassword(rs.getString("password"));
        return journalRecord;
    };

    public JournalRecordJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<JournalRecord> list() {
        String sql = "SELECT * FROM JournalRecords";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<JournalRecord> get(int id) {
        String sql = "SELECT * FROM JournalRecords WHERE journalRecordId = ?";
        JournalRecord journalRecord = null;
        try {
            journalRecord = jdbcTemplate.queryForObject(sql, new Object[] {id}, rowMapper);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(journalRecord);
    }

    public List<JournalRecord> getByEducationForm(boolean educationForm) {
        String sql = "SELECT * FROM JournalRecords WHERE isFullTimeEducationForm = ?";
        return jdbcTemplate.query(sql, new Object[]{educationForm}, rowMapper);
    }

    @Override
    public int create(JournalRecord newJournalRecord) {
        String sql = "INSERT INTO JournalRecords(fullName, birthday, isFullTimeEducationForm, password) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, newJournalRecord.getFullName());
            ps.setDate(2, java.sql.Date.valueOf(newJournalRecord.getBirthday()));
            ps.setBoolean(3, newJournalRecord.isFullTimeEducationForm());
            ps.setString(4, newJournalRecord.getPassword());
            return ps;
        }, keyHolder);

        return rowsAffected == 0 ? 0 : keyHolder.getKey().intValue();
    }

    @Override
    public boolean update(JournalRecord journalRecord, int id) {
        String sql = "UPDATE JournalRecords SET fullName = ?, birthday = ?, isFullTimeEducationForm = ?, password = ? WHERE journalRecordId = ?";
        int rowsAffected = jdbcTemplate.update(sql, journalRecord.getFullName(), journalRecord.getBirthday(), journalRecord.isFullTimeEducationForm(), journalRecord.getPassword(), id);
        return rowsAffected != 0;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM JournalRecords WHERE journalRecordId = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected != 0;
    }
}
