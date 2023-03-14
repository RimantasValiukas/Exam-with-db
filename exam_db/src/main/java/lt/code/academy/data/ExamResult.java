package lt.code.academy.data;

import java.time.LocalDateTime;

public class ExamResult {
    private String examName;
    private double grade;
    private LocalDateTime dateTime;

    public ExamResult() {}

    public ExamResult(String examName, double grade, LocalDateTime dateTime) {
        this.examName = examName;
        this.grade = grade;
        this.dateTime = dateTime;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return String.format("Egzamino pavadinimas: %s, įvertinimas: %s, laikymo data: %s%n", examName, grade, dateTime);
    }
}
