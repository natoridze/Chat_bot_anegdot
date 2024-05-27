package Javabot.repository;
import Javabot.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Integer> {

    // Метод для получения всех шуток
    List<Joke> findAll();

    // Метод для получения шутки по ID
    Optional<Joke> findById(Integer id);

    @Query(value = "SELECT MAX(id) FROM Joke")
    Integer findMaxId();

    @Query(value = "SELECT * FROM Joke ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String findRandomJoke();
}