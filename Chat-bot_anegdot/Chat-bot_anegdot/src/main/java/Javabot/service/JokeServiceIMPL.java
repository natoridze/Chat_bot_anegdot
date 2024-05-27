package Javabot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Javabot.model.Joke;
import Javabot.repository.JokeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JokeServiceIMPL implements JokeService {

    private final JokeRepository jokeRepository;

    @Autowired
    public JokeServiceIMPL(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }

    @Override
    public Joke getJokeById(int id) {
        Optional<Joke> jokeOptional = jokeRepository.findById(id);
        return jokeOptional.orElse(null);
    }

    @Override
    public void createJoke(Joke joke) {
        // Проверяем, пуста ли база данных
        if (jokeRepository.count() == 0) {
            joke.setId(1);
        } else {
            // Иначе ищем максимальный ID и увеличиваем его на 1
            Integer maxId = jokeRepository.findMaxId();
            int nextId = (maxId != null) ? maxId + 1 : 1;
            joke.setId(nextId);
        }
        jokeRepository.save(joke);
    }

    @Override
    public void updateJoke(int id, Joke updatedJoke) {
        // Check if the joke with the given ID exists
        if (!jokeRepository.existsById(id)) {
            throw new IllegalArgumentException("Joke with ID " + id + " does not exist.");
        }

        // Set the ID for the updated joke
        updatedJoke.setId(id);

        // Save the updated joke to the repository
        jokeRepository.save(updatedJoke);
    }

    @Override
    public void deleteJoke(int id) {
        jokeRepository.deleteById(id);
    }
    @Override
    public void findRandomJoke() {
        jokeRepository.findRandomJoke();
    }
}