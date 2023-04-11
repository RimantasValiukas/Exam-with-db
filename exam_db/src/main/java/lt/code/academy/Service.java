package lt.code.academy;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Updates.*;

import lt.code.academy.data.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.List;

public class Service {
    private final MongoCollection<Student> studentCollection;
    private final MongoCollection<Exam> examCollection;
    private final MongoClient client;
    private final MongoDatabase database;

    public Service() {
        CodecRegistry registry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), registry);
        MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(codecRegistry).build();
        client = MongoClients.create(settings);
        database = client.getDatabase("examsDB");

        studentCollection = database.getCollection("students", Student.class);
        examCollection = database.getCollection("exams", Exam.class);
    }

    public void updateExam(Exam exam) {
        examCollection.updateOne(eq("_id", exam.getId()), new Document("$set", exam));
    }

    public void updateStudentResults(ObjectId id, List<ExamResult> examResults) {
        studentCollection.updateOne(eq("_id", id), set("examResults", examResults));
    }

    public FindIterable<Student> getStudents() {
        return studentCollection.find();
    }

    public void deleteExam(String examName) {
        examCollection.deleteOne(eq("examName", examName));
    }

    public void updateEditedExam(String examName, List<Question> questions) {
        examCollection.updateOne(eq("examName", examName), set("questions", questions));
    }

    public Exam getExam(String examName) {
        return examCollection.find(eq("examName", examName)).first();
    }

    public FindIterable<Exam> getExams() {
        return examCollection.find();
    }

    public void createExam(Exam exam) {
        examCollection.insertOne(exam);
    }

    public Student studentLogin(String userName, String password) {
        return studentCollection.find(and(eq("userName", userName), eq("password", password))).first();
    }

    public boolean checkIfUserNameAvailable(String userName) {
        Student studentDb = studentCollection.find(eq("userName", userName)).first();
        if (studentDb == null) {
            return true;
        }
        return false;
    }

    public void creatNewUser(Student student) {
        studentCollection.insertOne(student);
    }
}
