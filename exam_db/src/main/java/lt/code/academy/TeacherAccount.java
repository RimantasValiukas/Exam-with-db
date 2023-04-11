package lt.code.academy;

import com.mongodb.client.FindIterable;
import lt.code.academy.data.*;

import java.util.*;

public class TeacherAccount {
    protected final Service service;
    Scanner sc;

    public TeacherAccount(Service service) {
        this.service = service;
        sc = new Scanner(System.in);
    }

    private void printStatistic() {
        FindIterable<Exam> exams = service.getExams();
        for (Exam exam: exams) {
            if (exam.getCount() == 0) {
                continue;
            }
            System.out.printf("Egzaminas \"%s\" buvo spręstas %s kartus(ų).%n",exam.getExamName(), exam.getCount());
            List<Question> questions = exam.getQuestions();
            printQuestionsStatistic(questions);
            printAnswersStatistic((questions));
            System.out.println("----------------------------------------------");
        }
    }

    private void printAnswersStatistic(List<Question> questions) {
        Map<Integer, Integer> countAnswers = new HashMap<>();
        for (Question question: questions) {
            List<Answer> answers = question.getPossibleAnswers();

            for (int i = 0; i < answers.size(); i++) {
                int answerNumber = i + 1;
                int answerCount = answers.get(i).getCount();

                if (countAnswers.get(answerNumber) == null) {
                    countAnswers.put(answerNumber, answerCount);
                    continue;
                }

                countAnswers.put(answerNumber, countAnswers.get(answerNumber) + answerCount);
            }
        }
        countAnswers.forEach((k, v) -> System.out.printf("Atsakymo variantas \"%s\" buvo pasirinktas %s kartus(ų)%n", k, v));
    }

    private void printQuestionsStatistic(List<Question> questions) {
        int correctAnswersNum = 0;
        for (Question question: questions) {
            List<Answer> answers = question.getPossibleAnswers();
            for (Answer answer: answers) {
                if (answer.getAnswer().equals(question.getCorrectAnswer())) {
                    System.out.printf("Klausime \"%s\", teisingas atsakymas \"%s\", kuris buvo pasirinktas %s kartus(ų).%n",
                            question.getQuestion(), question.getCorrectAnswer(), answer.getCount());
                    correctAnswersNum += answer.getCount();
                }
            }
        }
        System.out.printf("Iš viso teisingi atsakymai buvo pasirinkti %s kartus(ų).%n", correctAnswersNum);
    }

    private void printAllExamsResults() {
        FindIterable<Student> students = service.getStudents();

        for (Student student: students) {
            List<ExamResult> examResults = student.getExamResults();
            if (examResults == null) {
                continue;
            }

            for (ExamResult examResult: examResults) {
                System.out.printf("Studento %s %s laikyto \"%s\" egzamino rezultatas yra %s%n",
                        student.getName(), student.getLastName(), examResult.getExamName(), examResult.getGrade());
            }
        }
    }

    private void deleteExam() {
        printExams();
        System.out.println("Įveskite egzamino pavadinimą, kurį norite ištrinti:");
        String examName = sc.nextLine();
        if (service.getExam(examName) == null) {
            System.out.printf("Egzamino pavadinimu \"%s\" duomenų bazėje nėra.%n", examName);
            return;
        }
        System.out.printf("Ar tikrai norite ištrinti egzaminą \"%s\"? Įveskite \"taip\" arba \"ne\"%n", examName);
        if (!sc.nextLine().equals("taip")) {
            System.out.println("Egzaminas neištrintas.");
            return;
        }
        service.deleteExam(examName);
        System.out.printf("Egzaminas pavadinimu \"%s\" sėkmingai ištrintas%n", examName);
    }

    private void editExam() {
        printExams();
        System.out.println("Įveskite egzamino pavadinimą, kurį norite koreguoti:");
        String examName = sc.nextLine();
        Exam exam = service.getExam(examName);

        if (exam == null) {
            System.out.printf("Egzaminas pavadinimu \"%s\" nerastas%n", examName);
            return;
        }

        List<Question> questions = exam.getQuestions();
        for (Question question: questions) {
            System.out.printf("Klausimas: %s; Įveskite pakoreguotą klausimą:%n", question.getQuestion());
            question.setQuestion(sc.nextLine());
            System.out.printf("Teisingas atsakymas: %s; Įveskite pakoreguotą atsakymą:%n", question.getCorrectAnswer());
            question.setCorrectAnswer(sc.nextLine());

            for (Answer answer: question.getPossibleAnswers()) {
                System.out.printf("Atsakymo variantas: %s; Įveskite pakoreguotą atsakymą:%n", answer.getAnswer());
                answer.setAnswer(sc.nextLine());
            }
        }

        service.updateEditedExam(examName, questions);
    }

    protected void printExams() {
        FindIterable<Exam> exams = service.getExams();
        System.out.println("Šiuo metu aktyvūs egzaminai:");
        for (Exam exam: exams) {
            System.out.println("Egzamino pavadinimas: " + exam.getExamName());
        }
    }

    private void createExam() {
        System.out.println("Įveskite egzamino pavadinimą:");
        String examName = sc.nextLine();
        int questionNumber = getCorrectNumber("Kiek klausimų sudarys egzaminą?");
        int answersNumber = getCorrectNumber("Kiek galimų atsakymų turės klausimas?");
        List<Question> questions = new LinkedList<>();

        for (int i = 0; i < questionNumber; i++) {
            List<Answer> answers = new LinkedList<>();
            System.out.println("Įveskite klausimą:");
            String enteredQuestion = sc.nextLine();
            System.out.println("Įveskite teisingą atsakymą:");
            String correctAnswer = sc.nextLine();

            for (int j = 0; j < answersNumber; j++) {
                System.out.println("Įveskite galimą atsakymą:");
                Answer answer = new Answer(sc.nextLine());
                answers.add(answer);
            }

            Question question = new Question(enteredQuestion, correctAnswer, answers);
            questions.add(question);
        }

        Exam exam = new Exam(null, examName, questions);
        service.createExam(exam);
    }

    protected int getCorrectNumber(String text) {
        while (true) {
            try {
                System.out.println(text);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Neteisingas formatas");
            }
        }
    }

    private void userAction(String action) {
        switch (action) {
            case "1" -> createExam();
            case "2" -> editExam();
            case "3" -> deleteExam();
            case "4" -> printAllExamsResults();
            case "5" -> printStatistic();
            case "6" -> System.out.println("Programa baigė darbą");
            default -> System.out.println("Tokios funkcijos nėra");
        }
    }

    public void teacherMenu() {
        String action;
        do {
            System.out.println("""
                1 - sukurti egzaminą
                2 - koreguoti egzaminą
                3 - trinti egzaminą
                4 - žiūrėti egzaminų rezultatus
                5 - žiūrėti statistiką
                6 - išeiti
                """);
            action = sc.nextLine();
            userAction(action);
        } while (!action.equals("6"));
    }
}
