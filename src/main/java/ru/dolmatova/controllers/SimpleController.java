package ru.dolmatova.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.dolmatova.dao.BotRepository;
import ru.dolmatova.models.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SimpleController {
    @Value("${spring.application.name}")
    String appName;

    @Autowired
    BotRepository botRepository;


    @GetMapping("/")
    public String homePage(Model model) {
        List<Bot> bots = new ArrayList<>();
        botRepository.findAll().forEach(bots::add);
        model.addAttribute("bots", bots);
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/bots/get/{id}")
    public String getBotById(@PathVariable final int id, Model model) {
        Optional<Bot> bot = botRepository.findById((long) id);
        bot.ifPresent(value -> model.addAttribute("bot", value));
        return "bot_view";
    }
}
