package lt.code.academy;

import lt.code.academy.data.*;

import java.time.LocalDateTime;
import java.util.List;

public class StudentAccount extends TeacherAccount {

    Student student;
    Exam exam;

    public StudentAccount(Service service) {
        super(service);
    }

    private void printExamsResults() {
        List<ExamResult> examResults = student.getExamResults();
        if (examResults.isEmpty()) {
            System.out.println("Jūs nelaikėte jokio egzamino.");
            return;
        }
        System.out.printf("Studento %s %s laikytų egzaminų rezultatai:%n", student.getName(), student.getLastName());
        examResults.forEach(System.out::println);
    }

    private void chooseExam() {
        printExams();
        System.out.println("Įveskite egzamino pavadinimą:");
        String examName = sc.nextLine();
        exam = service.getExam(examName);
        if (exam == null) {
            System.out.printf("Egzamino pavadinimu \"%s\" nėra%n", examName);
            return;
        }
        if (!checkIfPossibleTakeExam()) {
            System.out.println("Egzamino perlaikyti dar negalite!");
            return;
        }
            takeExam();
    }

    private boolean checkIfPossibleTakeExam() {
        List<ExamResult> examResults = student.getExamResults();
        for (ExamResult examResult: examResults) {
            LocalDateTime possibleTime = examResult.getDateTime().plusHours(48);
            String examName = examResult.getExamName();
            if (examName.equals(exam.getExamName()) && LocalDateTime.now().isAfter(possibleTime)) {
                return true;
            }
        }
        return false;
    }


    private void takeExam() {
        List<Question> questions = exam.getQuestions();
        int correctAnswersNum = 0;
        System.out.printf("Studentas %s %s laiko egzaminą %s%n", student.getName(), student.getLastName(), exam.getExamName());

        for (Question question : questions) {
            List<Answer> possibleAnswers = question.getPossibleAnswers();
            int answerNum = 0;
            System.out.println(question.getQuestion());

            for (Answer answer : possibleAnswers) {
                answerNum++;
                System.out.printf("%s. %s%n", answerNum, answer.getAnswer());
            }

            int answer = getCorrectNumber("Įveskite atsakymo numerį:");
            correctAnswersNum += getPoint(question, answer);
        }
        exam.setCount(exam.getCount() + 1);
        service.updateExam(exam);
        gradeExam(correctAnswersNum, questions.size());
    }

    private void gradeExam(int points, int questionsNum) {
        double questionValue = 10 / questionsNum;
        double studentGrade = questionValue * points;
        ExamResult examResult = new ExamResult(exam.getExamName(), studentGrade, LocalDateTime.now());

        List<ExamResult> examResults = student.getExamResults();
        examResults.add(examResult);

        student.setExamResults(examResults);
        service.updateStudentResults(student.getId(), examResults);
        System.out.println("Jūsų egzamino įvertinimas yra " + studentGrade);
    }

    private int getPoint(Question question, int answer) {
        List<Answer> possibleAnswers = question.getPossibleAnswers();
        String studentAnswer = "";

        for (int i = 0; i < possibleAnswers.size(); i++) {
            if (i + 1 == answer) {
                studentAnswer = possibleAnswers.get(i).getAnswer();
                setCount(question, studentAnswer);
            }
        }

        if (question.getCorrectAnswer().equals(studentAnswer)) {
            return 1;
        }
        return 0;
    }

    private void setCount(Question question, String answer) {
        List<Question> questions = exam.getQuestions();
        for (Question q : questions) {
            if (q.equals(question)) {
                List<Answer> answers = q.getPossibleAnswers();
                for (Answer a : answers) {
                    if (a.getAnswer().equals(answer)) {
                        a.setCount(a.getCount() + 1);
                    }
                }
            }
        }
    }

    private void action(String action) {
        switch (action) {
            case "1" -> chooseExam();
            case "2" -> printExamsResults();
            case "3" -> System.out.println("Programa baigė darbą.");
            default -> System.out.println("Tokios funkcijos nėra");
        }
    }

    public void studentMenu() {
        String action;
        do {
            System.out.println("""
                    1 - laikyti egzaminą
                    2 - žiūrėti egzaminų rezultatus
                    3 - išeiti
                    """);
            action = sc.nextLine();
            action(action);
        } while (!action.equals("3"));
    }
}
