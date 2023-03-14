package lt.code.academy.data;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private ObjectId id;
    private String userName;
    private String password;
    private String name;
    private String lastName;
    private List<ExamResult> examResults;

    public Student() {}

    public Student(ObjectId id, String userName, String password, String name, String lastName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        examResults = new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ExamResult> getExamResults() {
        return examResults;
    }

    public void setExamResults(List<ExamResult> examResults) {
        this.examResults = examResults;
    }
}
