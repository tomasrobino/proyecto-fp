package alquilercoches.gui;

import alquilercoches.negocio.Alquiler;
import alquilercoches.Util;
import alquilercoches.db.DBCRUDAlquileres;
import alquilercoches.personal.Usuario;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Optional;

public class AlquileresController {

    private final Usuario usuario;
    private TableView<Alquiler> tablaAlquileres;
    private TextField matriculaField, modeloField, marcaField, clienteField;
    private BorderPane view;

    public AlquileresController(Usuario usuario) {
        this.usuario = usuario;
        crearVista();
    }

    private void crearVista() {
        view = new BorderPane();

        // Configurar tabla de alquileres
        tablaAlquileres = new TableView<>();
        tablaAlquileres.setEditable(true);

        TableColumn<Alquiler, String> matriculaCol = new TableColumn<>("Matrícula");
        matriculaCol.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        TableColumn<Alquiler, String> modeloCol = new TableColumn<>("Modelo");
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        TableColumn<Alquiler, String> marcaCol = new TableColumn<>("Marca");
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));
        TableColumn<Alquiler, String> clienteCol = new TableColumn<>("Cliente");
        clienteCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        tablaAlquileres.getColumns().addAll(matriculaCol, modeloCol, marcaCol, clienteCol);

        // Actualizar formulario al seleccionar un alquiler
        tablaAlquileres.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                matriculaField.setText(newSel.getMatricula());
                modeloField.setText(newSel.getModelo());
                marcaField.setText(newSel.getMarca());
                clienteField.setText(newSel.getCliente());
            }
        });

        // Crear formulario de alquiler
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        matriculaField = new TextField();
        matriculaField.setPromptText("Matrícula");
        modeloField = new TextField();
        modeloField.setPromptText("Modelo");
        marcaField = new TextField();
        marcaField.setPromptText("Marca");
        clienteField = new TextField();
        clienteField.setPromptText("Cliente");

        formPane.add(new Label("Matrícula:"), 0, 0);
        formPane.add(matriculaField, 1, 0);
        formPane.add(new Label("Modelo:"), 0, 1);
        formPane.add(modeloField, 1, 1);
        formPane.add(new Label("Marca:"), 0, 2);
        formPane.add(marcaField, 1, 2);
        formPane.add(new Label("Cliente:"), 0, 3);
        formPane.add(clienteField, 1, 3);

        // Botones del formulario
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        Button guardarBtn = new Button("Guardar");
        Button eliminarBtn = new Button("Eliminar");
        Button limpiarBtn = new Button("Limpiar");

        guardarBtn.setOnAction(e -> {
            if (validarFormulario()) {
                Alquiler alquiler = new Alquiler(
                        matriculaField.getText(),
                        modeloField.getText(),
                        marcaField.getText(),
                        clienteField.getText()
                );
                guardarAlquiler(alquiler);
                cargarAlquileres();
                limpiarFormulario();
            }
        });
        eliminarBtn.setOnAction(e -> {
            Alquiler seleccionado = tablaAlquileres.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar eliminación");
                alert.setHeaderText("Eliminar alquiler");
                alert.setContentText("¿Está seguro de eliminar el alquiler del vehículo " + seleccionado.getModelo() + "?");
                Optional<ButtonType> resultado = alert.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    eliminarAlquiler(seleccionado.getMatricula());
                    cargarAlquileres();
                    limpiarFormulario();
                }
            } else {
                Util.mostrarAlerta("Error", "Debe seleccionar un alquiler para eliminar.");
            }
        });
        limpiarBtn.setOnAction(e -> limpiarFormulario());
        buttonBox.getChildren().addAll(guardarBtn, eliminarBtn, limpiarBtn);

        VBox rightPane = new VBox(10, formPane, buttonBox);
        view.setCenter(tablaAlquileres);
        view.setRight(rightPane);

        cargarAlquileres();
    }

    private void cargarAlquileres() {
        List<Alquiler> alquileres = (usuario != null)
                ? usuario.obtenerTodosLosAlquileres()
                : DBCRUDAlquileres.obtenerTodosLosAlquileres();

        // Si no hay alquileres, se añaden algunos de ejemplo
        if (alquileres == null || alquileres.isEmpty()) {
            alquileres = List.of(
                    new Alquiler("1234ABC", "Civic", "Honda", "5435345435"),
                    new Alquiler("5678XYZ", "Focus", "Ford", "543543543"),
                    new Alquiler("9999AAA", "Corolla", "Toyota", "435435435")
            );
        }
        tablaAlquileres.setItems(FXCollections.observableArrayList(alquileres));
    }

    private boolean validarFormulario() {
        if (matriculaField.getText().isEmpty()) {
            Util.mostrarAlerta("Error", "La matrícula es obligatoria.");
            return false;
        }
        if (clienteField.getText().isEmpty()) {
            Util.mostrarAlerta("Error", "El cliente es obligatorio.");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        matriculaField.clear();
        modeloField.clear();
        marcaField.clear();
        clienteField.clear();
        tablaAlquileres.getSelectionModel().clearSelection();
    }

    private void guardarAlquiler(Alquiler alquiler) {
        Alquiler existente = DBCRUDAlquileres.buscarAlquiler(alquiler.getMatricula());
        if (existente != null) {
            DBCRUDAlquileres.actualizarAlquiler(alquiler);
        } else {
            DBCRUDAlquileres.insertarAlquiler(alquiler);
        }
    }

    private void eliminarAlquiler(String matricula) {
        if (usuario != null) {
            usuario.eliminarAlquiler(matricula);
        } else {
            DBCRUDAlquileres.eliminarAlquiler(matricula);
        }
    }

    public BorderPane getView() {
        return view;
    }
}
