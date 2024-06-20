package sql;


import static sql.DatabaseOperations.*;

public class Main {
    public static void main(String[] args) {
        //String query = "SELECT id FROM public.post_image WHERE image_id = '66733516fee61e629e4a0efd'";
       // executeQuery(query);

        //updateIsConfirmed("54988d59-3c5c-47bd-ab53-696c6b263ab6", true);

        String email = "jannie.kerluke@gmail.com";  // Замените на нужный email
        String id = getIdByEmail(email);
        System.out.println(id);
    }



}
