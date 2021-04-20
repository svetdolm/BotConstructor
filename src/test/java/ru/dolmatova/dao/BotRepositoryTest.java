package ru.dolmatova.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dolmatova.models.Bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BotRepositoryTest {

    @Autowired
    private BotRepository botRepository;

    @Test
    public void whenFindingBotById_thenCorrect() {
        botRepository.save(new Bot("John", "123213123"));
        assertEquals("John", (botRepository.findById(1L)).get().getName());
    }

}