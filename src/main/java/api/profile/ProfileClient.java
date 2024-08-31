package api.profile;

import api.user.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ProfileClient {
    String pathName;
    public static String BASE_URL = "https://inctagram.work";
    private static String GET_USER_PROFILE_URL = "/api/v1/users/profile";
    private static String UPDATE_USER_PROFILE_URL = "/api/v1/user";
    private static String UPLOAD_USER_AVATAR_URL = "/api/v1/user/avatar";
    private static String DELETE_USER_AVATAR_URL = "/api/v1/user/avatar";

    @Step("Перейти по базовому URL")
    public void setBaseURL() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Войти в профиль пользователя {user} с авторизацией")
    public Response getUserProfileWithAuth(String accessToken) {
        return given()
                .auth()
                .oauth2(accessToken)
                .when()
                .get(GET_USER_PROFILE_URL);
    }

    @Step("Изменить данные профиля пользователя {user}")
    public Response updateUserProfileWithAuth(Profile profile, String accessToken) {
        return given()
                .auth()
                .oauth2(accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(profile)
                .when()
                .put(UPDATE_USER_PROFILE_URL);
    }

    @Step("Загрузить аватар пользователя {user}")
    public Response uploadUserAvatar(String pathName) {
        File pngFile = new File(pathName);
        return given()
                .header("Content-Type", "multipart/form-data ")
                .multiPart(pngFile)
                .when()
                .post(UPLOAD_USER_AVATAR_URL);
    }

    @Step("Удалить аватар пользователя {user}")
    public void deleteUser(String accessToken) {
        given()
                .auth()
                .oauth2(accessToken)
                .when()
                .delete(DELETE_USER_AVATAR_URL)
                .body().asString();
    }
    @Step("Проверить статус код")
    public void checkStatusCode(Response response, int httpStatus) {
        response.then().statusCode(httpStatus);
    }

}
