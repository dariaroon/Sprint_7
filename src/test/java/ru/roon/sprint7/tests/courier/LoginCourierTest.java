package ru.roon.sprint7.tests.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.roon.sprint7.model.Courier;
import ru.roon.sprint7.step.CourierSteps;

import java.util.UUID;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static ru.roon.sprint7.constants.Url.QA_SCOOTER_URL;

public class LoginCourierTest {

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

    @Before
    public void createCourier() {
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

    @DisplayName("Логина курьера")
    @Description("Проверка успешной аутентификации курьера")
    @Test
    public void testLoginCourier() {
        courierSteps.loginCourier(new Courier().setLogin(TEST_LOGIN).setPassword(TEST_PASSWORD)).then()
            .assertThat()
            .body("id", notNullValue())
            .and()
            .statusCode(HTTP_OK);
    }


    @Test
    @DisplayName("Логин с неправильным паролем")
    @Description("Проверка логина с неправильным паролем")
    public void testLoginWithIncorrectPassword() {
        courierSteps.loginCourier(new Courier().setLogin(TEST_LOGIN).setPassword("incorrect"))
            .then()
            .assertThat()
            .body("message", equalTo("Учетная запись не найдена"))
            .and()
            .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    @DisplayName("Логин без указания логина")
    @Description("Проверка аутентификации без указания логина")
    public void testLoginWithoutLogin() {
        courierSteps.loginCourier(new Courier().setPassword(TEST_PASSWORD))
            .then()
            .assertThat()
            .body("message", equalTo("Недостаточно данных для входа"))
            .and()
            .statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Логин не существующего курьера")
    @Description("Проверка логина не существующего курьера")
    public void testLoginNonExistingCourier() {
        courierSteps.loginCourier(new Courier().setLogin(UUID.randomUUID().toString()).setPassword("password"))
            .then()
            .assertThat()
            .body("message", equalTo("Учетная запись не найдена"))
            .and()
            .statusCode(HTTP_NOT_FOUND);
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
