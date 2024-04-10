package io.proj3ct.Chatbot_anegdot.controller;

import io.proj3ct.Chatbot_anegdot.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.proj3ct.Chatbot_anegdot.service.JokeService;
import java.util.List;

@RestController
public class JokeController {
    @Autowired
    private JokeService jokeService;

    // Метод для выдачи всех анекдотов
    @GetMapping("/jokes")
    public List<Joke> getAllJokes() {
        return jokeService.getAllJokes();
    }

    // Метод для выдачи анекдота по ID
    @GetMapping("/jokes/{id}")
    public Joke getJokeById(@PathVariable int id) {
        return jokeService.getJokeById(id);
    }

    // Метод для создания нового анекдота
    @PostMapping("/jokes")
    public void createJoke(@RequestBody Joke joke) {
        jokeService.createJoke(joke);
    }

    // Метод для изменения существующего анекдота
    @PutMapping("/jokes/{id}")
    public void updateJoke(@PathVariable int id, @RequestBody Joke updatedJoke) {
        jokeService.updateJoke(id, updatedJoke);
    }

    // Метод для удаления анекдота по ID
    @DeleteMapping("/jokes/{id}")
    public void deleteJoke(@PathVariable int id) {
        jokeService.deleteJoke(id);
    }
    @GetMapping("/jokes/random")
    public void findRandomJoke() {
        jokeService.getRandomJoke();
    }
}
