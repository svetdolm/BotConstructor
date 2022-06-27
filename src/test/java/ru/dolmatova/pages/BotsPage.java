package ru.dolmatova.pages;

import com.codeborne.selenide.CollectionCondition;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.dolmatova.models.Bot;

import static com.codeborne.selenide.Selenide.*;

public class BotsPage {
    private SelenideElement botsNameInput = $x("//input[@class='tableBots_input']");
    private SelenideElement botsTokenInput = $(By.name("token"));
    private SelenideElement botsCreateButton = $x("//button[@class='tableBots_submitBtn']");
    private SelenideElement botsTable = $(By.className("tableBots_body"));

    public BotsPage() {
        open("/");
    }

    @Step(value = "Define bots name = {name}")
    public BotsPage inputBotsName(final String name) {
        botsNameInput.sendKeys(name);
        return this;
    }

    @Step(value = "Define bots token = {token}")
    public BotsPage inputBotsToken(final String token) {
        botsTokenInput.sendKeys(token);
        return this;
    }

    @Step(value = "Bots create")
    public BotsPage clickCreateBotButton() {
        botsCreateButton.click();
        return this;
    }

    @Step(value = "Check bot created and appeared in bot list")
    public BotsPage checkBotCreated(final String botName) {
        botsTable
                .$$x(".//a[@test-id='bot-name']")
                .shouldHave(CollectionCondition.itemWithText(botName));
        return this;
    }

    @Step(value = "Delete bot {botName}")
    public void deleteBot(final String botName) {
        botsTable
                .$x(String.format(".//tr[@test-id='botListItem-%s']//i[@test-id='bot-delete-button']", botName)).click();
    }

    @Step(value = "Create bot and check creation is done")
    public BotsPage createBotAndCheckCreationDone(final Bot bot) {
        return inputBotsName(bot.getName())
                .inputBotsToken(bot.getToken())
                .clickCreateBotButton()
                .checkBotCreated(bot.getName());
    }

    @Step(value = "Create bot and check creation fail")
    public BotsPage createBotAndCheckCreationFail(final Bot bot) {
        return inputBotsName(bot.getName())
                .inputBotsToken(bot.getToken())
                .clickCreateBotButton()
                .checkBotNotCreated(bot.getName());
    }

    @Step("Open bots edit page by clicking on it's link")
    public BotsEditPage openBotEditPage(final Bot bot) {
        botsTable.$x(String.format(".//a[text()='%s']", bot.getName())).click();
        return new BotsEditPage();
    }

    public Long getBotsId(final Bot bot) {
        String id = botsTable
                .$x(String.format(".//tr[@test-id='botListItem-%s']//i[@test-id='bot-delete-button']", bot.getName()))
        .getAttribute("data-id");
        return Long.parseLong(id);
    }

    @Step(value = "Check bot didn`t created")
    public BotsPage checkBotNotCreated(final String name) {
        botsTable
                .$$x(".//a[@test-id='bot-name']")
                .shouldHave(CollectionCondition.noneMatch("Empty name", el -> el.getText().equals(name)));
        return this;
    }
}
