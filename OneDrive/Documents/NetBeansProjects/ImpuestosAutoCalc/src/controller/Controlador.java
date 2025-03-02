package controller;

import model.CalculadoraImpuestos;
import model.Vehiculo;

import javax.swing.*;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.util.Properties;

public class Controlador {
    // Método para calcular el impuesto a partir de los datos del vehículo
    public double calcularImpuesto(Vehiculo vehiculo) {
        return CalculadoraImpuestos.calcularImpuesto(vehiculo);
    }

    // Método para obtener los detalles del cálculo del impuesto
    public String getDetallesCálculo(Vehiculo vehiculo) {
        double impuesto = CalculadoraImpuestos.calcularImpuesto(vehiculo);
        double tarifaBase = CalculadoraImpuestos.obtenerTarifaBase(vehiculo);
        double descuentoAntiguedad = CalculadoraImpuestos.obtenerDescuentoAntiguedad(vehiculo);
        double ajusteCilindraje = CalculadoraImpuestos.obtenerAjusteCilindraje(vehiculo);

        String detalles = "Marca: " + vehiculo.getMarca() + "\n" +
                           "Modelo: " + vehiculo.getModelo() + "\n" +
                           "Año de Fabricación: " + vehiculo.getAnioFabricacion() + "\n" +
                           "Cilindraje: " + vehiculo.getCilindraje() + " CC\n" +
                           "Avalúo Comercial: $" + vehiculo.getAvalúoComercial() + "\n" +
                           "Tipo de Uso: " + vehiculo.getTipoUso() + "\n\n" +
                           "Categoría: " + vehiculo.getCategoria() + "\n\n" +  
                           "Tarifa Base: %" + String.format("%.2f", tarifaBase) + "\n" +
                           "Descuento por Antigüedad: %" + String.format("%.2f", descuentoAntiguedad) + "\n" +
                           "Ajuste por Cilindraje: %" + String.format("%.2f", ajusteCilindraje) + "\n" +
                           "Impuesto Calculado: $" + String.format("%.2f", impuesto);
        
        return detalles;
    }

    // Método para actualizar las tarifas en el archivo tarifas.properties
    public void actualizarTarifas(double tarifaParticular1, double tarifaParticular2, double tarifaParticular3, 
                                   double tarifaPublico, double tarifaMoto, double rangoParticular1, 
                                   double rangoParticular2) {
        try {
            // Actualizando el archivo de tarifas (tarifas.properties)
            String archivoTarifas = "model/tarifas.properties";
            Properties propiedades = new Properties();
            
            // Cargando las propiedades existentes
            propiedades.load(getClass().getClassLoader().getResourceAsStream(archivoTarifas));
            
            // Estableciendo las nuevas propiedades
            propiedades.setProperty("tarifa.particular.1", String.valueOf(tarifaParticular1));
            propiedades.setProperty("tarifa.particular.2", String.valueOf(tarifaParticular2));
            propiedades.setProperty("tarifa.particular.3", String.valueOf(tarifaParticular3));
            propiedades.setProperty("tarifa.publico", String.valueOf(tarifaPublico));
            propiedades.setProperty("tarifa.moto", String.valueOf(tarifaMoto));
            propiedades.setProperty("rango.particular.1", String.valueOf(rangoParticular1));
            propiedades.setProperty("rango.particular.2", String.valueOf(rangoParticular2));

            // Guardando los cambios en el archivo con FileOutputStream
            try (FileOutputStream outputStream = new FileOutputStream(getClass().getClassLoader().getResource(archivoTarifas).getPath())) {
                propiedades.store(outputStream, null);  // Usamos OutputStream aquí
                JOptionPane.showMessageDialog(null, "Las tarifas se han actualizado correctamente.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar las tarifas: " + e.getMessage());
        }
    }
}