package selenium;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;

import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.By;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    @Author: Egemen Bozkus

    Learning Selenide Test Automation Framework
 */

public class SeleniumTest {

    @Test
    public void automationTest() {
        open("https://explorecalifornia.org/contact.htm");

        // Wait for the first and last form to appear on the page
        $("#name").should(appear);
        $("#comments").should(appear);

        $("#name").setValue("Greatest Software Tester Alive");
        $("#state").selectOptionContainingText("Washington");
        $("#backpack").click();
        $(By.name("newsletter")).selectRadio("yes");

        $("#name").shouldHave(value("Greatest Software Tester Alive"));

        String state = $("#state").getSelectedText();
        assert(state.equals("Washington"));

        $("#backpack").shouldBe(selected);

        $(By.name("newsletter")).shouldHave(value("yes"));
    }

    @Test
    public void checkBoxTest() {
        open("https://explorecalifornia.org/contact.htm");

        List<SelenideElement> elements = $$(byAttribute("type", "checkbox"));

        System.out.println("Selecting all checkboxes...");
        for (SelenideElement el : elements) {
            //System.out.println(el.name());
            el.click();
            el.shouldBe(selected);
        }
        System.out.println("Done\n");

        System.out.println("Un-selecting all checkboxes...");
        for (SelenideElement el : elements) {
            //System.out.println(el.name());
            el.click();
            el.shouldBe(not(selected));
        }
        System.out.println("Done\n");

    }

    @Test
    public void stateSelectorTest() {
        open("https://explorecalifornia.org/contact.htm");

        List<SelenideElement> elements = $$(byTagName("option"));

        for (SelenideElement el : elements) {
            System.out.println(el.text());

            $("#state").selectOptionContainingText(el.text());

            String state = $("#state").getSelectedText();
            assert(state.equals(el.text()));
        }

    }

    @Test
    public void calendarTest() {
        open("https://explorecalifornia.org/contact.htm");

        String date = java.time.LocalDate.now().toString();     // format: yyyy-mm-dd
        String regex = "(\\d{4})-(\\d{2})-(\\d{2})";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(date);

        String year = "";
        String month = "";
        String day = "";
        String finalDate = "";

        if (m.find()) {
            year = m.group(1);
            month = m.group(2);
            day = m.group(3);
            finalDate = month + day + year;
        }

        System.out.println("Today's date: " + month + "/" + day + "/" + year);

        $("input#tripDate").setValue(finalDate);
        System.out.println("Date in calendar: " + $("input#tripDate").val());
        $("input#tripDate").shouldHave(value(date));

    }
}
