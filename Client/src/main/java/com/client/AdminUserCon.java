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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.menegment.enums.Action;
import org.menegment.enums.Roles;
import org.menegment.models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AdminUserCon {

    private int i = 0;

    private ObservableList<User> usersData = FXCollections.observableArrayList();
    @FXML
    private Label error;

    @FXML
    private TextField login;

    @FXML
    private TableColumn<User, String> loginColum;

    @FXML
    private TextField password;

    @FXML
    private TableColumn<User, String> passwordColum;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> user_idColum;

    @FXML
    private TextField user_role;

    @FXML
    private TableColumn<User, Roles> user_roleColum;


    private void setUserData(ArrayList<User> users) {
        usersData.addAll(users);
        userTable.setItems(usersData);
    }


    @FXML
    private void initializeUserTable() {
        user_idColum.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUser_id()));
        loginColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLogin()));
        passwordColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPassword()));
        user_roleColum.setCellValueFactory(data -> new SimpleObjectProperty<Roles>(data.getValue().getRole_user()));
    }

    @FXML
    void OnPoisk(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        User user = new User();
        if (login.getText().isBlank()) {
            error.setStyle("-fx-text-fill: red;");
        } else {
            user.setLogin(login.getText());
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.POISK_USER);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    System.out.println("sdasd");
                    user = (User) ClientSocket.getInstance().getObjectInputStream().readObject();
                    userTable.requestFocus();
                    userTable.getSelectionModel().select(user.getUser_id());
                    userTable.getFocusModel().focus(user.getUser_id());
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
    void onAddUser(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        User user = new User();
        if (login.getText().isBlank() || password.getText().isBlank() || user_role.getText().isBlank()) {
            error.setStyle("-fx-text-fill: red;");
        } else {
            user.setLogin(login.getText());
            user.setPassword(password.getText());
            user.setRole_user(Roles.USER);
            if (user_role.getText().equals("ADMIN")) {
                user.setRole_user(Roles.ADMIN);
            } else {
                if (user_role.getText().equals("WORKER")) {
                    user.setRole_user(Roles.WORKER);
                }
            }
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ADD_USER);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    user = (User) ClientSocket.getInstance().getObjectInputStream().readObject();
                    usersData.add(user);
                    userTable.setItems(usersData);
                    break;
                }
                case NO: {
                    error.setStyle("-fx-text-fill: red;");
                    break;
                }
            }
        }
    }

    public void all()
    {
        try {
            error.setStyle("-fx-text-fill: aab7a424;");
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ALL_USER);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    usersData = FXCollections.observableArrayList();
                    initializeUserTable();
                    ArrayList<User> users = (ArrayList<User>) ClientSocket.getInstance().getObjectInputStream().readObject();
                    setUserData(users);
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
    void onAllUser(ActionEvent event) {
       all();
    }

    @FXML
    void onCreateOrder(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ORDER_USER);
        ClientSocket.getInstance().getObjectOutputStream().flush();
        Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
        switch (a) {
            case OK: {
                ArrayList<User> users = (ArrayList<User>) ClientSocket.getInstance().getObjectInputStream().readObject();
                File file = new File("D:/" + "Order" + i + ".txt");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(users.toString());
                fileWriter.flush();
                i++;
                break;
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
        if (userTable.getFocusModel().getFocusedItem() != null) {
            User user = userTable.getFocusModel().getFocusedItem();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.DELETE_USER);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    usersData.remove(user);
                    userTable.setItems(usersData);
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
    void onBack(ActionEvent event) throws IOException {
        error.setStyle("-fx-text-fill: aab7a424;");
        Stage stage = (Stage) login.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("AdminContener.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onRedakt(ActionEvent event) throws IOException, ClassNotFoundException {
        error.setStyle("-fx-text-fill: aab7a424;");
        User user = userTable.getFocusModel().getFocusedItem();
        System.out.println(user);


        if (login.getText().isEmpty() || password.getText().isEmpty() || user_role.getText().isEmpty()) {
            error.setStyle("-fx-text-fill: red;");
        } else {
            user.setLogin(login.getText());
            user.setPassword(password.getText());
            user.setRole_user(Roles.USER);
            if (user_role.getText().equals("ADMIN")) {
                user.setRole_user(Roles.ADMIN);
            } else {
                if (user_role.getText().equals("WORKER")) {
                    user.setRole_user(Roles.WORKER);
                }
            }
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.REDAKT_USER);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(user);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            System.out.println(a);
            switch (a) {
                case OK: {
                    user = (User) ClientSocket.getInstance().getObjectInputStream().readObject();
                    usersData = FXCollections.observableArrayList();
                    all();
                    break;
                }
                case NO: {
                    error.setStyle("-fx-text-fill: red;");
                    break;
                }
            }
        }
    }
}

