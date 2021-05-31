package ru.dolmatova.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dolmatova.dao.BotRepository;
import ru.dolmatova.dao.StepRepository;
import ru.dolmatova.models.Bot;
import ru.dolmatova.models.Step;

import java.util.Optional;

@RestController
public class StepController {

    @Autowired
    StepRepository stepRepository;

    @Autowired
    BotRepository botRepository;

    @PostMapping(value = "/steps/save")
    public ResponseEntity<JsonNode> saveStep(@RequestParam(name = "bot_id") final String botId,
                                             @RequestParam(name = "question") final String question,
                                             @RequestParam(name = "answer") final String answer) {
        Optional<Bot> bot = botRepository.findById(Long.valueOf(botId));
        return bot.map(value ->
                ResponseEntity.ok(new ObjectMapper()
                        .convertValue(stepRepository
                                .save(new Step(question, answer, value)), JsonNode.class)))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(JsonNodeFactory.instance.objectNode()));
    }

    @DeleteMapping("/steps/delete/{id}")
    public ResponseEntity<JsonNode> deleteStepById(@PathVariable final int id) {
        Optional<Step> step = stepRepository.findById((long) id);
        if (step.isPresent()) {
            stepRepository.delete(step.get());
            return ResponseEntity.ok(new ObjectMapper()
                    .convertValue(step.get(), JsonNode.class));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonNodeFactory.instance.objectNode());
    }
}