/*
    Originalmente, no planeaba tener esta clase. Pensaba realizarlo con las propiedades de Cliente y Vehiculo como
    en el commit "f3e579cfe0423ac5a820d6253ff46bb4777776c3". Sin embargo, me di cuenta de que si bien es posible,
    sería muchísimo más engorroso manejar CRUD y GUI sin ella.
    Así que la agregué
 */


package alquilercoches.negocio;

public class Alquiler {

    private String matricula;
    private String modelo;
    private String marca;
    private String cliente;

    public Alquiler(String matricula, String modelo, String marca, String cliente) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
        this.cliente = cliente;
    }

    // Getters y Setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Alquiler{" +
                "matricula='" + matricula + '\'' +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", cliente='" + cliente + '\'' +
                '}';
    }
}
