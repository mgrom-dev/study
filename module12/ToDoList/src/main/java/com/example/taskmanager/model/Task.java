package com.example.taskmanager.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Task {
    private int id;

    @NonNull
    private String name; //имя задачи

    @NonNull
    private String description; //описание задачи
}
