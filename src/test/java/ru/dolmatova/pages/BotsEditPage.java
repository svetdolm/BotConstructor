package ru.dolmatova.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.dolmatova.models.Bot;

import static com.codeborne.selenide.Selenide.*;

public class BotsEditPage {
    private SelenideElement botsQuestionInput = $(By.name("question"));
    private SelenideElement botsAnswerInput = $(By.name("answer"));
    private SelenideElement botsAddButton = $x("//button[@class='tableSteps_submitBtn']");
    private SelenideElement dialogTable = $(By.className("tableSteps_body"));
    private SelenideElement botNameTitle = $(By.id("name"));
    private SelenideElement botTokenTitle = $(By.id("token"));

    public BotsEditPage(){
    }

    @Step(value = "Define bots question = {question}")
    public BotsEditPage inputBotsQuestion(final String question){
        botsQuestionInput.sendKeys(question);
        return this;
    }

    @Step(value = "Define bots answer = {answer}")
    public BotsEditPage inputBotsAnswer(final String answer){
        botsAnswerInput.sendKeys(answer);
        return this;
    }

    @Step(value = "Dialog create!")
    public BotsEditPage clickCreateDialogButton(){
        botsAddButton.click();
        return this;
    }

    @Step(value = "Check dialog created and appeared in list")
    public BotsEditPage checkDialogCreated(final String botDialog) {
        dialogTable
                .$$x(".//a[@test-id='question-name']")
                .shouldHave(CollectionCondition.itemWithText(botDialog));
        return this;
    }

    @Step(value = "Delete dialog {botDialog")
    public void deleteDialog(final String botDialog){
        dialogTable
                .$x(String.format(".//tr[@test-id='dialogListItem-%s']//i[@test-id='dialog-delete-button']", botDialog)).click();

    }

    @Step(value = "Check title contains proper name of the bot")
    public BotsEditPage checkBotNameTitle(final String name){
        botNameTitle.shouldHave(Condition.exactValue(name));
        return this;
    }

    @Step(value = "Check title contains proper token of the bot")
    public BotsEditPage checkBotTokenTitle(final String token){
        botTokenTitle.shouldHave(Condition.exactValue(token));
        return this;
    }

    @Step(value="Check title contains proper bot attributes")
    public BotsEditPage checkBotTitle(final Bot bot){
        return checkBotNameTitle(bot.getName())
                .checkBotTokenTitle(bot.getToken());
    }
}
