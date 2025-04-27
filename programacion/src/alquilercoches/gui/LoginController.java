package alquilercoches.gui;

import alquilercoches.personal.Administrador;
import alquilercoches.personal.Usuario;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class LoginController {

    private Usuario usuario;
    private Administrador admin;
    private String rol;

    public boolean mostrarLoginDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Iniciar Sesión");
        dialog.setHeaderText("Ingrese sus credenciales");

        ButtonType loginButtonType = new ButtonType("Iniciar Sesión", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField dniField = new TextField();
        dniField.setPromptText("DNI");
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        ComboBox<String> rolCombo = new ComboBox<>();
        rolCombo.getItems().addAll("Usuario", "Administrador");
        rolCombo.setValue("Usuario");

        grid.add(new Label("DNI:"), 0, 0);
        grid.add(dniField, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombreField, 1, 1);
        grid.add(new Label("Contraseña:"), 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(new Label("Rol:"), 0, 3);
        grid.add(rolCombo, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dniField.requestFocus();

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == loginButtonType) {
            String dni = dniField.getText();
            String nombre = nombreField.getText();
            this.rol = rolCombo.getValue();

            // En este ejemplo se acepta cualquier credencial
            if ("Administrador".equals(this.rol)) {
                admin = new Administrador(dni, nombre);
            } else {
                usuario = new Usuario(dni, nombre);
            }
            return true;
        }
        return false;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public String getRol() {
        return rol;
    }
}
