package com.example.taskmanager.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    public int id;

    public String name; //имя задачи

    public String description; //описание задачи
}
