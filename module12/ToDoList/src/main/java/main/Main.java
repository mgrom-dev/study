package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import response.ToDo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class Main  {


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        new Service();
        System.out.println("Введите: exit, для закрытия сервера.");
        while(!new Scanner(System.in).nextLine().equals("exit"));
    }
}
