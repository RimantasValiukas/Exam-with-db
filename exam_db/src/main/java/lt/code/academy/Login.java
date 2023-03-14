package lt.code.academy;

import lt.code.academy.data.Student;
import lt.code.academy.data.Teacher;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Scanner;

public class Login {
    private final Service service;
    private final TeacherAccount teacherAccount;
    private final StudentAccount studentAccount;
    Scanner sc;

    public Login(Service service) {
        this.service = service;
        teacherAccount = new TeacherAccount(service);
        studentAccount = new StudentAccount(service);
        sc = new Scanner(System.in);
    }

    private void login() {
        String userName;
        String password;
        Student student;
        do {
            System.out.println("Įveskite prisijungimo vardą:");
            userName = sc.nextLine();
            if (userName.equals(Teacher.TEACHER.getUserName())) {
                teacherLogin();
                return;
            }
            System.out.println("Įveskite slaptažodį:");
            password = sc.nextLine();
            student = service.studentLogin(userName, DigestUtils.sha256Hex(password));
            if (student == null) {
                System.out.println("Neteisingas prisijungimo vardas arba slaptažodis!");
            }
        } while (student == null);
        System.out.printf("Sėkmingai prisijungei %s %s%n", student.getName(), student.getLastName());
        studentAccount.student = student;
        studentAccount.studentMenu();
    }

    private void teacherLogin() {
        System.out.println("Įveskite slaptažodį:");
        String password = sc.nextLine();
        while (!password.equals(Teacher.TEACHER.getPassword())) {
            System.out.println("Slaptažodis neteisingas, pakartokite:");
            password = sc.nextLine();
        }
        System.out.printf("Sveiki dėstytojau %s %s%n", Teacher.TEACHER.getName(), Teacher.TEACHER.getLastName());
        teacherAccount.teacherMenu();
    }

    private void registration() {
        System.out.println("Įveskite prisijungimo vardą:");
        String userName = sc.nextLine();
        if (!service.checkIfUserNameAvailable(userName)) {
            System.out.println("Toks prisijungimo vardas užimtas!");
            return;
        }
        System.out.println("Įveskite savo vardą:");
        String name = sc.nextLine();
        System.out.println("Įveskite savo pavardę:");
        String lastName = sc.nextLine();
        String password = createPassword();
        Student student = new Student(null, userName, password, name, lastName);
        service.creatNewUser(student);
        System.out.println("Vartotojas sėkmingai sukurtas.");
        loginMenu();
    }

    private String createPassword() {
        String password;
        String repeatedPassword;
        do {
            System.out.println("Įveskite slaptažodį");
            password = sc.nextLine();
            System.out.println("Pakartokite slaptažodį");
            repeatedPassword = sc.nextLine();
            if (!password.equals(repeatedPassword)) {
                System.out.println("Slaptažodžiai nesutampa, pakartokite");
            }
        } while (!password.equals(repeatedPassword));
        return DigestUtils.sha256Hex(password);
    }

    public void loginMenu() {
        System.out.println("""
                1 - Prisijungti
                2 - Registruotis
                3 - Išeiti""");

        String action = sc.nextLine();

        switch (action) {
            case "1" -> login();
            case "2" -> registration();
            case "3" -> System.out.println("Programa baigė darbą.");
            default -> System.out.println("Tokios funkcijos nėra");
        }
    }



}
