package ru.dolmatova.utilities;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dolmatova.models.Bot;
import ru.dolmatova.models.Step;

import java.util.List;
import java.util.Optional;


public class CommonBot extends TelegramLongPollingBot {

    private final Bot bot;

    public CommonBot(int bot) {
        this.bot = bot;
    }

    @Override
    public String getBotUsername() {
        return bot.getName();
    }

    @Override
    public String getBotToken() {
        return bot.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<Step> steps = bot.getSteps();

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
