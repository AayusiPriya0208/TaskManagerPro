package com.example.taskmanager.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class CreateTaskDTO {

  String title;
   String description;
    String deadline;
}
