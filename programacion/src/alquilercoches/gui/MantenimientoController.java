package alquilercoches.gui;

import alquilercoches.Util;
import alquilercoches.personal.Administrador;
import alquilercoches.vehiculos.Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MantenimientoController {

    private final Administrador admin;
    private final VehiculosController vehiculosController;
    private TableView<Vehiculo> tablaMantenimiento;
    private BorderPane view;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public MantenimientoController(Administrador admin, VehiculosController vehiculosController) {
        this.admin = admin;
        this.vehiculosController = vehiculosController;
        crearVista();
    }

    private void crearVista() {
        view = new BorderPane();
        tablaMantenimiento = new TableView<>();

        TableColumn<Vehiculo, String> matriculaCol = new TableColumn<>("Matrícula");
        matriculaCol.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        TableColumn<Vehiculo, String> tipoCol = new TableColumn<>("Tipo");
        tipoCol.setCellValueFactory(cellData -> {
            Vehiculo vehiculo = cellData.getValue();
            String tipo;
            if (vehiculo instanceof alquilercoches.vehiculos.Turismo) {
                tipo = "Turismo";
            } else if (vehiculo instanceof alquilercoches.vehiculos.Furgoneta) {
                tipo = "Furgoneta";
            } else if (vehiculo instanceof alquilercoches.vehiculos.Motocicleta) {
                tipo = "Motocicleta";
            } else {
                tipo = "Desconocido";
            }
            return javafx.beans.binding.Bindings.createStringBinding(() -> tipo);
        });

        TableColumn<Vehiculo, String> marcaCol = new TableColumn<>("Marca");
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Vehiculo, String> modeloCol = new TableColumn<>("Modelo");
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Vehiculo, Integer> aptitudCol = new TableColumn<>("Estado");
        aptitudCol.setCellValueFactory(new PropertyValueFactory<>("aptitud"));
        aptitudCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    switch (item) {
                        case 0:
                            setText("No Apto");
                            setStyle("-fx-background-color: #ffcccc;");
                            break;
                        case 1:
                            setText("Revisión Próxima");
                            setStyle("-fx-background-color: #ffffcc;");
                            break;
                        case 2:
                            setText("Apto");
                            setStyle("-fx-background-color: #ccffcc;");
                            break;
                        default:
                            setText("Desconocido");
                            setStyle("");
                    }
                }
            }
        });

        TableColumn<Vehiculo, Date> ultimoMantenimientoCol = new TableColumn<>("Último Mantenimiento");
        ultimoMantenimientoCol.setCellValueFactory(cellData -> {
            Vehiculo vehiculo = cellData.getValue();
            Date ultimo = null;
            List<Date> mantenimientos = vehiculo.getMantenimientos();
            if (!mantenimientos.isEmpty()) {
                ultimo = mantenimientos.get(mantenimientos.size() - 1);
            }
            final Date fecha = ultimo;
            return javafx.beans.binding.Bindings.createObjectBinding(() -> fecha);
        });
        ultimoMantenimientoCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(dateFormat.format(item));
                }
            }
        });

        tablaMantenimiento.getColumns().addAll(matriculaCol, tipoCol, marcaCol, modeloCol, aptitudCol, ultimoMantenimientoCol);

        // Panel lateral: registrar mantenimiento
        VBox sidePanel = new VBox(10);
        sidePanel.setPadding(new Insets(10));
        Label tituloLabel = new Label("Registrar Mantenimiento");
        tituloLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label seleccionLabel = new Label("Seleccione un vehículo y haga clic en 'Registrar Mantenimiento'");
        Button registrarBtn = new Button("Registrar Mantenimiento");
        registrarBtn.setOnAction(e -> {
            Vehiculo seleccionado = tablaMantenimiento.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                TextInputDialog fechaDialog = new TextInputDialog();
                fechaDialog.setTitle("Registrar Mantenimiento");
                fechaDialog.setHeaderText("Ingrese la fecha del mantenimiento");
                fechaDialog.setContentText("Formato: dd/MM/yyyy");

                fechaDialog.showAndWait().ifPresent(fechaStr -> {
                    try {
                        Date fecha = dateFormat.parse(fechaStr);
                        admin.insertarMantenimiento(seleccionado.getMatricula(), fecha);
                        List<Vehiculo> vehiculosActualizados = admin.getVehiculos();
                        ObservableList<Vehiculo> nuevaLista = FXCollections.observableArrayList(vehiculosActualizados);
                        vehiculosController.getTablaVehiculos().setItems(nuevaLista);
                        tablaMantenimiento.setItems(vehiculosController.getTablaVehiculos().getItems());
                        tablaMantenimiento.refresh();
                        Util.mostrarAlerta("Información", "Mantenimiento registrado para " + seleccionado.getMatricula());
                    } catch (ParseException ex) {
                        Util.mostrarAlerta("Error", "La fecha debe tener el formato dd/MM/yyyy.");
                    }
                });

            } else {
                Util.mostrarAlerta("Error", "Debe seleccionar un vehículo.");
            }
        });

        sidePanel.getChildren().addAll(tituloLabel, seleccionLabel, registrarBtn);

        // Historial de mantenimientos
        VBox historialBox = new VBox(10);
        historialBox.setPadding(new Insets(10));
        Label historialLabel = new Label("Historial de Mantenimientos");
        historialLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        ListView<String> historialListView = new ListView<>();
        tablaMantenimiento.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                ObservableList<String> historial = FXCollections.observableArrayList();
                for (Date fecha : newSel.getMantenimientos()) {
                    historial.add(dateFormat.format(fecha));
                }
                historialListView.setItems(historial);
            } else {
                historialListView.setItems(FXCollections.observableArrayList());
            }
        });
        historialBox.getChildren().addAll(historialLabel, historialListView);

        VBox rightPane = new VBox(10, sidePanel, historialBox);
        view.setCenter(tablaMantenimiento);
        view.setRight(rightPane);

        // Compartir la lista de vehículos con el panel de mantenimiento
        tablaMantenimiento.setItems(vehiculosController.getTablaVehiculos().getItems());
    }

    public BorderPane getView() {
        return view;
    }

    public TableView<Vehiculo> getTablaMantenimiento() {
        return tablaMantenimiento;
    }
}
