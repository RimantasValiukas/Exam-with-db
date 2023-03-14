package lt.code.academy.data;

public enum Teacher {
    TEACHER("Antanas", "Kazlauskas", "destytojas", "labas");

    private final String name;
    private final String lastName;
    private final String userName;
    private final String password;

    Teacher(String name, String lastName, String userName, String password) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
