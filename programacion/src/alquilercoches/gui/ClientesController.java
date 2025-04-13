package alquilercoches.gui;

import alquilercoches.negocio.Cliente;
import alquilercoches.Util;
import alquilercoches.db.DBCRUDClientes;
import alquilercoches.personal.Usuario;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Optional;

public class ClientesController {

    private final Usuario usuario;
    private TableView<Cliente> tablaClientes;
    private TextField dniField, nombreField, direccionField, telefonoField, emailField;
    private BorderPane view;

    public ClientesController(Usuario usuario) {
        this.usuario = usuario;
        crearVista();
    }

    private void crearVista() {
        view = new BorderPane();

        // Configurar tabla de clientes
        tablaClientes = new TableView<>();
        tablaClientes.setEditable(true);

        TableColumn<Cliente, String> dniCol = new TableColumn<>("DNI");
        dniCol.setCellValueFactory(new PropertyValueFactory<>("dni"));
        TableColumn<Cliente, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Cliente, String> direccionCol = new TableColumn<>("Dirección");
        direccionCol.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        TableColumn<Cliente, String> telefonoCol = new TableColumn<>("Teléfono");
        telefonoCol.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        TableColumn<Cliente, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        tablaClientes.getColumns().addAll(dniCol, nombreCol, direccionCol, telefonoCol, emailCol);

        // Actualizar formulario al seleccionar un cliente
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                dniField.setText(newSel.getDni());
                nombreField.setText(newSel.getNombre());
                direccionField.setText(newSel.getDireccion());
                telefonoField.setText(newSel.getTelefono());
                emailField.setText(newSel.getEmail());
            }
        });

        // Crear formulario de cliente
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        dniField = new TextField();
        dniField.setPromptText("DNI");
        nombreField = new TextField();
        nombreField.setPromptText("Nombre");
        direccionField = new TextField();
        direccionField.setPromptText("Dirección");
        telefonoField = new TextField();
        telefonoField.setPromptText("Teléfono");
        emailField = new TextField();
        emailField.setPromptText("Email");

        formPane.add(new Label("DNI:"), 0, 0);
        formPane.add(dniField, 1, 0);
        formPane.add(new Label("Nombre:"), 0, 1);
        formPane.add(nombreField, 1, 1);
        formPane.add(new Label("Dirección:"), 0, 2);
        formPane.add(direccionField, 1, 2);
        formPane.add(new Label("Teléfono:"), 0, 3);
        formPane.add(telefonoField, 1, 3);
        formPane.add(new Label("Email:"), 0, 4);
        formPane.add(emailField, 1, 4);

        // Botones del formulario
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        Button guardarBtn = new Button("Guardar");
        Button eliminarBtn = new Button("Eliminar");
        Button limpiarBtn = new Button("Limpiar");

        guardarBtn.setOnAction(e -> {
            if (validarFormulario()) {
                Cliente cliente = new Cliente(
                        dniField.getText(),
                        nombreField.getText(),
                        direccionField.getText(),
                        telefonoField.getText(),
                        emailField.getText()
                );
                guardarCliente(cliente);
                cargarClientes();
                limpiarFormulario();
            }
        });
        eliminarBtn.setOnAction(e -> {
            Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar eliminación");
                alert.setHeaderText("Eliminar cliente");
                alert.setContentText("¿Está seguro de eliminar al cliente " + seleccionado.getNombre() + "?");
                Optional<ButtonType> resultado = alert.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    eliminarCliente(seleccionado.getDni());
                    cargarClientes();
                    limpiarFormulario();
                }
            } else {
                Util.mostrarAlerta("Error", "Debe seleccionar un cliente para eliminar.");
            }
        });
        limpiarBtn.setOnAction(e -> limpiarFormulario());
        buttonBox.getChildren().addAll(guardarBtn, eliminarBtn, limpiarBtn);

        VBox rightPane = new VBox(10, formPane, buttonBox);
        view.setCenter(tablaClientes);
        view.setRight(rightPane);

        cargarClientes();
    }

    private void cargarClientes() {
        // En una aplicación real se cargarían los datos de la BD
        List<Cliente> clientes = (usuario != null)
                ? usuario.obtenerTodosLosClientes()
                : DBCRUDClientes.obtenerTodosLosClientes();

        // Si no hay clientes, se añaden algunos de ejemplo
        if (clientes == null || clientes.isEmpty()) {
            clientes = List.of(
                    new Cliente("12345678A", "Juan Pérez", "Calle Mayor 1", "666111222", "juan@example.com"),
                    new Cliente("87654321B", "María López", "Avenida Libertad 23", "666333444", "maria@example.com"),
                    new Cliente("11122233C", "Carlos Gómez", "Plaza España 5", "666555666", "carlos@example.com")
            );
        }
        tablaClientes.setItems(FXCollections.observableArrayList(clientes));
    }

    private boolean validarFormulario() {
        if (dniField.getText().isEmpty()) {
            Util.mostrarAlerta("Error", "El DNI es obligatorio.");
            return false;
        }
        if (nombreField.getText().isEmpty()) {
            Util.mostrarAlerta("Error", "El nombre es obligatorio.");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        dniField.clear();
        nombreField.clear();
        direccionField.clear();
        telefonoField.clear();
        emailField.clear();
        tablaClientes.getSelectionModel().clearSelection();
    }

    private void guardarCliente(Cliente cliente) {
        if (usuario != null) {
            Cliente existente = usuario.buscarCliente(cliente.getDni());
            if (existente != null) {
                usuario.actualizarCliente(cliente);
            } else {
                usuario.insertarCliente(cliente);
            }
        } else {
            Cliente existente = DBCRUDClientes.buscarCliente(cliente.getDni());
            if (existente != null) {
                DBCRUDClientes.actualizarCliente(cliente);
            } else {
                DBCRUDClientes.insertarCliente(cliente);
            }
        }
    }

    private void eliminarCliente(String dni) {
        if (usuario != null) {
            usuario.eliminarCliente(dni);
        } else {
            DBCRUDClientes.eliminarCliente(dni);
        }
    }

    public BorderPane getView() {
        return view;
    }
}
