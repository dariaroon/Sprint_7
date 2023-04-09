package ru.roon.sprint7.tests.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.roon.sprint7.step.OrderSteps;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;
import static ru.roon.sprint7.constants.Url.QA_SCOOTER_URL;

public class ListOrderTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @BeforeClass
    public static void setUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri(QA_SCOOTER_URL)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    }

    @Test
    @DisplayName("Список заказов")
    @Description("Проверка получения списка заказов")
    public void testOrdersList() {
        orderSteps.getOrders()
            .then()
            .assertThat()
            .statusCode(HTTP_OK)
            .assertThat()
            .body(notNullValue());
    }
}
