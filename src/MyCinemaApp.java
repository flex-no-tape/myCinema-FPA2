public class MyCinemaApp{
    public static void main(String[] args){
    DatabaseManager database = new DatabaseManager();
    database.initialiseDB();

    User newUser = new User("fletcher139", "1234", "Fletcher", "Brown");
    UserDAO userConn = new UserDAO();
    userConn.registerUser(newUser);

    }
}
