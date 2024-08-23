package sql;


import api.user.UserClient;

import static sql.DatabaseOperations.*;

public class Main {
    public static void main(String[] args) {
        //подтвердить регистрацию
        String email = "aaa@aa.com";  // Заменить на нужный email
        String confCode = getConfirmationCodeByUserId(getIdByEmail(email));
        UserClient userClient = new UserClient();
        userClient.setBaseURL();
        userClient.confirmRegistrationUser(confCode);
    }



}
