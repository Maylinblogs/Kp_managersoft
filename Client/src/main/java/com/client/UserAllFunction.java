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
import org.menegment.models.Claim;
import org.menegment.models.Department;
import org.menegment.models.User;
import org.menegment.models.Vacancies;

import java.io.IOException;
import java.util.ArrayList;

public class UserAllFunction {

    private ObservableList<Vacancies> vacanciesData = FXCollections.observableArrayList();
    private ObservableList<Department> departmentsData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Department, String> adsressColum1;

    @FXML
    private TableColumn<Vacancies, Integer> departament_idColum;

    @FXML
    private TableColumn<Department, String> departament_nameColum1;

    @FXML
    private TableColumn<Department, Integer> department_idColumS;

    @FXML
    private TableColumn<Department, Integer> id_userColum1;

    @FXML
    private TableColumn<Vacancies, Double> priceColum;
    @FXML
    private TextField info;

    @FXML
    private TextField name;

    @FXML
    private TableColumn<Vacancies, String> infoColum;

    @FXML
    private TableColumn<Vacancies, String> name_vacancieColum;

    @FXML
    private TableView<Department> tableDepartment1;

    @FXML
    private TableView<Vacancies> tableVacancie;

    @FXML
    private TableColumn<Vacancies, Integer> vacancie_idColum;

    @FXML
    private Label error;


    @FXML
    private void initializeTableV() {
        vacancie_idColum.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getVacancies_id()));
        name_vacancieColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName_vacancies()));
        departament_idColum.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDepartment_id()));
        infoColum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInfo()));
        priceColum.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrice()));
    }

    @FXML
    private void initializeTableD() {
        department_idColumS.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDepartment_id()));
        departament_nameColum1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName_department()));
        adsressColum1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));
        //  id_userColum1.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId_user()));
    }

    private void allV() {
        try {
            //error.setStyle("-fx-text-fill: aab7a424;");
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ALL_VACANCIES);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    vacanciesData = FXCollections.observableArrayList();
                    initializeTableV();
                    ArrayList<Vacancies> vacancies = (ArrayList<Vacancies>) ClientSocket.getInstance().getObjectInputStream().readObject();
                    vacanciesData.addAll(vacancies);
                    tableVacancie.setItems(vacanciesData);
                    break;
                }
                case NO: {
               //     error.setStyle("-fx-text-fill: red;");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void allD() {
        try {
           // error.setStyle("-fx-text-fill: aab7a424;");
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ALL_DEPARTMENT);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    departmentsData = FXCollections.observableArrayList();
                    initializeTableD();
                    ArrayList<Department> departments = (ArrayList<Department>) ClientSocket.getInstance().getObjectInputStream().readObject();
                    departmentsData.addAll(departments);
                    tableDepartment1.setItems(departmentsData);
                    break;
                }
                case NO: {
                 ///   error.setStyle("-fx-text-fill: red;");
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
    void onALLDEpartment(ActionEvent event) {
        allD();
    }

    @FXML
    void onALLVacancie(ActionEvent event) {
        allV();
    }

    @FXML
    void onAddDepartment(ActionEvent event) throws IOException, ClassNotFoundException {
       // error.setStyle("-fx-text-fill: aab7a424;");
        Department department = new Department();
        if (name.getText().isBlank() || info.getText().isBlank()) {
            error.setStyle("-fx-text-fill: red;");
        } else {
            department.setName_department(name.getText());
            department.setId_user(ClientSocket.getLocalUser().getUser_id());
            department.setAddress(info.getText());
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ADD_DEPARTMENT);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(department);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    department = (Department) ClientSocket.getInstance().getObjectInputStream().readObject();
                    allD();
                    break;
                }
                case NO: {
                  //  error.setStyle("-fx-text-fill: red;");
                    break;
                }
            }
        }
    }

    @FXML
    void onAddVacancie(ActionEvent event) throws IOException, ClassNotFoundException {
        //error.setStyle("-fx-text-fill: aab7a424;");
        Vacancies vacancie = new Vacancies();
        if (name.getText().isBlank() || info.getText().isBlank() || price.getText().isBlank()) {
         //   error.setStyle("-fx-text-fill: red;");
        } else {
            vacancie.setPrice(Double.valueOf(price.getText()));
            vacancie.setName_vacancies(name.getText());
            vacancie.setInfo(info.getText());
            Department department = tableDepartment1.getFocusModel().getFocusedItem();
            if (department.getDepartment_id() >= 0) {
                vacancie.setDepartment_id(department.getDepartment_id());
                ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.ALL_VACANCIES_USER);
                ClientSocket.getInstance().getObjectOutputStream().flush();
                ClientSocket.getInstance().getObjectOutputStream().writeObject(vacancie);
                ClientSocket.getInstance().getObjectOutputStream().flush();
                Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
                switch (a) {
                    case OK: {
                        vacancie = (Vacancies) ClientSocket.getInstance().getObjectInputStream().readObject();
                        allV();
                        break;
                    }
                    case NO: {
                     //   error.setStyle("-fx-text-fill: red;");
                        break;
                    }
                }
            } else {
                //error.setStyle("-fx-text-fill: red;");
            }

        }
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) tableVacancie.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(UserClaim.class.getResource("UserContener.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onDeleteVacancie(ActionEvent event) throws IOException, ClassNotFoundException {
       // error.setStyle("-fx-text-fill: aab7a424;");
        if (tableVacancie.getFocusModel().getFocusedItem() != null) {
            Vacancies vacancies = tableVacancie.getFocusModel().getFocusedItem();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.DELETE_VACANCIES);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(vacancies);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    vacanciesData.remove(vacancies);
                    tableVacancie.setItems(vacanciesData);
                    break;
                }
                case NO: {
                 //   error.setStyle("-fx-text-fill: red;");
                    break;
                }
            }
        }
    }


    @FXML
    private TextField c1;

    @FXML
    private TextField c2;

    @FXML
    private TextField c3;

    @FXML
    private TextField price;


    @FXML
    void on1Formul(ActionEvent event) throws IOException, ClassNotFoundException {
        ArrayList<Double> doubles = new ArrayList<>();
        if (c1.getText().isBlank() || c2.getText().isBlank() || c3.getText().isBlank()) {

        } else {
            doubles.add(1.0);
            doubles.add(Double.valueOf(c1.getText()));
            doubles.add(Double.valueOf(c2.getText()));
            doubles.add(Double.valueOf(c3.getText()));
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.FORMULA_1);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(doubles);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    Double res = (Double) ClientSocket.getInstance().getObjectInputStream().readObject();
                    price.setText(String.valueOf(res));
                    break;
                }
                case NO: {
                    break;
                }
            }
        }

    }

    @FXML
    void on2Formula(ActionEvent event) throws IOException, ClassNotFoundException {
        ArrayList<Double> doubles = new ArrayList<>();
        if (c1.getText().isBlank() || c2.getText().isBlank() || c3.getText().isBlank()) {

        } else {
            doubles.add(2.0);
            doubles.add(Double.valueOf(c1.getText()));
            doubles.add(Double.valueOf(c2.getText()));
            doubles.add(Double.valueOf(c3.getText()));
            ClientSocket.getInstance().getObjectOutputStream().writeObject(Action.FORMULA_1);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            ClientSocket.getInstance().getObjectOutputStream().writeObject(doubles);
            ClientSocket.getInstance().getObjectOutputStream().flush();
            Action a = (Action) ClientSocket.getInstance().getObjectInputStream().readObject();
            switch (a) {
                case OK: {
                    Double res = (Double) ClientSocket.getInstance().getObjectInputStream().readObject();
                    price.setText(String.valueOf(res));
                    break;
                }
                case NO: {
                    break;
                }
            }
        }
    }
}
