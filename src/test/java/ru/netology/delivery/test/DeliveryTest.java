package ru.netology.delivery.test;
import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.test.DataGenerator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    private static Faker faker;
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[placeholder='Город']").setValue(DataGenerator.generateCity());
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "a");
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $("[class='notification__title']").shouldBe(visible);
        $x("//div[contains(text(), 'Встреча успешно')]").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible);
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "a");
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        $("[class='button__text']").click();
        $x("//div[contains(text(), 'У вас уже запланирована встреча')]").shouldBe(visible);
        $x("//div[contains(text(), 'Перепланировать')]").click();
        $x("//div[contains(text(), 'Встреча успешно')]").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(visible);
    }
}