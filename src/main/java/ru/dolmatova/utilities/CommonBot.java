package ru.dolmatova.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dolmatova.dao.BotRepository;
import ru.dolmatova.models.Bot;
import ru.dolmatova.models.Step;

import java.util.List;
import java.util.Optional;


public class CommonBot extends TelegramLongPollingBot {

    private final int _id;

    @Autowired
    BotRepository botRepository;

    public CommonBot(int _id) {
        this._id = _id;
    }

    @Override
    public String getBotUsername() {
        Optional<Bot> bot = botRepository.findById((long) _id);
        return bot.map(Bot::getName).orElse(null);
    }

    @Override
    public String getBotToken() {
        Optional<Bot> bot = botRepository.findById((long) _id);
        return bot.map(Bot::getToken).orElse(null);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<Bot> bot = botRepository.findById((long) _id);
        List<Step> steps = bot.map(Bot::getSteps).orElse(null);

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            if (steps != null) {
                Optional<Step> step = steps.stream()
                        .filter(s -> s
                                .getQuestion()
                                .equals(update.getMessage().getText()))
                        .findFirst();
                if (step.isPresent()) {
                    message.setText(step.get().getAnswer());
                } else message.setText("Бот не распознал команду");
            } else message.setText("У бота отсутствует список команд");

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
