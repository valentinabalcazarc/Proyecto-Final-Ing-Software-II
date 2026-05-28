package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ProfessionalService;
import com.piedraazul.app_client.services.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML private Button btnEdit;
    @FXML private Label  lblHint;

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

        // Habilitar btnEdit solo cuando hay una fila seleccionada
        tableSchedules.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    boolean selected = newVal != null;
                    btnEdit.setDisable(!selected);
                    lblHint.setVisible(!selected);
                }
        );
    }

    private void loadProfessionals() {
        List<Professional> professionals = professionalService.getAllProfessionals();
        ObservableList<Professional> data = FXCollections.observableArrayList(professionals);
        tableSchedules.setItems(data);
    }

    // ----------------------------------------------------------------
    // Abrir diálogo de edición
    // ----------------------------------------------------------------

    @FXML
    public void handleEdit() {
        Professional selected = tableSchedules.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/EditprofessionalScheduleDialog.fxml")
            );
            Parent root = loader.load();

            EditProfessionalScheduleController dialogController = loader.getController();
            dialogController.setProfessional(selected);

            Stage dialog = new Stage();
            dialog.setTitle("Editar horario — " + selected.getFullName());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(btnEdit.getScene().getWindow());
            dialog.setResizable(false);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/fxml/stylesheet.css").toExternalForm()
            );
            dialog.setScene(scene);
            dialog.showAndWait();

            // Recargar tabla al cerrar el diálogo para reflejar los cambios
            loadProfessionals();
            tableSchedules.getSelectionModel().clearSelection();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo abrir el panel de edición.");
            alert.showAndWait();
        }
    }

    // ----------------------------------------------------------------
    // Volver al panel admin
    // ----------------------------------------------------------------

    @FXML
    public void handleBack() {
        NavigationService.getInstance().navigateTo(
                "/fxml/AdminView.fxml",
                "Piedra Azul - Administrador",
                btnBack
        );
    }
}