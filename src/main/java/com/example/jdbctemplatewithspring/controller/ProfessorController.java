package com.example.jdbctemplatewithspring.controller;

import com.example.jdbctemplatewithspring.controller.ProfessorController.NewProfessorRequest;
import com.example.jdbctemplatewithspring.model.Professor;
import com.example.jdbctemplatewithspring.service.ProfessorService;
import com.example.jdbctemplatewithspring.service.ProfessorService;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professors")
@Tag(name = "Professors")
public class ProfessorController {
	@Autowired
    private ProfessorService professorService;

    @Operation (
            description = "Gets professor by ID, ID needs to be specified in URL.",
            summary = "Get professor by ID",
            responses =  {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleProfessor",
                                            value = "{\"professorId\": 1, \"fullName\": \"Svitlana Proskura\", \"position\": \"Dean\", \"password\": \"fkgldfkr\" }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                    name = "NotFoundResponse",
                                    value = ""
                                )
                            )
                    )
            }
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Professor> getProfessorById(
            @ApiParam(
                    name = "id",
                    value = "ID of the professor.",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id) {
        Professor foundProfessor = professorService.getById(id);
        if(foundProfessor != null)
            return new ResponseEntity<>(foundProfessor, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation (
            description = "Gets the list of all professors.",
            summary = "Get all professors",
            responses =  {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleProfessor",
                                            value = "{\"studentId\": 1, \"fullName\": \"Ivan Tolkunov\", \"birthday\": \"2001-07-07\", \"isFullTimeEducationForm\": true, \"password\": \"hashed\" }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "NotFoundResponse",
                                            value = ""
                                    )
                            )
                    )
            }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Professor>> getProfessors() {
        List<Professor> professors = professorService.findAll();
        if(!professors.isEmpty())
            return new ResponseEntity<>(professors, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation (
            description = "Creates new professor in the database.",
            summary = "Create professor",
            responses = {
                    @ApiResponse(
                            description = "CREATED",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleProfessor",
                                            value = "7"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "BAD REQUEST",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "BadRequest",
                                            value = ""
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Integer> createProfessor(
            @ApiParam(value = "Object that needs to be added to the database", required = true)
            @RequestBody NewProfessorRequest professor) {
        try {
            Professor newProfessor = new Professor();
            newProfessor.setFullName(professor.fullName);
            newProfessor.setPosition(professor.position);
            newProfessor.setPassword(professor.password);
            int newId = professorService.create(newProfessor);
            return new ResponseEntity<>(newId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation (
            description = "Creates new professor in the database and deletes another one that has id specified in URL.",
            summary = "Create new professor and delete old one.",
            responses = {
                    @ApiResponse(
                            description = "CREATED",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleProfessor",
                                            value = "7"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "BAD REQUEST",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "BadRequest",
                                            value = ""
                                    )
                            )
                    )
            }
    )
    @PostMapping("/{id}")
    public ResponseEntity<Integer> createNewProfessorAndDeleteOldOne(
            @ApiParam(value = "Object that needs to be added to the database", required = true)
            @RequestBody NewProfessorRequest professor,
            @ApiParam(
            value = "ID of the professor that needs to be updated.",
            name = "id",
            type = "integer",
            example = "2",
            required = true)
            @PathVariable int id) {
        try {
            Professor newProfessor = new Professor();
            newProfessor.setFullName(professor.fullName);
            newProfessor.setPosition(professor.position);
            newProfessor.setPassword(professor.password);
            professorService.createAndDelete(newProfessor, id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation (
            description = "Updates existing professor in the database by ID.",
            summary = "Update existing professor",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleProfessor",
                                            value = ""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "OK",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleProfessor",
                                            value = ""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "INTERNAL SERVER ERROR",
                            responseCode = "503",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "InternalServerError",
                                            value = ""
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity updateProfessor(
            @ApiParam(
                    value = "ID of the professor that needs to be updated.",
                    name = "id",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id,
            @ApiParam(value = "Modified version of the object.", required = true)
            @RequestBody NewProfessorRequest professorDetails) {
        Professor professor = professorService.getById(id);
        if(professor == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        professor.setFullName(professorDetails.fullName);
        professor.setPosition(professorDetails.position);
        professor.setPassword(professorDetails.password);

        try{
            boolean updated = professorService.update(professor, id);
            return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation (
            description = "Delete existing professor in the database by ID.",
            summary = "Delete existing professor",
            responses = {
                    @ApiResponse(
                            description = "NO CONTENT",
                            responseCode = "204",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "NoContentResponse",
                                            value = ""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "INTERNAL SERVER ERROR",
                            responseCode = "503",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "InternalServerError",
                                            value = ""
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Professor> deleteProfessor(
            @ApiParam(
                    name = "id",
                    value = "ID of the professor.",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id) {
        try {
            professorService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    record NewProfessorRequest(
            int professorId,
            String fullName,
            String position,
            String password
    ) { }
}
