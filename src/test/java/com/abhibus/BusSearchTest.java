package com.abhibus;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BusSearchTest {

    @BeforeClass
    @Parameters({"browser"})
    public static void setup(String browser) {
        Configuration.timeout = 10 * 1000;
        Configuration.browser = browser;
        Configuration.baseUrl = "https://www.abhibus.com";
    }

    @DataProvider
    public Object[][] itineraries() {
        return new String[][]{
                {"Tenal", "Tenali", "Hyderaba", "Hyderabad", "Tenali → Hyderabad"}
        };
    }

    @BeforeMethod
    public void beforeMethod() {
        open("/");
    }

    @Test(description = "Search Buses", dataProvider = "itineraries")
    public void busSearch(String from, String fromExpected, String to, String toExpected, String expected) {
        //to select leaving from
        $("#source").setValue(from);
        $("#ui-id-1 li.ui-menu-item")
                .shouldHave(text(fromExpected))
                .shouldBe(appear)
                .click();
        //to select destination
        $("#destination").setValue(to);
        $("#ui-id-2 li.ui-menu-item")
                .shouldHave(text(toExpected))
                .shouldBe(appear)
                .click();

        // from date: 04-October-2019
        $("#datepicker1").click();
        SelenideElement datePicker1 = $(".ui-datepicker-group-first");
        while (!datePicker1.find("span.ui-datepicker-month").text().trim().equals("October")) {
            $(".ui-datepicker-group-last").find("span").shouldHave(text("Next")).click();
        }
        datePicker1.findAll("tr td a").filterBy(text("4")).first().click();

        // to date: 06-October-2019
        $("#datepicker2").click();
        SelenideElement datePicker2 = $(".ui-datepicker-group-first");
        while (!datePicker2.find("span.ui-datepicker-month").text().trim().equals("October")) {
            $(".ui-datepicker-group-last").find("span").shouldHave(text("Next")).click();
        }
        datePicker2.findAll("tr td a").filterBy(text("6")).first().click();

        // click search
        $("a[title='Search Buses']").click();

        // assert search page
        $("#SubHead1way h1").shouldHave(text(expected));
    }
}