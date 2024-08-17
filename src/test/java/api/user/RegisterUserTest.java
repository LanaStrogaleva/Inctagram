package api.user;

import net.datafaker.Faker;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static sql.DatabaseOperations.getConfirmationCodeByUserId;
import static sql.DatabaseOperations.getIdByEmail;


public class RegisterUserTest {
    String accessToken;
    Integer id;
    String userName;
    String email;
    String password;
    String passwordConfirm;
    String baseUrl;

    @BeforeEach
    void setup() {

    }

    @AfterEach
    void teardown() {
    }

    @Test
    @DisplayName("Регистрация и логин пользователя")
    @Description("Регистрация пользователя со случайными данными. Запрос возвращает код ответа - 201")
    public void createUniqUser() {
        UserClient userClient = new UserClient();
        userClient.setBaseURL();
        Faker faker = new Faker();
        userName = faker.name().firstName() + faker.name().firstName();
        System.out.println(userName);
        email = faker.internet().emailAddress();
        System.out.println(email);
        password = faker.internet().password(6, 15, true, true, true) +"a!F1";
        passwordConfirm = password;
        System.out.println(password);
        System.out.println("пароль: " + password);


        User user = new User()
                .withUserName(userName)
                .withEmail(email)
                .withPassword(password);
        Response response = userClient.registrateUser(user);
        System.out.println("*****Регистрация пользователя*****");
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        userClient.checkStatusCode(response, HttpStatus.SC_NO_CONTENT);
        userClient.checkResponseBodyNotEmpty(response);
        // Получение id пользователя из базы данных
        id = getIdByEmail(email);
        System.out.println("*****Получение id пользователя из из базы данных*****");
        System.out.println("id: " + id);

        // Получение кода подтверждения из базы данных
        String confirmationCode = getConfirmationCodeByUserId(id);
        System.out.println("*****Получение кода подтверждения из базы данных*****");
        System.out.println("Код подтверждения: " + confirmationCode);

        //Подтверждение регистрацию (переход по ссылке из письма)
        userClient.confirmRegistrationUser(confirmationCode);
        System.out.println("*****Подтверждение регистрацию (переход по ссылке из письма)*****");

        // Login пользователя
        User userAuth = new User()
                .withEmail(email)
                        .withPassword(password);
        Response responseLog = userClient.loginUser(userAuth);
        accessToken = responseLog.body().path("accessToken").toString();
        // Cтатус код и тело ответа для отладки
        System.out.println("*****Login пользователя*****");
        System.out.println("Status code: " + responseLog.getStatusCode());
        System.out.println("Response body: " + responseLog.getBody().asString());
//
//        //Переход в профиль пользователя
//        ProfileClient profileClient = new ProfileClient();
//        Response responseProfile = profileClient.getUserProfileWithAuth(accessToken);
//        // Cтатус код и тело ответа для отладки
//        System.out.println("*****Переход в профиль пользователя*****");
//        System.out.println("Status code: " + responseProfile.getStatusCode());
//        System.out.println("Response body: " + responseProfile.getBody().asString());
//
//        //Изменение профиля пользователя
//        Profile profile = new Profile()
//                .withUsername(username+"AAAA")
//                .withFirstName("First")
//                .withLastName("Last")
//                .withCountry("USA")
//                .withCity("San Francisco")
//                .withAboutMe("Hi everybody");
//        Response responseChangeProfile = profileClient.updateUserProfileWithAuth(profile, accessToken);
//        System.out.println("*****Изменение профиля пользователя*****");
//        System.out.println("Status code: " + responseChangeProfile.getStatusCode());
//        System.out.println("Response body: " + responseChangeProfile.getBody().asString());
//
//        //Загрузка аватара
//        String pathName = "C:\\аватар1.png";
//        Response responseProfileUploadAvatar = profileClient.uploadUserAvatar(pathName);
//        // Cтатус код и тело ответа для отладки
//        System.out.println("*****Загрузка аватара *****");
//        System.out.println("Status code: " + responseProfile.getStatusCode());
//        System.out.println("Response body: " + responseProfile.getBody().asString());

    }

}


