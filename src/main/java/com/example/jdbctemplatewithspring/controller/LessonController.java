package com.example.jdbctemplatewithspring.controller;

import com.example.jdbctemplatewithspring.service.LessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lessons")
@Tag(name = "Lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;
}
