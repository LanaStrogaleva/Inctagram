package api.user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserClient {

    public static String BASE_URL = "https://inctagram.work";
    private static String REGISTRATION_USER_URL = "/api/v1/auth/registration";
    private static String REGISTRATION_CONFIRMATION_USER_URL = "/api/v1/auth/registration-confirmation";
    private static String LOGIN_USER_URL = "/api/v1/auth/login";
    private static String REFRESH_TOKEN_URL = "/api/v1/auth/update-token";
    private static String LOGOUT_USER_URL = "/api/v1/auth/logout";


    @Step("Перейти по базовому URL")
    public void setBaseURL() {
        RestAssured.baseURI = BASE_URL;
    }


    @Step("Зарегистрировать пользователя {user}")
    public Response registrateUser(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post(REGISTRATION_USER_URL);
    }
    @Step("Подтвердить регистрацию {user} - переход по ссылке из письма")
    public Response confirmRegistrationUser(String confirmationCode) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body("{\n" +
                        "  \"confirmationCode\": \"" +confirmationCode+"\"\n" +
                        "}")
                .when()
                .post(REGISTRATION_CONFIRMATION_USER_URL);
    }

    @Step("Логин пользователя {user}")
    public Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_URL);
    }

    @Step("Получить refresh Token пользователя {user}")
    public Response refreshTokenUser(String accessToken) {
        return given()
                .auth()
                .oauth2(accessToken)
                .and()
                .when()
                .post(REFRESH_TOKEN_URL);
    }


    @Step("Выйти из профиля пользователя {user}")
    public Response logoutUserProfile(String refreshToken) {
        return given()
                .header("Autorization", "ApiKey " + refreshToken + "")
                .when()
                .post(LOGOUT_USER_URL);
    }

    @Step("Проверить статус код")
    public void checkStatusCode(Response response, int httpStatus) {
        response.then().statusCode(httpStatus);
    }

    @Step("Проверить сообщение в теле ответа")
    public void checkResponseBodyMessage(Response response, String message ) {
        response.then().assertThat()
                .body("message", equalTo(message));
        System.out.println(response.body().path("message").toString());
    }

    @Step("Проверить имя пользователя в теле ответа")
    public void checkNameResponseBody(Response response, String value ) {
        response.then().assertThat()
                .body("user.username", equalTo(value));
    }

    @Step("Проверить email пользователя в теле ответа")
    public void checkEmailResponseBody(Response response, String value ) {
        response.then().assertThat()
                .body("user.email", equalTo(value));
    }


    @Step("Проверить успешность выполнения в теле ответа")
    public void checkIsSuccessResponse(Response response, Boolean success ) {
        response.then().assertThat()
                .body("success", equalTo(success));
        System.out.println(response.body().path("success").toString());
    }

    @Step("Проверить, что тело ответа не пустое")
    public void checkResponseBodyNotEmpty(Response response) {
        response.then().assertThat()
                .body(notNullValue());
    }

}
