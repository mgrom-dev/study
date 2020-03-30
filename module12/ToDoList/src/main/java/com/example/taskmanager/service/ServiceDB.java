package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.embedded.RedisServer;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Класс, который осуществляет работу с jedis базой данных
 */
public class ServiceDB implements Base {
    private int counterId = 0; //счетчик для ид дела
    private static final String PROC_NAME = "redis-server-2.8.19.exe"; //имя процесса с сервером redis
    private ProcessHandle proc;
    private JedisPool pool;
    private RedisServer server;
    Jedis jedis;

    {
        try {
            //инициализируем сервер
            server = new RedisServer();
            //ищем уже запущенный процесс с сервером
            proc = findRedis();
            if (proc == null) { //если процесс не найден, то стартуем сервер
                server.start();
                proc = findRedis();
            }
            //создаем клиента редис
            pool = new JedisPool(new JedisPoolConfig(), "localhost");
            jedis = pool.getResource();
            trackMainClass();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Ищем процесс по имени
     */
    private static ProcessHandle findRedis(){
        return ProcessHandle.allProcesses().
                filter(proc -> proc.info().command().orElse("").matches(".+\\\\" + ServiceDB.PROC_NAME)).
                findFirst().orElse(null);
    }

    /**
     * Таймер с отслеживанием завершения главного потока
     */
    private void trackMainClass(){
        //запускаем таймер с периодичность 1 сек.
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //если поток с главным классом закрыт, то производим сохранение бд и выходим из программы.
                if (Thread.getAllStackTraces().keySet().stream().noneMatch(key -> "main".equals(key.getName()))) {
                    //закрываем клиента, сервер и выходим из программы
                    pool.close();
                    if (proc != null) {
                        proc.destroy();
                    } else {
                        server.stop();
                    }
                    System.exit(0);
                }
            }
        },0, 1, TimeUnit.SECONDS);
    }

    @Override
    public List<Task> list() {
        return jedis.keys("*").stream().map(k -> get(Integer.parseInt(k))).collect(Collectors.toList());
    }

    @Override
    public boolean add(Task task) {
        counterId++;
        String id = counterId + "";
        jedis.hset(id, "name", task.getName());
        jedis.hset(id, "description", task.getDescription());
        return true;
    }

    @Override
    public boolean modify(Task task) {
        String id = task.getId() + "";
        jedis.hset(id, "name", task.getName());
        jedis.hset(id, "description", task.getDescription());
        return true;
    }

    @Override
    public Task get(int id) {
        Map<String, String> hashMap = jedis.hgetAll(id + "");
        if (hashMap.size() > 0) {
            return new Task(id, hashMap.get("name"), hashMap.get("description"));
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String idS = id + "";
        if (jedis.hgetAll(idS).size() > 0) {
            jedis.del(idS);
            return true;
        }
        return false;
    }
}