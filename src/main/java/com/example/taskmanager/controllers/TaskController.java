package com.example.taskmanager.controllers;
import java.text.ParseException;
import java.util.List;

import com.example.taskmanager.dtos.CreateTaskDTO;
import com.example.taskmanager.dtos.ErrorResponseDTO;
import com.example.taskmanager.dtos.TaskResponseDTO;
import com.example.taskmanager.dtos.UpdateTaskDTO;
import com.example.taskmanager.entities.TaskEntity;
import com.example.taskmanager.service.NoteService;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    // inject task service

    private final TaskService taskService;
    private final NoteService noteService;
    private ModelMapper modelMapper = new ModelMapper();

    public TaskController(TaskService  taskService,NoteService noteService ) {
        this. taskService = taskService;
        this. noteService= noteService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks(){
         var tasks= taskService.getTasks();

         return ResponseEntity.ok(tasks);

    }
  @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable(value = "id") Integer id ){
        var task= taskService.getTaskById(id);
      var notes = noteService.getNotesForTask(id);
        if(task==null){

            return ResponseEntity.notFound().build();
        }

      var taskResponse = modelMapper.map(task, TaskResponseDTO.class);
      taskResponse.setNotes(notes);


        return ResponseEntity.ok(taskResponse);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id,@RequestBody UpdateTaskDTO body) throws ParseException {

        var task = taskService.updateTask(id, body.getDescription(), body.getDeadline(), body.getCompleted());

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);

    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {


        var task=taskService.addTask(body.getTitle(),body.getDescription(),body.getDeadline());
        return ResponseEntity.ok(task);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e){


        if(e instanceof ParseException){

            return ResponseEntity.badRequest().body(new ErrorResponseDTO("invalid date format"));


        }
         e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal server error"));


    }




}
