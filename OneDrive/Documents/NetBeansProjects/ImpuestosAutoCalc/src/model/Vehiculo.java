package model;

public class Vehiculo {

    private String marca;
    private String modelo;
    private int anioFabricacion;
    private double cilindraje;
    private double avalúoComercial;
    private String tipoUso;
    private String categoria; // le agrego esto para que se vea de modo general la aplicación de las tarifas 
    private final String imagenRuta;

    // Constructor con validaciones
    public Vehiculo(String marca, String modelo, int anioFabricacion, double cilindraje, double avalúoComercial, String tipoUso, String categoria) {
        if (marca == null || marca.isEmpty()) {
            throw new IllegalArgumentException("La marca no puede ser vacía");
        }
        if (modelo == null || modelo.isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede ser vacío");
        }
        if (anioFabricacion < 1900 || anioFabricacion > 2025) {
            throw new IllegalArgumentException("El año de fabricación no es válido");
        }
        if (cilindraje <= 0) {
            throw new IllegalArgumentException("El cilindraje debe ser mayor que 0");
        }
        if (avalúoComercial <= 0) {
            throw new IllegalArgumentException("El avalúo comercial debe ser mayor que 0");
        }

        // Validaciones según la categoría y tipo de uso
        if ("bus".equalsIgnoreCase(categoria) && !"publico".equalsIgnoreCase(tipoUso)) {
            throw new IllegalArgumentException("El bus debe ser de tipo uso público.");
        }
        if ("camión de carga".equalsIgnoreCase(categoria) && !"comercial".equalsIgnoreCase(tipoUso)) {
            throw new IllegalArgumentException("El camión de carga debe ser de tipo uso comercial.");
        }
        if ("carro".equalsIgnoreCase(categoria) && !"particular".equalsIgnoreCase(tipoUso)) {
            throw new IllegalArgumentException("El carro debe ser de tipo uso particular.");
        }

        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.cilindraje = cilindraje;
        this.avalúoComercial = avalúoComercial;
        this.tipoUso = tipoUso;
        this.categoria = categoria;
        this.imagenRuta = obtenerImagenRuta();
    }

    // Método para obtener la ruta de la imagen según la categoría 
    private String obtenerImagenRuta() {
        return switch (categoria.toLowerCase()) {
            case "carro" -> "/img/carro.jpg"; 
            case "bus" -> "/img/bus.jpg"; 
            case "camión de carga" -> "/img/camion_de_carga.jpg";
            default -> "/img/default.jpg"; 
        };
    }

    // Getters y Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public double getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(double cilindraje) {
        this.cilindraje = cilindraje;
    }

    public double getAvalúoComercial() {
        return avalúoComercial;
    }

    public void setAvalúoComercial(double avalúoComercial) {
        this.avalúoComercial = avalúoComercial;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagenRuta() {
        return imagenRuta;
    }
}
