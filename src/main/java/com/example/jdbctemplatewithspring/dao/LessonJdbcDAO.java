package com.example.jdbctemplatewithspring.dao;

import com.example.jdbctemplatewithspring.model.Lesson;
import jakarta.persistence.Entity;
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
public class LessonJdbcDAO implements DAO<Lesson> {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Lesson> rowMapper = (rs, rowNum) -> {
        Lesson lesson = new Lesson();
        lesson.setLessonId(rs.getInt("lessonId"));
        lesson.setClassNumber(rs.getInt("classNumber"));
        lesson.setProfessorId(rs.getInt("professorId"));
        lesson.setSubjectName(rs.getString("subjectName"));
        return lesson;
    };

    public LessonJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Lesson> list() {
        String sql = "SELECT * FROM Lessons";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Lesson> get(int id) {
        String sql = "SELECT * FROM Lessons WHERE lessonId = ?";
        Lesson lesson = null;
        try {
            lesson = jdbcTemplate.queryForObject(sql, new Object[] {id}, rowMapper);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(lesson);
    }

    @Override
    public int create(Lesson newLesson) {
        String sql = "INSERT INTO Lessons(subjectName, professorId, weekDay, classNumber) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, newLesson.getSubjectName());
            ps.setInt(2, newLesson.getProfessorId());
            ps.setString(3, newLesson.getWeekDay());
            ps.setInt(4, newLesson.getClassNumber());
            return ps;
        }, keyHolder);

        return rowsAffected == 0 ? 0 : keyHolder.getKey().intValue();
    }

    @Override
    public boolean update(Lesson lesson, int id) {
        String sql = "UPDATE Lessons SET subjectName = ?, professorId = ?, weekDay = ?, classNumber = ? WHERE lessonId = ?";
        int rowsAffected = jdbcTemplate.update(sql, lesson.getSubjectName(), lesson.getProfessorId(), lesson.getWeekDay(), lesson.getClassNumber(), id);
        return rowsAffected != 0;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Lessons WHERE lessonId = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected != 0;
    }
}
