package com.client;

import com.client.utility.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.menegment.enums.Action;
import org.menegment.models.Resume;
import org.menegment.models.User;
import org.menegment.models.Worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WorkerAccount {

    private int i = 0;

    @FXML
    private TextArea education;

    @FXML
    private TextArea experience;

    @FXML
    private TextField lastname;

    @FXML
    private TextField login;

    @FXML
    private TextField name;

    @FXML
    private TextField passport;

    @FXML
    private PasswordField newpassword;

    @FXML
    private PasswordField password;

    @FXML
    private TextField specialization;

    @FXML
    private TextField surname;

    @FXML
    private Label error;

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) login.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(WorkerContener.class.getResource("WorkerContener.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Р П!");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onCreateResume(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        User user = ClientSocket.getLocalUser();
        login.setText(user.getLogin());
        password.setText(user.getPassword());
        newpassword.setText(user.getPassword());
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.GET_PERSON_WORKER_RESUME);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                System.out.println("sdwda");
                Worker worker = (Worker) ClientSocket.getInstance().getObjectInputStream().readObject();

                if (worker == null) {
                    error.setStyle("-fx-text-fill: red;");
                    break;
                } else {
                    name.setText(worker.getName_worker());
                    surname.setText(worker.getSur_name());
                    lastname.setText(worker.getLast_name());
                    passport.setText(worker.getPassport());
                    specialization.setText(worker.getResume().getSpecialization());
                    education.setText(worker.getResume().getEducation());
                    experience.setText(worker.getResume().getExperience());
                    File file = new File("D:/" + "Resume" + i + " " + surname.getText() + ".doc");
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(worker.toString());
                    fileWriter.flush();
                    i++;
                }
                break;
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }


    @FXML
    void onOK(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        Worker worker = new Worker();
        if (name.getText().isEmpty() || surname.getText().isEmpty() || lastname.getText().isEmpty()
                || passport.getText().isEmpty()) {
            error.setStyle("-fx-text-fill: red;");
        } else {
            User user = ClientSocket.getLocalUser();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.LOGIN);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            switch ((Action) ClientSocket.getInstance().getObjectInputStream().readObject()) {
                case OK: {
                    user = (User) ClientSocket.getInstance().getObjectInputStream().readObject();
                    worker.setName_worker(name.getText());
                    worker.setSur_name(surname.getText());
                    worker.setPassport(passport.getText());
                    worker.setLast_name(lastname.getText());
                    worker.setUser_id(user.getUser_id());
                    Resume resume = new Resume();
                    resume.setEducation(education.getText());
                    resume.setExperience(experience.getText());
                    resume.setSpecialization(specialization.getText());
                    worker.setResume(resume);
                    ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.CREATE_WORKER_RESUME);
                    ClientSocket.getInstance().getObjectOutputStream().flush();
                    System.out.println("worker");
                    ClientSocket.getInstance().getObjectOutputStream().writeObject(worker);
                    ClientSocket.getInstance().getObjectOutputStream().flush();
                    switch ((Action) ClientSocket.getInstance().getObjectInputStream().readObject()) {
                        case OK: {
                            //успех
                            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEE");
                        }
                        case NO: {
                            error.setStyle("-fx-text-fill: red;");
                            break;
                        }
                    }
                    break;
                }
                case NO: {
                    error.setStyle("-fx-text-fill: red;");
                    break;
                }
            }

        }
    }


    @FXML
    void onRedarteee(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        System.out.println("ejndqdno");

        User user1 = ClientSocket.getLocalUser();
        if (login.getText().isEmpty() || password.getText().isEmpty() || !password.getText().equals(newpassword.getText())) {
            error.setStyle("-fx-text-fill: red;");
        } else {
            User user = new User();
            user.setLogin(login.getText());
            user.setPassword(password.getText());
            System.out.println(user);
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.REDAKT_USER_WORKER);
            ClientSocket.getInstance().getObjectOutputStream().flush();

            ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(user1);
            ClientSocket.getInstance().getObjectOutputStream().flush();

            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            System.out.println(a);
            switch (a) {
                case OK: {
                    user = (User) ClientSocket.getInstance().getObjectInputStream().readObject();
                    System.out.println(user);
                    login.setText(user.getLogin());
                    password.setText(user.getPassword());
                    newpassword.setText(user.getPassword());
                    ClientSocket.setLocalUser(user);
                    break;
                }
                case NO: {
                    error.setStyle("-fx-text-fill: red;");
                    break;
                }
            }
        }
    }


    @FXML
    void onUpdate(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        User user = ClientSocket.getLocalUser();
        login.setText(user.getLogin());
        password.setText(user.getPassword());
        newpassword.setText(user.getPassword());
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.GET_PERSON_WORKER_RESUME);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                Worker worker = (Worker) ClientSocket.getInstance().getObjectInputStream().readObject();
                if (worker.getWorker_id() == null) {
                    error.setStyle("-fx-text-fill: red;");
                } else {
                    name.setText(worker.getName_worker());
                    surname.setText(worker.getSur_name());
                    lastname.setText(worker.getLast_name());
                    passport.setText(worker.getPassport());
                    specialization.setText(worker.getResume().getSpecialization());
                    education.setText(worker.getResume().getEducation());
                    experience.setText(worker.getResume().getExperience());
                }
                break;
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }
}
