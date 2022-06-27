package ru.dolmatova.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.dolmatova.models.Bot;

import static io.restassured.RestAssured.given;

public class FixtureHelper {

    @Step(value = "Create bot via REST")
    public static Bot createBot(final Bot bot){
        Response response = given()
                .param("botName", bot.getName())
                .param("token", bot.getToken())
        .when()
                .post("/bots/save")
        .then()
                .log().all()
                .extract().response();
        return response.getBody().as(Bot.class);
    }
}
