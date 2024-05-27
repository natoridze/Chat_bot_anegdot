package Javabot.service;

import Javabot.model.Joke;
import Javabot.repository.JokeRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Javabot.repository.JokeRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TelegramBotService  {
    private final TelegramBot telegramBot;
    private final JokeRepository jokeRepository;
    public TelegramBotService(@Autowired TelegramBot telegramBot, @Autowired JokeRepository jokeRepository) {
        this.telegramBot = telegramBot;
        this.jokeRepository = jokeRepository;
        this.telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::buttonClickReact);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, Throwable::printStackTrace);
    }
    private void buttonClickReact(Update update) {
        String text = update.message().text();
        if ("/help".equalsIgnoreCase(text)) {
            processHelpCommand(update);
        } else if ("/jokes".equalsIgnoreCase(text)) {
            sendAllJokes(update);
        } else if (text.startsWith("/jokes/")) {
            processJokeById(update, text);
        } else if ("/random".equalsIgnoreCase(text)){
            sendRandomJoke(update);
        }
    }

    private void sendRandomJoke(Update update) {
        // Извлекаем случайную шутку из репозитория
        String randomJoke = jokeRepository.findRandomJoke();
        // Подготавливаем сообщение для отправки
        SendMessage request = new SendMessage(update.message().chat().id(), randomJoke)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyToMessageId(update.message().messageId());
        // Отправляем сообщение
        this.telegramBot.execute(request);
    }

    private void processHelpCommand(Update update) {
        // Создайте сообщение с помощью текста, который описывает доступные команды
        String helpMessage = "Доступные команды:\n" +
                "/jokes - Получить список всех анекдотов\n" +
                "/jokes/{id} - Получить анекдот по ID\n" +
                "/jokes/random - Получить случайный анекдот\n" +
                "/help - Получить справку о доступных командах";

        // Отправьте сообщение с помощью бота
        SendMessage request = new SendMessage(update.message().chat().id(), helpMessage);
        telegramBot.execute(request);
    }
    private void sendAllJokes(Update update) {
        List<Joke> allJokes = jokeRepository.findAll();
        if (allJokes.isEmpty()) {
            SendMessage request = new SendMessage(update.message().chat().id(), "Список анекдотов пуст.");
            telegramBot.execute(request);
        } else {
            StringBuilder jokesList = new StringBuilder("Список анекдотов:\n");
            for (Joke joke : allJokes) {
                jokesList.append(joke.getId()).append(". ").append(joke.getText()).append("\n");
            }
            SendMessage request = new SendMessage(update.message().chat().id(), jokesList.toString());
            telegramBot.execute(request);
        }
    }

    private void processJokeById(Update update, String text) {
        // Разбираем команду и извлекаем ID анекдота
        String[] parts = text.split("/");
        if (parts.length != 3) {
            // Некорректный формат команды
            SendMessage request = new SendMessage(update.message().chat().id(), "Некорректный формат команды.");
            telegramBot.execute(request);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            // Неверный формат ID
            SendMessage request = new SendMessage(update.message().chat().id(), "ID анекдота должен быть целым числом.");
            telegramBot.execute(request);
            return;
        }
        // Получаем анекдот по ID из репозитория
        Optional<Joke> optionalJoke = jokeRepository.findById(id);
        if (optionalJoke.isPresent()) {
            Joke joke = optionalJoke.get();
            SendMessage request = new SendMessage(update.message().chat().id(), joke.getText());
            telegramBot.execute(request);
        } else {
            // Анекдот с указанным ID не найден
            SendMessage request = new SendMessage(update.message().chat().id(), "Анекдот с указанным ID не найден.");
            telegramBot.execute(request);
        }
    }
}