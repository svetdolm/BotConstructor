package ru.dolmatova.tests;


import org.junit.jupiter.api.Test;
import ru.dolmatova.models.Bot;
import ru.dolmatova.pages.BotsEditPage;
import ru.dolmatova.pages.BotsPage;
import ru.dolmatova.utils.FixtureHelper;

import java.util.UUID;


public class BotTests extends BaseTest {

    private BotsPage botsPage;
    private BotsEditPage botsEditPage;
    private Bot bot;

    @Test
    public void createBotTest() {
        bot = new Bot(UUID.randomUUID().toString(), "TestBotToken");
        botsPage = new BotsPage()
                .createBotAndCheckCreationDone(bot);
    }

    @Test
    public void createBotTestWithEmptyName() {
        bot = new Bot("", "TestBotToken");
        botsPage = new BotsPage()
                .createBotAndCheckCreationFail(bot);
    }

    @Test
    public void createBotTestWithEmptyToken() {
        bot = new Bot(UUID.randomUUID().toString(), "");
        botsPage = new BotsPage()
                .createBotAndCheckCreationFail(bot);
    }

    @Test
    public void createBotTestWithEmptyAttributes() {
        bot = new Bot("", "");
        botsPage = new BotsPage()
                .createBotAndCheckCreationFail(bot);
    }

    @Test
    public void createTalkBotTest(){
        bot = FixtureHelper.createBot(new Bot(UUID.randomUUID().toString(), "TestBotToken"));
        botsEditPage = new BotsPage()
                .openBotEditPage(bot)
                .checkBotTitle(bot);
    }
}
