package Javabot.service;
import Javabot.model.Joke;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JokeService {
    List<Joke> getAllJokes();

    Joke getJokeById(int id);

    void createJoke(Joke joke);

    void updateJoke(int id, Joke updatedJoke);

    void deleteJoke(int id);
    @Query(value = "SELECT * FROM Joke ORDER BY RAND() LIMIT 1", nativeQuery = true)
    void findRandomJoke();
}