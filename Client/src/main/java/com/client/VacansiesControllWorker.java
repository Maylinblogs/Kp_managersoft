package com.client;

import com.client.utility.ClientSocket;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.menegment.enums.Action;
import org.menegment.models.*;

import java.io.IOException;
import java.util.ArrayList;

public class VacansiesControllWorker {


    private ObservableList<Vacancies> vacanciesData = FXCollections.observableArrayList();

    private ObservableList<Claim> yes = FXCollections.observableArrayList();
    private ObservableList<Claim> no = FXCollections.observableArrayList();
    private ObservableList<Claim> oio = FXCollections.observableArrayList();


    @FXML
    private ListView<Claim> Clean;

    @FXML
    private TableColumn<Vacancies, String> departament_nameColum;

    @FXML
    private TableColumn<Vacancies, String> infoColum;

    @FXML
    private TextField nameVacancie;

    @FXML
    private TableColumn<Vacancies, String> name_vacancieColum;

    @FXML
    private ListView<Claim> noClean;

    @FXML
    private TableView<Vacancies> tableVacancie;

    @FXML
    private TableColumn<Vacancies, Integer> vacancie_idColum;

    @FXML
    private ListView<Claim> yesClean;

    @FXML
    private Label error;


    @FXML
    private void initializeTable() {
        vacancie_idColum.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getVacancies_id()));
        name_vacancieColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName_vacancies()));
        departament_nameColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName_vacancies()));
        infoColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInfo()));
    }

    @FXML
    void onCreate(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        User user = ClientSocket.getLocalUser();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.GET_PERSON_WORKER_RESUME);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                Worker worker = (Worker) ClientSocket.getInstance().getObjectInputStream().readObject();
                Vacancies vacancies = tableVacancie.getFocusModel().getFocusedItem();
                Claim claim = new Claim();
                claim.setId_worker(worker.getWorker_id());
                claim.setName(user.getLogin());
                claim.setStatus_claim(0);
                claim.setInfo(vacancies.getInfo());
                ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.CREATE_CLAIM);
                ClientSocket.getInstance().getObjectOutputStream().flush();
                ClientSocket.getInstance().getObjectOutputStream().writeObject(claim);
                ClientSocket.getInstance().getObjectOutputStream().flush();
                Action b = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
                switch (b) {
                    case OK: {
                        claim = (Claim) ClientSocket.getInstance().getObjectInputStream().readObject();
                        oio.add(claim);
                        Clean.setItems(oio);
                        break;
                    }
                    case NO: {
                        error.setStyle("-fx-text-fill: red;");
                        break;
                    }

                }
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }

    @FXML
    void onDelete(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        if (Clean.getFocusModel().getFocusedItem() != null) {
            Claim claim = Clean.getFocusModel().getFocusedItem();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.DELETE_CLAIM);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(claim);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            System.out.println(a);
            switch (a) {
                case OK: {
                    oio.remove(claim);
                    Clean.setItems(oio);
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
    void onPoisk(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        Vacancies vacancies = new Vacancies();
        if (nameVacancie.getText().isBlank()) {
            error.setStyle("-fx-text-fill: red;");
        } else {
            vacancies.setName_vacancies(nameVacancie.getText());
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.POISK_VACANCIES);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(vacancies);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    vacancies = (Vacancies) ClientSocket.getInstance().getObjectInputStream().readObject();
                    tableVacancie.requestFocus();
                    tableVacancie.getSelectionModel().select(vacancies.getVacancies_id());
                    tableVacancie.getFocusModel().focus(vacancies.getVacancies_id());
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
    void onAllVacancies(ActionEvent event) {
        try {
            error.setStyle("-fx-text-fill: aab7a424;");
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ALL_VACANCIES);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    vacanciesData = FXCollections.observableArrayList();
                    initializeTable();
                    ArrayList<Vacancies> vacancies = (ArrayList<Vacancies>) ClientSocket.getInstance().getObjectInputStream().readObject();
                    vacanciesData.addAll(vacancies);
                    tableVacancie.setItems(vacanciesData);
                    break;
                }
                case NO: {
                    error.setStyle("-fx-text-fill: red;");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) tableVacancie.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("WorkerContener.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("!");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onUpdate(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        User user = ClientSocket.getLocalUser();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.GET_PERSON_WORKER_RESUME);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        System.out.println(a);
        switch (a) {
            case OK: {
                Worker worker = (Worker) ClientSocket.getInstance().getObjectInputStream().readObject();
                System.out.println(worker);
                ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.UPDATE_CLAIM);
                ClientSocket.getInstance().getObjectOutputStream().flush();
                ClientSocket.getInstance().getObjectOutputStream().writeObject(worker);
                ClientSocket.getInstance().getObjectOutputStream().flush();
                Action b = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
                System.out.println(b);
                switch (b) {
                    case OK: {
                        ArrayList<Claim> OIO = (ArrayList<Claim>) ClientSocket.getInstance().getObjectInputStream().readObject();
                        System.out.println(OIO);
                        ArrayList<Claim> No = (ArrayList<Claim>) ClientSocket.getInstance().getObjectInputStream().readObject();
                        System.out.println(No);
                        ArrayList<Claim> Yes = (ArrayList<Claim>) ClientSocket.getInstance().getObjectInputStream().readObject();
                        oio = FXCollections.observableArrayList();
                        oio.addAll(OIO);
                        Clean.setItems(oio);
                        no = FXCollections.observableArrayList();
                        no.addAll(No);
                        noClean.setItems(no);
                        yes = FXCollections.observableArrayList();
                        yes.addAll(Yes);
                        yesClean.setItems(yes);
                        break;
                    }
                    case NO: {
                        error.setStyle("-fx-text-fill: red;");
                        break;
                    }

                }
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }
}
