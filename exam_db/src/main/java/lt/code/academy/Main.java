package lt.code.academy;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        Login login = new Login(service);

        login.loginMenu();

    }
}
