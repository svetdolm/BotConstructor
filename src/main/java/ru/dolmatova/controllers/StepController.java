package ru.dolmatova.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dolmatova.dao.BotRepository;
import ru.dolmatova.dao.StepRepository;
import ru.dolmatova.models.Bot;
import ru.dolmatova.models.Step;

import java.util.Optional;

@RestController("/steps")
public class StepController {

    @Autowired
    StepRepository stepRepository;

    @Autowired
    BotRepository botRepository;

    @PostMapping("/save")
    public void saveStep(@RequestParam(name = "botId") final int botId,
                         @RequestParam(name = "question") final String question,
                         @RequestParam(name = "answer") final String answer) {
        Optional<Bot> bot = botRepository.findById((long) botId);
        bot.ifPresent(value -> stepRepository.save(new Step(question, answer, value)));
    }
}
