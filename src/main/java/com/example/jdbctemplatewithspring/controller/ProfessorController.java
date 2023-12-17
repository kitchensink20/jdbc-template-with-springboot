package com.example.jdbctemplatewithspring.controller;

import com.example.jdbctemplatewithspring.service.ProfessorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professors")
@Tag(name = "Professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

}
