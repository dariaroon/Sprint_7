package ru.roon.sprint7.tests.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.roon.sprint7.model.Order;
import ru.roon.sprint7.step.OrderSteps;

import java.util.Collections;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static ru.roon.sprint7.constants.Url.QA_SCOOTER_URL;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private static final String TEST_FIRST_NAME = "firstName";
    private static final String TEST_LAST_NAME = "lastName";
    private static final String TEST_ADDRESS = "address";
    private static final String TEST_METRO_STATION = "stationMetro";
    private static final String TEST_PHONE = "phone";
    private static final int TEST_RENT_TIME = 1;
    private static final String TEST_DELIVERY_DATE = "2022-02-02";
    private static final String TEST_COMMENT = "comment";


    private final Order order;
    private final OrderSteps orderSteps;

    public CreateOrderTest(Order order) {
        this.order = order;
        this.orderSteps = new OrderSteps();
    }

    @BeforeClass
    public static void setUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri(QA_SCOOTER_URL)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getOrders() {
        return new Object[][]{
            {new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, List.of("BLACK"), null)},
            {new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, List.of("GREY"), null)},
            {new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, List.of("BLACK", "GREY"), null)},
            {new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, Collections.emptyList(), null)}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Успешное создание заказа")
    public void testCreateOrder() {
        var response = orderSteps.createOrder(order);
        response.then()
            .assertThat()
            .body("track", notNullValue())
            .and()
            .statusCode(HTTP_CREATED);
        order.setTrack(response.jsonPath().getString("track"));
    }

    @After
    public void clearData() {
        orderSteps.cancelOrder(order.getTrack());
    }
}
