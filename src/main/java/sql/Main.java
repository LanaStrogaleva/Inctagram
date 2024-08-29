package sql;


import api.user.UserClient;
import io.restassured.response.Response;

import static sql.DatabaseOperations.*;

public class Main {
    public static void main(String[] args) {
        //подтвердить регистрацию
        UserClient userClient = new UserClient();

        String email = "lana.strogaleva@mail.ru";  // Заменить на нужный email
//        Response response = userClient.resendConfirmationLink(email);
//        System.out.println(response.getStatusCode());
        String confCode = getConfirmationCodeByUserId(getIdByEmail(email));

        userClient.setBaseURL();
        userClient.confirmRegistrationUser(confCode);

        // получить recoveryCode
//        String recoveryCode = getRecoveryCodeByUserId(email);
//        System.out.println(recoveryCode);
    }





}
