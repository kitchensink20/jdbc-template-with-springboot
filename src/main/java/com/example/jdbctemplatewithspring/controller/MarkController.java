package com.example.jdbctemplatewithspring.controller;

import com.example.jdbctemplatewithspring.controller.MarkController.NewMarkRequest;
import com.example.jdbctemplatewithspring.model.Mark;
import com.example.jdbctemplatewithspring.service.MarkService;
import com.example.jdbctemplatewithspring.service.MarkService;

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
@RequestMapping("/api/marks")
@Tag(name = "Marks")
public class MarkController {
	@Autowired
    private MarkService markService;

    @Operation (
            description = "Gets mark by ID, ID needs to be specified in URL.",
            summary = "Get mark by ID",
            responses =  {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleMark",
                                            value = "{\"markId\": 1, \"studentId\": 1, \"lessonId\": 1, \"date\": \"2023-12-16\", \"mark\": 9.0 }"
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
    public ResponseEntity<Mark> getMarkById(
            @ApiParam(
                    name = "id",
                    value = "ID of the mark.",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id) {
        Mark foundMark = markService.getById(id);
        if(foundMark != null)
            return new ResponseEntity<>(foundMark, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation (
            description = "Gets the list of all marks.",
            summary = "Get all marks",
            responses =  {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleMark",
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
    public ResponseEntity<List<Mark>> getMarks() {
        List<Mark> marks = markService.findAll();
        if(!marks.isEmpty())
            return new ResponseEntity<>(marks, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation (
            description = "Creates new mark in the database.",
            summary = "Create mark",
            responses = {
                    @ApiResponse(
                            description = "CREATED",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleMark",
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
    public ResponseEntity<Integer> createMark(
            @ApiParam(value = "Object that needs to be added to the database", required = true)
            @RequestBody NewMarkRequest mark) {
        try {
            Mark newMark = new Mark();
            newMark.setStudentId(mark.studentId);
            newMark.setLessonId(mark.lessonId);
            newMark.setDate(mark.date);
            newMark.setMark(mark.mark);
            int newId = markService.create(newMark);
            return new ResponseEntity<>(newId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation (
            description = "Creates new mark in the database and deletes another one that has id specified in URL.",
            summary = "Create new mark and delete old one.",
            responses = {
                    @ApiResponse(
                            description = "CREATED",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleMark",
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
    public ResponseEntity<Integer> createNewMarkAndDeleteOldOne(
            @ApiParam(value = "Object that needs to be added to the database", required = true)
            @RequestBody NewMarkRequest mark,
            @ApiParam(
            value = "ID of the mark that needs to be updated.",
            name = "id",
            type = "integer",
            example = "2",
            required = true)
            @PathVariable int id) {
        try {
            Mark newMark = new Mark();
            newMark.setStudentId(mark.studentId);
            newMark.setLessonId(mark.lessonId);
            newMark.setDate(mark.date);
            newMark.setMark(mark.mark);
            markService.createAndDelete(newMark, id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation (
            description = "Updates existing mark in the database by ID.",
            summary = "Update existing mark",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleMark",
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
                                            name =  "ExampleMark",
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
    public ResponseEntity updateMark(
            @ApiParam(
                    value = "ID of the mark that needs to be updated.",
                    name = "id",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id,
            @ApiParam(value = "Modified version of the object.", required = true)
            @RequestBody NewMarkRequest markDetails) {
        Mark mark = markService.getById(id);
        if(mark == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        mark.setStudentId(markDetails.studentId);
        mark.setLessonId(markDetails.lessonId);
        mark.setDate(markDetails.date);
        mark.setMark(markDetails.mark);

        try{
            boolean updated = markService.update(mark, id);
            return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation (
            description = "Delete existing mark in the database by ID.",
            summary = "Delete existing mark",
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
    public ResponseEntity<Mark> deleteMark(
            @ApiParam(
                    name = "id",
                    value = "ID of the mark.",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id) {
        try {
            markService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    record NewMarkRequest(
            int markId,
            int studentId,
            int lessonId,
            LocalDate date,
            float mark
    ) { }
}
