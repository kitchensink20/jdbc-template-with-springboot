package com.example.jdbctemplatewithspring.controller;

import com.example.jdbctemplatewithspring.controller.JournalRecordController.NewJournalRecordRequest;
import com.example.jdbctemplatewithspring.model.JournalRecord;
import com.example.jdbctemplatewithspring.model.Lesson;
import com.example.jdbctemplatewithspring.service.LessonService;

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
@RequestMapping("/api/lessons")
@Tag(name = "Lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @Operation (
            description = "Gets lesson by ID, ID needs to be specified in URL.",
            summary = "Get journal record by ID",
            responses =  {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleJournalRecord",
                                            value = "{\"lessonId\": 1, \"subjectName\": \"Algorythms\", \"professorId\": \"8\", \"weekDay\": \"Monday\", \"classNumber\": \"3\" }"
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
    public ResponseEntity<Lesson> getLessonById(
            @ApiParam(
                    name = "id",
                    value = "ID of the journal record.",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id) {
        Lesson foundLesson = lessonService.getById(id);
        if(foundLesson != null)
            return new ResponseEntity<>(foundLesson, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation (
            description = "Gets the list of all lessons.",
            summary = "Get all lessons",
            responses =  {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleJournalRecord",
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
    public ResponseEntity<List<Lesson>> getLessons() {
        List<Lesson> lessons = lessonService.findAll();
        if(!lessons.isEmpty())
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation (
            description = "Creates new lesson in the database.",
            summary = "Create lesson",
            responses = {
                    @ApiResponse(
                            description = "CREATED",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleLesson",
                                            value = "8"
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
    public ResponseEntity<Integer> createLesson(
            @ApiParam(value = "Object that needs to be added to the database", required = true)
            @RequestBody NewlessonRequest lesson) {
        try {
            Lesson newlesson = new Lesson();
            newlesson.setProfessorId(lesson.professorId);
            newlesson.setClassNumber(lesson.classNumber);
            newlesson.setSubjectName(lesson.subjectName);
            newlesson.setWeekDay(lesson.weekDay);
            int newId = lessonService.create(newlesson);
            return new ResponseEntity<>(newId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation (
            description = "Creates new lesson in the database and deletes another one that has id specified in URL.",
            summary = "Create new lesson and delete old one.",
            responses = {
                    @ApiResponse(
                            description = "CREATED",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleLesson",
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
    public ResponseEntity<Integer> createNewLessonAndDeleteOldOne(
            @ApiParam(value = "Object that needs to be added to the database", required = true)
            @RequestBody NewlessonRequest lesson,
            @ApiParam(
            value = "ID of the lesson that needs to be updated.",
            name = "id",
            type = "integer",
            example = "2",
            required = true)
            @PathVariable int id) {
        try {
            Lesson newlesson = new Lesson();
            newlesson.setProfessorId(lesson.professorId);
            newlesson.setClassNumber(lesson.classNumber);
            newlesson.setSubjectName(lesson.subjectName);
            newlesson.setWeekDay(lesson.weekDay);
            lessonService.createAndDelete(newlesson, id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation (
            description = "Updates existing lesson in the database by ID.",
            summary = "Update existing lesson",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleLesson",
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
                                            name =  "ExampleLesson",
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
    public ResponseEntity updateLesson(
            @ApiParam(
                    value = "ID of the journal record that needs to be updated.",
                    name = "id",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id,
            @ApiParam(value = "Modified version of the object.", required = true)
            @RequestBody NewlessonRequest lessonDetails) {
        Lesson lesson = lessonService.getById(id);
        if(lesson == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        lesson.setProfessorId(lessonDetails.professorId);
        lesson.setClassNumber(lessonDetails.classNumber);
        lesson.setSubjectName(lessonDetails.subjectName);
        lesson.setWeekDay(lessonDetails.weekDay);

        try{
            boolean updated = lessonService.update(lesson, id);
            return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation (
            description = "Delete existing lesson in the database by ID.",
            summary = "Delete existing lesson",
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
    public ResponseEntity<Lesson> deleteLesson(
            @ApiParam(
                    name = "id",
                    value = "ID of the journal record.",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id) {
        try {
            lessonService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    record NewlessonRequest(
            int lessonId,
            String subjectName,
            int professorId,
            String weekDay,
            int classNumber
    ) { }
}
