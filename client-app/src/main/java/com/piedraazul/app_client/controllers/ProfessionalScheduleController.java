package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ProfessionalService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProfessionalScheduleController {

    @FXML private TableView<Professional> tableSchedules;
    @FXML private TableColumn<Professional, String>  colName;
    @FXML private TableColumn<Professional, String>  colSpeciality;
    @FXML private TableColumn<Professional, String>  colType;
    @FXML private TableColumn<Professional, String>  colArrival;
    @FXML private TableColumn<Professional, String>  colDeparture;
    @FXML private TableColumn<Professional, Integer> colInterval;
    @FXML private Button btnBack;

    private final ProfessionalService professionalService =
            ServiceManager.getInstance().getProfessionalService();

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialityProf"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeProf"));
        colArrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        colDeparture.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        colInterval.setCellValueFactory(new PropertyValueFactory<>("attentionInterval"));

        loadProfessionals();
    }

    private void loadProfessionals() {
        List<Professional> professionals = professionalService.getAllProfessionals();
        ObservableList<Professional> data = FXCollections.observableArrayList(professionals);
        tableSchedules.setItems(data);
    }

    @FXML
    public void handleBack() {
        NavigationService.getInstance().navigateTo(
                "/fxml/AdminView.fxml",
                "Piedra Azul - Administrador",
                btnBack
        );
    }
}