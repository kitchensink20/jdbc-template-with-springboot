package com.example.jdbctemplatewithspring.dao;

import com.example.jdbctemplatewithspring.model.Professor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class ProfessorJdbcDAO implements DAO<Professor> {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Professor> rowMapper = (rs, rowNum) -> {
        Professor professor = new Professor();
        professor.setProfessorId(rs.getInt("professorId"));
        professor.setFullName(rs.getString("fullName"));
        professor.setPosition(rs.getString("position"));
        professor.setPassword(rs.getString("password"));
        return professor;
    };

    public ProfessorJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Professor> list() {
        String sql = "SELECT * FROM Professors";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Professor> get(int id) {
        String sql = "SELECT * FROM Professors WHERE professorId = ?";
        Professor professor = null;
        try {
            professor = jdbcTemplate.queryForObject(sql, new Object[] {id}, rowMapper);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(professor);
    }

    @Override
    public int create(Professor newProfessor) {
        String sql = "INSERT INTO Professors(fullName, position, password) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, newProfessor.getFullName());
            ps.setString(2, newProfessor.getPosition());
            ps.setString(3, newProfessor.getPassword());
            return ps;
        }, keyHolder);

        return rowsAffected == 0 ? 0 : keyHolder.getKey().intValue();
    }

    @Override
    public boolean update(Professor professor, int id) {
        String sql = "UPDATE Professors SET fullName = ?, position = ?, password = ? WHERE professorId = ?";
        int rowsAffected = jdbcTemplate.update(sql, professor.getFullName(), professor.getPosition(), professor.getPassword(), id);
        return rowsAffected != 0;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Professors WHERE professorId = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected != 0;
    }
}
