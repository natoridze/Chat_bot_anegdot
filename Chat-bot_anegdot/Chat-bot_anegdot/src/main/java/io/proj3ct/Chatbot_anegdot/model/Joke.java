package io.proj3ct.Chatbot_anegdot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Joke {

    @Column(length = 2550000)
    private String body;

    private String category;

    @Id
    private Integer id;

    private double rating;
}