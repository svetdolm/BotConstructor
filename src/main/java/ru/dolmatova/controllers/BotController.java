package ru.dolmatova.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.dolmatova.dao.BotRepository;
import ru.dolmatova.models.Bot;
import ru.dolmatova.utilities.CommonBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("/bots")
public class BotController {

    @Autowired
    BotRepository botRepository;

    @PostMapping("/start/{id}")
    public void startBot(@PathVariable final int id) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CommonBot(id));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/save")
    public void saveBot(@RequestParam(name = "botName") final String botName,
                        @RequestParam(name = "token") final String token) {
        botRepository.save(new Bot(botName, token));
    }

    @GetMapping("/getAll")
    public List<Bot> getAllBots() {
        List<Bot> bots = new ArrayList<>();
        botRepository.findAll().forEach(bots::add);
        return bots;
    }

    @GetMapping("/get/{id}")
    public Bot getAllBots(@PathVariable final int id) {
        Optional<Bot> bot = botRepository.findById((long) id);
        return bot.orElse(null);
    }
}
