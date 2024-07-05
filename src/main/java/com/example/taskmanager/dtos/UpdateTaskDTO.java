package com.example.taskmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor


public class UpdateTaskDTO {

    String description;
    String deadline;
    Boolean completed;

}
