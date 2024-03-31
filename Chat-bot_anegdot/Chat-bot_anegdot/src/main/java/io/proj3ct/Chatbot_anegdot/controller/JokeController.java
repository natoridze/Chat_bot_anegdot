package io.proj3ct.Chatbot_anegdot.controller;

import io.proj3ct.Chatbot_anegdot.model.Joke;
import io.proj3ct.Chatbot_anegdot.model.JokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/jokes")
public class JokeController {

    private final JokeRepository jokeRepository;

    @Autowired
    public JokeController(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Joke>> getAllJokes() {
        return ResponseEntity.ok(jokeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Joke> createJoke(@RequestBody Joke joke) {
        return ResponseEntity.ok(jokeRepository.save(joke));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Joke> updateJoke(@PathVariable Integer id, @RequestBody Joke jokeDetails) {
        Optional<Joke> jokeOptional = jokeRepository.findById(id);
        if (!jokeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        jokeDetails.setId(id);
        return ResponseEntity.ok(jokeRepository.save(jokeDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJoke(@PathVariable Integer id) {
        if (!jokeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jokeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
