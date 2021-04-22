package ru.dolmatova.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.dolmatova.utilities.CommonBot;

@RestController("/bots")
public class BotController {

    @PostMapping("/start/{id}")
    public void startBot(@PathVariable final int id) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CommonBot(id));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
