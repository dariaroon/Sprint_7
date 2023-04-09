package ru.roon.sprint7.tests.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.roon.sprint7.model.Courier;
import ru.roon.sprint7.step.CourierSteps;

import java.util.UUID;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static ru.roon.sprint7.constants.Url.QA_SCOOTER_URL;

public class CreateCourierTest {

    private static final String TEST_LOGIN = UUID.randomUUID().toString();
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_FIRST_NAME = "firstName";

    private final CourierSteps courierSteps = new CourierSteps();

    @BeforeClass
    public static void setUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri(QA_SCOOTER_URL)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    }


    @DisplayName("Проверка создание курьера")
    @Description("Проверка успешного создания курьера")
    @Test
    public void testCreateNewCourier() {
        courierSteps.createCourier(new Courier()
                .setLogin(TEST_LOGIN)
                .setFirstName(TEST_FIRST_NAME)
                .setPassword(TEST_PASSWORD))
            .then()
            .assertThat()
            .statusCode(HTTP_CREATED)
            .and()
            .assertThat()
            .body("ok", is(true));
    }

    @DisplayName("Проверка создание двух одинаковых курьера")
    @Description("Проверка не доступности создания двух одинаковых курьера")
    @Test
    public void testCreateIdenticalCouriers() {
        Courier courier = new Courier()
            .setLogin(TEST_LOGIN)
            .setFirstName(TEST_FIRST_NAME)
            .setPassword(TEST_PASSWORD);

        courierSteps.createCourier(courier)
            .then()
            .assertThat()
            .statusCode(HTTP_CREATED)
            .and()
            .assertThat()
            .body("ok", is(true));
        courierSteps.createCourier(courier)
            .then()
            .assertThat()
            .statusCode(HTTP_CONFLICT)
            .and()
            .assertThat()
            .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Создание курьера с отсутствующим логином")
    @Description("Проверка не доступности создания курьера без логина")
    @Test
    public void testCreateCourierWithoutLogin() {
        courierSteps.createCourier(new Courier().setPassword(TEST_PASSWORD).setFirstName(TEST_FIRST_NAME))
            .then()
            .assertThat()
            .statusCode(HTTP_BAD_REQUEST)
            .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void clearData() {
        Response response = courierSteps.loginCourier(
            new Courier()
                .setLogin(TEST_LOGIN)
                .setPassword(TEST_PASSWORD));

        if (response.statusCode() == 200) {
            courierSteps.deleteCourier(response.as(Courier.class).getId());
        }
    }
}
