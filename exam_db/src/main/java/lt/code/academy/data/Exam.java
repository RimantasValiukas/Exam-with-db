package lt.code.academy.data;

import org.bson.types.ObjectId;

import java.util.List;

public class Exam {
    private ObjectId id;
    private String examName;
    private List<Question> questions;
    private int count;

    public Exam() {}

    public Exam(ObjectId id, String examName, List<Question> questions) {
        this.id = id;
        this.examName = examName;
        this.questions = questions;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
