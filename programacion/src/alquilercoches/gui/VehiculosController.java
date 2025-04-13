package alquilercoches.gui;

import alquilercoches.Util;
import alquilercoches.db.DBCRUDVehiculos;
import alquilercoches.personal.Administrador;
import alquilercoches.vehiculos.*;
import alquilercoches.TipoFurgoneta;
import alquilercoches.TipoTurismo;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VehiculosController {

    private final Administrador admin;
    private TableView<Vehiculo> tablaVehiculos;
    private ComboBox<String> tipoVehiculoCombo;
    private ComboBox<String> tipoEspecificoCombo;
    private TextField matriculaField, marcaField, modeloField, precioField, fabricacionField;
    private BorderPane view;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VehiculosController(Administrador admin) {
        this.admin = admin;
        crearVista();
    }

    private void crearVista() {
        view = new BorderPane();

        // Configurar tabla de vehículos
        tablaVehiculos = new TableView<>();

        TableColumn<Vehiculo, String> matriculaCol = new TableColumn<>("Matrícula");
        matriculaCol.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        TableColumn<Vehiculo, String> tipoCol = new TableColumn<>("Tipo");
        tipoCol.setCellValueFactory(cellData -> {
            Vehiculo vehiculo = cellData.getValue();
            String tipo;
            if (vehiculo instanceof Turismo) {
                tipo = "Turismo - " + ((Turismo) vehiculo).getTipo();
            } else if (vehiculo instanceof Furgoneta) {
                tipo = "Furgoneta - " + ((Furgoneta) vehiculo).getTipo();
            } else if (vehiculo instanceof Motocicleta) {
                tipo = "Motocicleta";
            } else {
                tipo = "Desconocido";
            }
            return Bindings.createStringBinding(() -> tipo);
        });

        TableColumn<Vehiculo, String> marcaCol = new TableColumn<>("Marca");
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Vehiculo, String> modeloCol = new TableColumn<>("Modelo");
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Vehiculo, Double> precioCol = new TableColumn<>("Precio");
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<Vehiculo, Integer> aptitudCol = new TableColumn<>("Estado");
        aptitudCol.setCellValueFactory(new PropertyValueFactory<>("aptitud"));
        aptitudCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Vehiculo, Integer> call(TableColumn<Vehiculo, Integer> param) {
                return new TableCell<>() {
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
                };
            }
        });

        tablaVehiculos.getColumns().addAll(matriculaCol, tipoCol, marcaCol, modeloCol, precioCol, aptitudCol);

        // Actualizar formulario al seleccionar vehículo
        tablaVehiculos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                matriculaField.setText(newSel.getMatricula());
                marcaField.setText(newSel.getMarca());
                modeloField.setText(newSel.getModelo());
                precioField.setText(String.valueOf(newSel.getPrecio()));
                fabricacionField.setText(dateFormat.format(newSel.getFabricacion()));

                if (newSel instanceof Turismo) {
                    tipoVehiculoCombo.setValue("Turismo");
                    actualizarTipoEspecifico("Turismo");
                    tipoEspecificoCombo.setValue(((Turismo) newSel).getTipo().toString());
                } else if (newSel instanceof Furgoneta) {
                    tipoVehiculoCombo.setValue("Furgoneta");
                    actualizarTipoEspecifico("Furgoneta");
                    tipoEspecificoCombo.setValue(((Furgoneta) newSel).getTipo().toString());
                } else if (newSel instanceof Motocicleta) {
                    tipoVehiculoCombo.setValue("Motocicleta");
                    actualizarTipoEspecifico("Motocicleta");
                }
            }
        });

        // Crear formulario de vehículo
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        tipoVehiculoCombo = new ComboBox<>();
        tipoVehiculoCombo.getItems().addAll("Turismo", "Motocicleta", "Furgoneta");
        tipoVehiculoCombo.setValue("Turismo");
        tipoVehiculoCombo.setOnAction(e -> actualizarTipoEspecifico(tipoVehiculoCombo.getValue()));
        tipoEspecificoCombo = new ComboBox<>();
        actualizarTipoEspecifico("Turismo");

        matriculaField = new TextField();
        matriculaField.setPromptText("Matrícula");
        marcaField = new TextField();
        marcaField.setPromptText("Marca");
        modeloField = new TextField();
        modeloField.setPromptText("Modelo");
        precioField = new TextField();
        precioField.setPromptText("Precio/día");
        fabricacionField = new TextField();
        fabricacionField.setPromptText("Fecha (dd/mm/aaaa)");

        formPane.add(new Label("Tipo:"), 0, 0);
        formPane.add(tipoVehiculoCombo, 1, 0);
        formPane.add(new Label("Subtipo:"), 0, 1);
        formPane.add(tipoEspecificoCombo, 1, 1);
        formPane.add(new Label("Matrícula:"), 0, 2);
        formPane.add(matriculaField, 1, 2);
        formPane.add(new Label("Marca:"), 0, 3);
        formPane.add(marcaField, 1, 3);
        formPane.add(new Label("Modelo:"), 0, 4);
        formPane.add(modeloField, 1, 4);
        formPane.add(new Label("Precio/día:"), 0, 5);
        formPane.add(precioField, 1, 5);
        formPane.add(new Label("Fabricación:"), 0, 6);
        formPane.add(fabricacionField, 1, 6);

        // Botones del formulario
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        Button guardarBtn = new Button("Guardar");
        Button eliminarBtn = new Button("Eliminar");
        Button limpiarBtn = new Button("Limpiar");

        guardarBtn.setOnAction(e -> {
            if (validarFormulario()) {
                try {
                    String matricula = matriculaField.getText();
                    String marca = marcaField.getText();
                    String modelo = modeloField.getText();
                    double precio = Double.parseDouble(precioField.getText());
                    Date fabricacion = dateFormat.parse(fabricacionField.getText());

                    Vehiculo vehiculo = null;
                    String tipoVehiculo = tipoVehiculoCombo.getValue();

                    switch (tipoVehiculo) {
                        case "Turismo":
                            TipoTurismo tipoTurismo = TipoTurismo.valueOf(tipoEspecificoCombo.getValue());
                            vehiculo = new Turismo(tipoTurismo, precio, fabricacion, matricula, marca, modelo);
                            break;
                        case "Motocicleta":
                            vehiculo = new Motocicleta(precio, fabricacion, matricula, marca, modelo);
                            break;
                        case "Furgoneta":
                            TipoFurgoneta tipoFurgoneta = TipoFurgoneta.valueOf(tipoEspecificoCombo.getValue());
                            vehiculo = new Furgoneta(tipoFurgoneta, precio, fabricacion, matricula, marca, modelo);
                            break;
                    }

                    if (vehiculo != null) {
                        // Agregar un mantenimiento inicial (fecha actual)
                        vehiculo.getMantenimientos().add(new Date());
                        guardarVehiculo(vehiculo);
                        cargarVehiculos();
                        limpiarFormulario();
                    }
                } catch (ParseException ex) {
                    Util.mostrarAlerta("Error", "Formato de fecha incorrecto. Use dd/mm/aaaa.");
                } catch (IllegalArgumentException ex) {
                    Util.mostrarAlerta("Error", "Error en los datos: " + ex.getMessage());
                }
            }
        });
        eliminarBtn.setOnAction(e -> {
            Vehiculo seleccionado = tablaVehiculos.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar eliminación");
                alert.setHeaderText("Eliminar vehículo");
                alert.setContentText("¿Está seguro de eliminar el vehículo con matrícula " + seleccionado.getMatricula() + "?");
                if (alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
                    admin.eliminarVehiculo(seleccionado.getMatricula());
                    cargarVehiculos();
                    limpiarFormulario();
                }
            } else {
                Util.mostrarAlerta("Error", "Debe seleccionar un vehículo para eliminar.");
            }
        });
        limpiarBtn.setOnAction(e -> limpiarFormulario());

        buttonBox.getChildren().addAll(guardarBtn, eliminarBtn, limpiarBtn);

        VBox rightPane = new VBox(10, formPane, buttonBox);
        view.setCenter(tablaVehiculos);
        view.setRight(rightPane);

        cargarVehiculos();
    }

    public void actualizarTipoEspecifico(String tipo) {
        tipoEspecificoCombo.getItems().clear();
        switch (tipo) {
            case "Turismo":
                for (TipoTurismo t : TipoTurismo.values()) {
                    tipoEspecificoCombo.getItems().add(t.toString());
                }
                tipoEspecificoCombo.setValue(TipoTurismo.values()[0].toString());
                tipoEspecificoCombo.setDisable(false);
                break;
            case "Furgoneta":
                for (TipoFurgoneta t : TipoFurgoneta.values()) {
                    tipoEspecificoCombo.getItems().add(t.toString());
                }
                tipoEspecificoCombo.setValue(TipoFurgoneta.values()[0].toString());
                tipoEspecificoCombo.setDisable(false);
                break;
            case "Motocicleta":
                tipoEspecificoCombo.setDisable(true);
                break;
        }
    }

    private boolean validarFormulario() {
        if (matriculaField.getText().isEmpty()) {
            Util.mostrarAlerta("Error", "La matrícula es obligatoria.");
            return false;
        }
        if (marcaField.getText().isEmpty()) {
            Util.mostrarAlerta("Error", "La marca es obligatoria.");
            return false;
        }
        if (modeloField.getText().isEmpty()) {
            Util.mostrarAlerta("Error", "El modelo es obligatorio.");
            return false;
        }
        try {
            double precio = Double.parseDouble(precioField.getText());
            if (precio <= 0) {
                Util.mostrarAlerta("Error", "El precio debe ser mayor que cero.");
                return false;
            }
        } catch (NumberFormatException e) {
            Util.mostrarAlerta("Error", "El precio debe ser un número válido.");
            return false;
        }
        try {
            dateFormat.parse(fabricacionField.getText());
        } catch (ParseException e) {
            Util.mostrarAlerta("Error", "La fecha debe tener el formato dd/mm/aaaa.");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        tipoVehiculoCombo.setValue("Turismo");
        actualizarTipoEspecifico("Turismo");
        matriculaField.clear();
        marcaField.clear();
        modeloField.clear();
        precioField.clear();
        fabricacionField.clear();
        tablaVehiculos.getSelectionModel().clearSelection();
    }

    private void guardarVehiculo(Vehiculo vehiculo) {
        if (DBCRUDVehiculos.getId(vehiculo.getMatricula()) != null) {
            admin.modificarVehiculo(vehiculo.getMatricula(), vehiculo);
        } else {
            admin.insertarVehiculo(vehiculo);
        }
    }

    private void cargarVehiculos() {
        List<Vehiculo> vehiculos = DBCRUDVehiculos.getVehiculos();
        if (vehiculos.isEmpty()) {
            try {
                Date fecha1 = dateFormat.parse("01/01/2022");
                Date fecha2 = dateFormat.parse("15/06/2021");
                Date fecha3 = dateFormat.parse("30/11/2023");
                Date mantenimiento = new Date();

                Turismo turismo = new Turismo(TipoTurismo.PEQUENO, 50.0, fecha1, "1234ABC", "Seat", "León");
                turismo.getMantenimientos().add(mantenimiento);
                turismo.calcularMantenimiento();

                Furgoneta furgoneta = new Furgoneta(TipoFurgoneta.GRAN_CARGA, 80.0, fecha2, "5678DEF", "Renault", "Kangoo");
                furgoneta.getMantenimientos().add(mantenimiento);
                furgoneta.calcularMantenimiento();

                Motocicleta moto = new Motocicleta(35.0, fecha3, "9012GHI", "Honda", "CBR500");
                moto.getMantenimientos().add(mantenimiento);
                moto.calcularMantenimiento();

                vehiculos = List.of(turismo, furgoneta, moto);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        tablaVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
    }

    public BorderPane getView() {
        return view;
    }

    // Permite acceder a la tabla para refrescar la vista en otros paneles (por ejemplo, en mantenimiento)
    public TableView<Vehiculo> getTablaVehiculos() {
        return tablaVehiculos;
    }
}
