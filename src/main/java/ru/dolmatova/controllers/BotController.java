package ru.dolmatova.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.dolmatova.dao.BotRepository;
import ru.dolmatova.models.Bot;
import ru.dolmatova.models.Step;
import ru.dolmatova.utilities.CommonBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BotController {

    @Autowired
    BotRepository botRepository;

    private BotSession botSession = null;

    @PostMapping("/bots/start/{id}")
    public ResponseEntity<JsonNode> startBot(@PathVariable final int id) {
        if (botSession == null) {
            try {
                Optional<Bot> optionalBot = botRepository.findById((long) id);
                if (optionalBot.isPresent()) {
                    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                    botSession = botsApi.registerBot(new CommonBot(optionalBot.get()));
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(JsonNodeFactory
                    .instance.objectNode()
                    .put("result", "Бот успешно создан"));
        } else return ResponseEntity.ok(JsonNodeFactory
                .instance.objectNode()
                .put("result", "Нет возможности запустить еще одного бота"));
    }

    @PostMapping("/bots/stop/")
    public ResponseEntity<JsonNode> stopBot() {
        if (botSession != null && botSession.isRunning()) {
            botSession.stop();
            botSession = null;
            return ResponseEntity.ok(JsonNodeFactory
                    .instance.objectNode()
                    .put("result", "Бот успешно остановлен"));
        }
        return ResponseEntity.ok(JsonNodeFactory
                .instance.objectNode()
                .put("result", "Все боты уже остановлены"));
    }


    @PostMapping("/bots/save")
    public ResponseEntity<JsonNode> saveBot(@RequestParam(name = "botName") final String botName,
                                            @RequestParam(name = "token") final String token) throws JsonProcessingException {
        return ResponseEntity.ok(new ObjectMapper()
                .convertValue(botRepository
                        .save(new Bot(botName, token)), JsonNode.class));
    }

    @GetMapping("/bots/getAll")
    public ResponseEntity<JsonNode> getAllBots() throws JsonProcessingException {
        List<Bot> bots = new ArrayList<>();
        botRepository.findAll().forEach(bots::add);
        return ResponseEntity.ok(new ObjectMapper().convertValue(bots, JsonNode.class));
    }

    @GetMapping(value = "/bots/getSteps/{id}")
    public ResponseEntity<JsonNode> getBotStepsByBotId(@PathVariable final int id) throws JsonProcessingException {
        Optional<Bot> bot = botRepository.findById((long) id);
        List<Step> botSteps = new ArrayList<>();
        return bot.map(value -> {
            botSteps.addAll(value.getSteps());
            return ResponseEntity.ok(new ObjectMapper()
                    .convertValue(botSteps, JsonNode.class));
        }).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(JsonNodeFactory.instance.objectNode()));
    }

    @DeleteMapping("/bots/delete/{id}")
    public ResponseEntity<JsonNode> deleteBotById(@PathVariable final int id) throws JsonProcessingException {
        Optional<Bot> bot = botRepository.findById((long) id);
        if (bot.isPresent()) {
            botRepository.deleteById((long) id);
            return ResponseEntity.ok(new ObjectMapper()
                    .convertValue(bot.get(), JsonNode.class));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonNodeFactory.instance.objectNode());
    }
}
