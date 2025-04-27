package alquilercoches.gui;

import alquilercoches.personal.Administrador;
import alquilercoches.personal.Usuario;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static alquilercoches.json.JSON.generarJSON;

public class AlquilerCochesApp extends Application {

    private Stage primaryStage;

    // Usuario activo o administrador
    private Usuario usuarioActual;
    private Administrador adminActual;
    private boolean esAdmin = false;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sistema de Alquiler de Coches");

        // Mostrar diálogo de inicio de sesión
        LoginController loginController = new LoginController();
        boolean loginExitoso = loginController.mostrarLoginDialog();

        if (!loginExitoso) {
            primaryStage.close();
            return;
        }

        // Establecer el usuario o el administrador según el rol seleccionado
        if ("Administrador".equals(loginController.getRol())) {
            adminActual = loginController.getAdmin();
            esAdmin = true;
        } else {
            usuarioActual = loginController.getUsuario();
        }

        inicializarInterfazPrincipal();
        primaryStage.show();
    }

    private void inicializarInterfazPrincipal() {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(crearMenuSuperior());

        TabPane tabPane = new TabPane();

        // Panel de gestión de alquileres
        AlquileresController alquileresController = new AlquileresController(usuarioActual);
        Tab alquileresTab = new Tab("Gestión de Alquileres", alquileresController.getView());
        alquileresTab.setClosable(false);
        tabPane.getTabs().add(alquileresTab);

        // Panel de gestión de clientes
        ClientesController clientesController = new ClientesController(usuarioActual);
        Tab clientesTab = new Tab("Gestión de Clientes", clientesController.getView());
        clientesTab.setClosable(false);
        tabPane.getTabs().add(clientesTab);


        // Paneles adicionales solo para administradores
        if (esAdmin) {
            VehiculosController vehiculosController = new VehiculosController(adminActual);
            Tab vehiculosTab = new Tab("Gestión de Vehículos", vehiculosController.getView());
            vehiculosTab.setClosable(false);

            MantenimientoController mantenimientoController = new MantenimientoController(adminActual, vehiculosController);
            Tab mantenimientoTab = new Tab("Mantenimiento", mantenimientoController.getView());
            mantenimientoTab.setClosable(false);
            vehiculosController.setMantenimientoController(mantenimientoController);

            tabPane.getTabs().addAll(vehiculosTab, mantenimientoTab);
        }

        mainLayout.setCenter(tabPane);
        Scene scene = new Scene(mainLayout, 900, 700);
        primaryStage.setScene(scene);
    }

    private MenuBar crearMenuSuperior() {
        MenuBar menuBar = new MenuBar();

        // Menú Sistema
        Menu sistemaMenu = new Menu("Sistema");
        MenuItem generarJSON = new MenuItem("Generar JSON");
        MenuItem logoutItem = new MenuItem("Cerrar Sesión");
        MenuItem exitItem = new MenuItem("Salir");

        generarJSON.setOnAction(e -> {
            generarJSON();
        });

        logoutItem.setOnAction(e -> {
            // Reinicia la aplicación (se volverá a solicitar el login)
            usuarioActual = null;
            adminActual = null;
            start(primaryStage);
        });

        exitItem.setOnAction(e -> primaryStage.close());
        sistemaMenu.getItems().addAll(generarJSON, logoutItem, exitItem);

        // Menú Ayuda
        Menu ayudaMenu = new Menu("Ayuda");
        MenuItem aboutItem = new MenuItem("Acerca de");
        aboutItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca de");
            alert.setHeaderText("Sistema de Alquiler de Coches");
            alert.setContentText("Versión 1.0\nDesarrollado para el curso de Programación Java");
            alert.showAndWait();
        });
        ayudaMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(sistemaMenu, ayudaMenu);
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
