package com.example.taskmanager;

import com.example.taskmanager.service.Base;
import com.example.taskmanager.service.ServiceDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main  {
    public static Base base;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        base = new ServiceDB();
        System.out.println("Введите: exit, для закрытия сервера.");
        while(!new Scanner(System.in).nextLine().equals("exit"));
    }
}
