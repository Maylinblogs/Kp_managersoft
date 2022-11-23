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
import org.menegment.models.Claim;
import org.menegment.models.User;
import org.menegment.models.Vacancies;
import org.menegment.models.Worker;

import java.io.IOException;
import java.util.ArrayList;

public class UserClaim {

    private ObservableList<Claim> claimData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Claim, Integer> claim_idColum;

    @FXML
    private TableColumn<Claim, String> infoColum;

    @FXML
    private TextField nameClaim;

    @FXML
    private TableColumn<Claim, String> name_claimColum;

    @FXML
    private TextArea resume;

    @FXML
    private TableView<Claim> tableVacancie;

    @FXML
    private TextArea worker;

    @FXML
    private TableColumn<Claim, Integer> worker_id_Colum;

    @FXML
    private Label error;


    @FXML
    private void initializeTable() {
        claim_idColum.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getClaim_id()));
        name_claimColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        worker_id_Colum.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatus_claim()));
        infoColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInfo()));
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) worker.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(UserClaim.class.getResource("UserContener.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("");
        stage.setResizable(false);
        stage.show();
    }


    private void all() throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ALL_CLIAM);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                claimData = FXCollections.observableArrayList();
                initializeTable();
                ArrayList<Claim> claims = (ArrayList<Claim>) ClientSocket.getInstance().getObjectInputStream().readObject();
                claimData.addAll(claims);
                tableVacancie.setItems(claimData);
                break;
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }

    @FXML
    void onALLClaim(ActionEvent event) throws IOException, ClassNotFoundException {
        all();
    }


    @FXML
    void onNoClaim(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        Claim claim = tableVacancie.getFocusModel().getFocusedItem();
        claim.setStatus_claim(1);
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.NO_CLAIM);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(claim);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                all();
                break;
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }


    @FXML
    void onPoisk(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        Claim claim = new Claim();
        if (nameClaim.getText().isBlank()) {
            error.setStyle("-fx-text-fill: red;");
        } else {
            claim.setName(nameClaim.getText());
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.POISK_CLIAM);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(claim);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    claim = (Claim) ClientSocket.getInstance().getObjectInputStream().readObject();
                    tableVacancie.requestFocus();
                    tableVacancie.getSelectionModel().select(claim.getClaim_id());
                    tableVacancie.getFocusModel().focus(claim.getClaim_id());
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
    void onYesClaim(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        Claim claim = tableVacancie.getFocusModel().getFocusedItem();
        claim.setStatus_claim(2);
        System.out.println(claim);
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.NO_CLAIM);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(claim);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        System.out.println(a);
        switch (a) {
            case OK: {
                all();
                break;
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }


    @FXML
    void onChoice(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        Claim claim = tableVacancie.getFocusModel().getFocusedItem();
        System.out.println(claim);
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.GET_CLAIM_WORKER);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        ClientSocket.getInstance().getObjectOutputStream().writeObject(claim);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                Worker worker1 = (Worker) ClientSocket.getInstance().getObjectInputStream().readObject();
                System.out.println(worker1);
                worker.setText(worker1.toString());
                resume.setText(worker1.getResume().toString());
                break;
            }
            case NO: {
                error.setStyle("-fx-text-fill: red;");
                break;
            }
        }
    }
}
