package ru.dolmatova.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;

public class BaseTest {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = 8080;
        RestAssured.defaultParser = Parser.JSON;

        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://127.0.0.1:8080";

        clearBrowserCookies();
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
        SelenideLogger.removeListener("AllureSelenide");
    }
}
