package lt.code.academy.data;

import java.util.List;
import java.util.Objects;

public class Question {
    private String question;
    private String correctAnswer;
    private List<Answer> possibleAnswers;

    public Question() {}

    public Question(String question, String correctAnswer, List<Answer> possibleAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = possibleAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<Answer> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<Answer> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(question, question1.question) && Objects.equals(correctAnswer, question1.correctAnswer) && Objects.equals(possibleAnswers, question1.possibleAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, correctAnswer, possibleAnswers);
    }
}
