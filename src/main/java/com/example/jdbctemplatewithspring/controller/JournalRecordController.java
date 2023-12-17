package com.example.jdbctemplatewithspring.controller;

import com.example.jdbctemplatewithspring.model.JournalRecord;
import com.example.jdbctemplatewithspring.service.JournalRecordService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/journalRecords")
@Tag(name = "Journal Records")
public class JournalRecordController {
    @Autowired
    private JournalRecordService journalRecordService;

    @Operation (
            description = "Gets journal record by ID, ID needs to be specified in URL.",
            summary = "Get journal record by ID",
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
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<JournalRecord> getJournalRecordById(
            @ApiParam(
                    name = "id",
                    value = "ID of the journal record.",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id) {
        JournalRecord foundJournalRecord = journalRecordService.getById(id);
        if(foundJournalRecord != null)
            return new ResponseEntity<>(foundJournalRecord, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation (
            description = "Gets the list of all journal records.",
            summary = "Get all journal records",
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
    public ResponseEntity<List<JournalRecord>> getJournalRecords() {
        List<JournalRecord> journalRecords = journalRecordService.findAll();
        if(!journalRecords.isEmpty())
            return new ResponseEntity<>(journalRecords, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation (
            description = "Creates new journal record in the database.",
            summary = "Create journal record",
            responses = {
                    @ApiResponse(
                            description = "CREATED",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleJournalRecord",
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
    public ResponseEntity<Integer> createJournalRecord(
            @ApiParam(value = "Object that needs to be added to the database", required = true)
            @RequestBody NewJournalRecordRequest journalRecord) {
        try {
            JournalRecord newJournalRecord = new JournalRecord();
            newJournalRecord.setFullName(journalRecord.fullName);
            newJournalRecord.setBirthday(journalRecord.birthday);
            newJournalRecord.setFullTimeEducationForm(journalRecord.isFullTimeEducationForm);
            newJournalRecord.setPassword(journalRecord.password);
            int newId = journalRecordService.create(newJournalRecord);
            return new ResponseEntity<>(newId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation (
            description = "Creates new journal record in the database and deletes another one that has id specified in URL.",
            summary = "Create new journal record and delete old one.",
            responses = {
                    @ApiResponse(
                            description = "CREATED",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleJournalRecord",
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
    public ResponseEntity<Integer> createNewJournalRecordAndDeleteOldOne(
            @ApiParam(value = "Object that needs to be added to the database", required = true)
            @RequestBody NewJournalRecordRequest journalRecord,
            @ApiParam(
            value = "ID of the journal record that needs to be updated.",
            name = "id",
            type = "integer",
            example = "2",
            required = true)
            @PathVariable int id) {
        try {
            JournalRecord newJournalRecord = new JournalRecord();
            newJournalRecord.setFullName(journalRecord.fullName);
            newJournalRecord.setBirthday(journalRecord.birthday);
            newJournalRecord.setFullTimeEducationForm(journalRecord.isFullTimeEducationForm);
            newJournalRecord.setPassword(journalRecord.password);
            journalRecordService.createAndDelete(newJournalRecord, id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation (
            description = "Updates existing journal record in the database by ID.",
            summary = "Update existing journal record",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name =  "ExampleJournalRecord",
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
                                            name =  "ExampleJournalRecord",
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
    public ResponseEntity updateJournalRecord(
            @ApiParam(
                    value = "ID of the journal record that needs to be updated.",
                    name = "id",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id,
            @ApiParam(value = "Modified version of the object.", required = true)
            @RequestBody NewJournalRecordRequest journalRecordDetails) {
        JournalRecord journalRecord = journalRecordService.getById(id);
        if(journalRecord == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        journalRecord.setFullName(journalRecordDetails.fullName);
        journalRecord.setBirthday(journalRecordDetails.birthday);
        journalRecord.setFullTimeEducationForm(journalRecordDetails.isFullTimeEducationForm);
        journalRecord.setPassword(journalRecordDetails.password);

        try{
            boolean updated = journalRecordService.update(journalRecord, id);
            return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation (
            description = "Delete existing journal record in the database by ID.",
            summary = "Delete existing journal record",
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
    public ResponseEntity<JournalRecord> deleteJournalRecord(
            @ApiParam(
                    name = "id",
                    value = "ID of the journal record.",
                    type = "integer",
                    example = "2",
                    required = true)
            @PathVariable int id) {
        try {
            journalRecordService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    record NewJournalRecordRequest(
            int journalRecordId,
            String fullName,
            LocalDate birthday,
            boolean isFullTimeEducationForm,
            String password
    ) { }
}
