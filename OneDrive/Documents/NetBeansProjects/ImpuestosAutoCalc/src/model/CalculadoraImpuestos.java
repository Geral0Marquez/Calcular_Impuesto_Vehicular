package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CalculadoraImpuestos {

    private static final Properties tarifas = new Properties();

    // Bloque estático para cargar las tarifas desde el archivo de propiedades
    static {
        try (InputStream input = CalculadoraImpuestos.class.getResourceAsStream("/model/tarifas.properties")) {
            if (input == null) {
                throw new IOException("No se pudo encontrar el archivo tarifas.properties");
            }
            tarifas.load(input);
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de tarifas: " + e.getMessage());
        }
    }

    // Método para calcular el impuesto de un vehículo
    public static double calcularImpuesto(Vehiculo vehiculo) {
        // Si el vehículo es de servicio público, está exento de impuesto
        if (vehiculo.getTipoUso().equalsIgnoreCase("publico")) {
            return 0.0;
        }

        double base = vehiculo.getAvalúoComercial(); // Base del impuesto: avalúo comercial
        double tarifa = obtenerTarifa(vehiculo); // Obtener la tarifa según el tipo y avalúo del vehículo

        // Ajuste por antigüedad (solo para vehículos particulares)
        int antiguedad = 2025 - vehiculo.getAnioFabricacion();
        if (antiguedad > 5) {
            tarifa -= tarifa * 0.05; // 5% de descuento por antigüedad
        }

        // Ajuste por cilindraje (solo para vehículos particulares)
        if (vehiculo.getCilindraje() > 200) {
            tarifa += tarifa * 0.1; // Aumento del 10% por cilindraje alto
        }

        // Cálculo final del impuesto
        return base * (tarifa / 100);
    }

    // Método para obtener la tarifa base según el tipo y avalúo del vehículo
    private static double obtenerTarifa(Vehiculo vehiculo) {
        double avaluo = vehiculo.getAvalúoComercial();
        String tipoUso = vehiculo.getTipoUso();

        // Si el vehículo es de uso público, se aplica exención
        if (tipoUso.equalsIgnoreCase("publico")) {
            return 0.0;
        } else if (tipoUso.equalsIgnoreCase("comercial")) {
            // Para vehículos comerciales, aplicar la tarifa según el rango mínimo y máximo de tarifas comerciales
            double tarifaMin = Double.parseDouble(tarifas.getProperty("tarifa.comercial.min", "1.0"));
            double tarifaMax = Double.parseDouble(tarifas.getProperty("tarifa.comercial.max", "1.5"));
          // Para aproximarse a la tarifa comercial general, se calcula el promedio entre el rango mínimo y máximo
            return (tarifaMin + tarifaMax) / 2;
        } else {
            // Definición de rangos de avalúo para vehículos particulares
            double limite1 = Double.parseDouble(tarifas.getProperty("rango.particular.1", "55679000"));
            double limite2 = Double.parseDouble(tarifas.getProperty("rango.particular.2", "125274000"));

            // Determinar la tarifa según el avalúo del vehículo particular
            if (avaluo <= limite1) {
                return Double.parseDouble(tarifas.getProperty("tarifa.particular.1", "1.5"));
            } else if (avaluo <= limite2) {
                return Double.parseDouble(tarifas.getProperty("tarifa.particular.2", "2.5"));
            } else {
                return Double.parseDouble(tarifas.getProperty("tarifa.particular.3", "3.5"));
            }
        }
    }

    // Método público para obtener la tarifa base de un vehículo
    public static double obtenerTarifaBase(Vehiculo vehiculo) {
        return obtenerTarifa(vehiculo);
    }

    // Método para obtener el ajuste por cilindraje
    public static double obtenerAjusteCilindraje(Vehiculo vehiculo) {
        return vehiculo.getCilindraje() > 200 ? 0.1 : 0; // Aumento del 10% si el cilindraje es mayor a 200cc
    }

    // Método para calcular el descuento por antigüedad
    public static double obtenerDescuentoAntiguedad(Vehiculo vehiculo) {
        int antiguedad = 2025 - vehiculo.getAnioFabricacion();
        if (antiguedad > 5) {
            return 0.05; // 5% de descuento si el vehículo tiene más de 5 años
        }
        return 0.0; // Sin descuento si tiene 5 años o menos
    }
}
