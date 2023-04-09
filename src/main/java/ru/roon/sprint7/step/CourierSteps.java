package ru.roon.sprint7.step;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.roon.sprint7.model.Courier;

import static io.restassured.RestAssured.given;
import static ru.roon.sprint7.constants.Url.COURIER_DELETE_PATH;
import static ru.roon.sprint7.constants.Url.COURIER_LOGIN_PATH;
import static ru.roon.sprint7.constants.Url.COURIER_PATH;

public class CourierSteps {

    @Step("Создание курьера")
    public Response createCourier(Courier request) {
        return given()
            .body(request)
            .when()
            .post(COURIER_PATH);
    }

    @Step("Логин курьера")
    public Response loginCourier(Courier courier) {
        return given()
            .body(courier)
            .when()
            .post(COURIER_LOGIN_PATH);
    }

    @Step("Удаление курьера")
    public void deleteCourier(long courierId) {
        given()
            .pathParam("id", courierId)
            .delete(COURIER_DELETE_PATH);
    }
}
