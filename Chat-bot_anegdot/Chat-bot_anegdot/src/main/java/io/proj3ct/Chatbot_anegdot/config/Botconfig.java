package io.proj3ct.Chatbot_anegdot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class Botconfig {

    @Value("${telegram.botName}")
    String botUserName;

    @Value("${telegram.botToken}")
    String token;
}
