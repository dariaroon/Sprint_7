package ru.roon.sprint7.step;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.roon.sprint7.model.Order;

import static io.restassured.RestAssured.given;
import static ru.roon.sprint7.constants.Url.CANCEL_ORDER_PATH;
import static ru.roon.sprint7.constants.Url.ORDERS_PATH;

public class OrderSteps {

    @Step("Create order")
    public Response createOrder(Order order) {
        return given()
            .body(order)
            .when()
            .post(ORDERS_PATH);
    }

    @Step("Отмена заказа")
    public void cancelOrder(String track) {
        given()
            .queryParam("track", track)
            .when()
            .put(CANCEL_ORDER_PATH);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
            .when()
            .get(ORDERS_PATH);
    }

}
