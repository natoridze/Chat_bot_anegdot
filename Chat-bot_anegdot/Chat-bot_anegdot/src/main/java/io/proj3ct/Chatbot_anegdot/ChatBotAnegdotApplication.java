package io.proj3ct.Chatbot_anegdot;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@AutoConfigurationPackage
@SpringBootApplication
public class ChatBotAnegdotApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
        new SpringApplicationBuilder(ChatBotAnegdotApplication.class)
                .run(args);

	}

}
