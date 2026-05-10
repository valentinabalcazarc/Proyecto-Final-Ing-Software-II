package com.piedraazul.app_client.controllers;

import com.piedraazul.app_client.services.FestivosService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import com.piedraazul.app_client.models.Appointment;
import com.piedraazul.app_client.models.Professional;
import com.piedraazul.app_client.design_patterns.strategy.AppointmentSearchContext;
import com.piedraazul.app_client.design_patterns.strategy.SearchByProfAndDateStrategy;
import com.piedraazul.app_client.design_patterns.strategy.SearchParams;
import com.piedraazul.app_client.services.NavigationService;
import com.piedraazul.app_client.services.ServiceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ProfessionalViewAppointmentsController {

    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, Integer> colId;
    @FXML private TableColumn<Appointment, LocalDate> colDate;
    @FXML private TableColumn<Appointment, LocalTime> colTime;
    @FXML private TableColumn<Appointment, Integer> colPatientName;
    @FXML private TableColumn<Appointment, String> colProfessional;
    @FXML private TableColumn<Appointment, String> colType;
    @FXML private TableColumn<Appointment, String> colSpeciality;
    @FXML private TableColumn<Appointment, String> colStatus;
    @FXML private TableColumn<Appointment, String> colDescription;

    @FXML private ComboBox<Professional> cbxProfessional;
    @FXML private DatePicker dpDate;
    @FXML private Button btnFind;
    @FXML private Button btnClearFilter;
    @FXML private Button btnRegresar;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private final AppointmentSearchContext searchContext = new AppointmentSearchContext();
    private final FestivosService festivosService = new FestivosService();

    @FXML
    public void initialize() {
        setupTable();
        loadProfessionals();
        loadAllAppointments();
        configurarCalendario();

        // Listener del DatePicker: respeta filtros activos
        dpDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                applyFilter();
            } else {
                if (cbxProfessional.getValue() == null) {
                    loadAllAppointments();
                } else {
                    applyFilter();
                }
            }
        });
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colProfessional.setCellValueFactory(new PropertyValueFactory<>("professionalName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeProfName"));
        colSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialityName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblAppointments.setItems(appointmentList);
    }

    private void loadProfessionals() {
        try {
            List<Professional> list = ServiceManager.getInstance().getProfessionalService().getAllProfessionals();
            cbxProfessional.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllAppointments() {
        try {
            // ── Patrón Strategy: carga inicial — citas AGENDADAS reales, sin filtro ──
            // Pasa ambos parámetros null → SearchByProfAndDateStrategy llama a
            // searchAppointmentsTyped(null, null) → endpoint GET /appointments (todas)
            searchContext.setStrategy(new SearchByProfAndDateStrategy());
            List<Appointment> list = searchContext.executeSearch(
                    new SearchParams.Builder().build());
            appointmentList.setAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lógica central de filtrado reutilizada por el botón "Buscar"
     * y el listener del DatePicker.
     */
    private void applyFilter() {
        Professional selectedProf = cbxProfessional.getValue();
        LocalDate selectedDate = dpDate.getValue();
        Long profId = (selectedProf != null) ? selectedProf.getCodProf() : null;

        // Si no hay ningún criterio activo, recargar todas las citas agendadas
        if (profId == null && selectedDate == null) {
            loadAllAppointments();
            return;
        }

        try {
            // ── Patrón Strategy: citas AGENDADAS filtradas por profesional y/o fecha ──
            searchContext.setStrategy(new SearchByProfAndDateStrategy());
            SearchParams params = new SearchParams.Builder()
                    .professionalId(profId)
                    .date(selectedDate)
                    .build();
            List<Appointment> results = searchContext.executeSearch(params);
            appointmentList.setAll(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFind() {
        applyFilter();
    }

    @FXML
    public void handleClearFilter() {
        cbxProfessional.setValue(null);
        dpDate.setValue(null);
        loadAllAppointments();
    }

    @FXML
    public void handleRegresar() {
        NavigationService.getInstance().navigateTo(
                "/fxml/ProfessionalMainView.fxml",
                "Piedra Azul - Profesional",
                btnRegresar);
    }

    private void configurarCalendario() {
        dpDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (festivosService.esDiaInvalido(date)) {
                    setDisable(true);
                    if (festivosService.esFestivo(date)) {
                        setStyle("-fx-background-color: #ffcccc; -fx-text-fill: #cc0000;");
                        setTooltip(new Tooltip("Día Festivo"));
                    } else {
                        setStyle("-fx-background-color: #f0f0f0;");
                        setTooltip(new Tooltip("Fin de semana"));
                    }
                }
            }
        });
    }
}