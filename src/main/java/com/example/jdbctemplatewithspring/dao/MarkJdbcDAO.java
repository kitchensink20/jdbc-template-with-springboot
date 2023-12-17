package com.example.jdbctemplatewithspring.dao;

import com.example.jdbctemplatewithspring.model.Mark;
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
public class MarkJdbcDAO implements DAO<Mark> {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Mark> rowMapper = (rs, rowNum) -> {
        Mark mark = new Mark();
        mark.setMarkId(rs.getInt("markId"));
        mark.setStudentId(rs.getInt("studentId"));
        mark.setLessonId(rs.getInt("lessonId"));
        mark.setDate(rs.getDate("date").toLocalDate());
        mark.setMark(rs.getFloat("mark"));
        return mark;
    };

    public MarkJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mark> list() {
        String sql = "SELECT * FROM Marks";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Mark> get(int id) {
        String sql = "SELECT * FROM Marks WHERE markId = ?";
        Mark mark = null;
        try {
            mark = jdbcTemplate.queryForObject(sql, new Object[] {id}, rowMapper);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(mark);
    }

    @Override
    public int create(Mark newMark) {
        String sql = "INSERT INTO Marks(studentId, lessonId, date, mark) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setInt(1, newMark.getStudentId());
            ps.setInt(2, newMark.getLessonId());
            ps.setDate(3, java.sql.Date.valueOf(newMark.getDate()));
            ps.setFloat(4, newMark.getMark());
            return ps;
        }, keyHolder);

        return rowsAffected == 0 ? 0 : keyHolder.getKey().intValue();
    }

    @Override
    public boolean update(Mark mark, int id) {
        String sql = "UPDATE Marks SET studentId = ?, lessonId = ?, date = ?, mark = ? WHERE ,markId = ?";
        int rowsAffected = jdbcTemplate.update(sql, mark.getStudentId(), mark.getLessonId(), mark.getDate(), mark.getMark(), id);
        return rowsAffected != 0;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Marks WHERE markId = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected != 0;
    }
}
