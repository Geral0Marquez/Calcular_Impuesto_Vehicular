package view;

import controller.Controlador;
import model.Vehiculo;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class InterfazUsuario extends JFrame {
    private final JTextField txtMarca, txtModelo, txtAnio, txtCilindraje, txtAvaluo;
    private final JComboBox<String> cbTipoUso, cbCategoria;  // Declarar cbCategoria aquí
    private final JLabel lblImagen, lblResultado;
    private final Controlador controller;
    private boolean impuestoCalculado;

    public InterfazUsuario() {
        controller = new Controlador();
        impuestoCalculado = false;

        setTitle("Calculadora de Impuestos Vehiculares");
        setSize(550, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);

        // Panel principal para los campos de entrada y botones
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.setPreferredSize(new Dimension((int) (getWidth() * 0.6), getHeight())); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        // Campos de texto para los datos del vehículo
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Marca:"), gbc);
        txtMarca = new JTextField();
        gbc.gridx = 1;
        panel.add(txtMarca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Modelo:"), gbc);
        txtModelo = new JTextField();
        gbc.gridx = 1;
        panel.add(txtModelo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Año de Fabricación:"), gbc);
        txtAnio = new JTextField();
        gbc.gridx = 1;
        panel.add(txtAnio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Cilindraje:"), gbc);
        txtCilindraje = new JTextField();
        gbc.gridx = 1;
        panel.add(txtCilindraje, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Avalúo Comercial:"), gbc);
        txtAvaluo = new JTextField();
        gbc.gridx = 1;
        panel.add(txtAvaluo, gbc);
        
        // ComboBox para seleccionar la categoría del vehículo 
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Categoría:"), gbc);
        cbCategoria = new JComboBox<>(new String[]{"bus", "carro", "camión de carga"});
        gbc.gridx = 1;
        panel.add(cbCategoria, gbc);

        // ComboBox para seleccionar el tipo de uso del vehículo
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Tipo de Uso:"), gbc);
        cbTipoUso = new JComboBox<>(new String[]{"particular", "publico", "comercial"});
        gbc.gridx = 1;
        panel.add(cbTipoUso, gbc);

        // Botón para calcular el impuesto del vehículo
        JButton btnCalcular = new JButton("Calcular Impuesto");
        btnCalcular.addActionListener(e -> calcularImpuesto());
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(btnCalcular, gbc);

        // Botón para ver los detalles del cálculo del impuesto
        JButton btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.addActionListener(e -> verDetalles());
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(btnVerDetalles, gbc);

        // JLabel para mostrar la imagen del vehículo (imagen por defecto si no se encuentra)
        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(250, 300));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/img/default.jpg"));
        Image defaultImg = defaultIcon.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(defaultImg));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 9;
        panel.add(lblImagen, gbc);

        // JLabel para mostrar el resultado del cálculo del impuesto
        lblResultado = new JLabel("Impuesto: $0.00");
        lblResultado.setFont(new Font("Arial", Font.BOLD, 14));
        lblResultado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(lblResultado, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Método para calcular el impuesto del vehículo
    private void calcularImpuesto() {
        try {
            // Crear objeto Vehiculo con los datos introducidos por el usuario
            Vehiculo vehiculo = new Vehiculo(
                txtMarca.getText(),
                txtModelo.getText(),
                Integer.parseInt(txtAnio.getText()),
                Double.parseDouble(txtCilindraje.getText()),
                Double.parseDouble(txtAvaluo.getText()),
                cbTipoUso.getSelectedItem().toString(),
                cbCategoria.getSelectedItem().toString()  
            );

            // Calcular el impuesto usando el controlador
            double impuesto = controller.calcularImpuesto(vehiculo);
            String mensajeImpuesto = (impuesto == 0.0 && vehiculo.getTipoUso().equalsIgnoreCase("publico"))
                                     ? "No se aplica impuesto"
                                     : "Impuesto: $" + String.format("%.2f", impuesto);
            lblResultado.setText(mensajeImpuesto);

            // Mostrar la imagen del vehículo
            String rutaImagen = vehiculo.getImagenRuta();
            ImageIcon icon;
            try {
                icon = new ImageIcon(getClass().getResource(rutaImagen));
                if (icon.getIconWidth() == -1) {
                    throw new NullPointerException();
                }
            } catch (NullPointerException e) {
                icon = new ImageIcon(getClass().getResource("/img/default.jpg"));
            }
            Image img = icon.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));

            impuestoCalculado = true;
        } catch (NumberFormatException e) {
            // Mostrar mensaje de error si los valores numéricos son incorrectos
            JOptionPane.showMessageDialog(this, "Error: Verifique los valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Mostrar mensaje de error inesperado
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar los detalles del cálculo del impuesto
    private void verDetalles() {
        if (!impuestoCalculado) {
            // Informar al usuario que debe calcular el impuesto primero
            JOptionPane.showMessageDialog(this, "Por favor, calcule primero el impuesto antes de ver los detalles.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Crear objeto Vehiculo con los mismos datos para obtener los detalles del cálculo
        Vehiculo vehiculo = new Vehiculo(
            txtMarca.getText(),
            txtModelo.getText(),
            Integer.parseInt(txtAnio.getText()),
            Double.parseDouble(txtCilindraje.getText()),
            Double.parseDouble(txtAvaluo.getText()),
            cbTipoUso.getSelectedItem().toString(),
            cbCategoria.getSelectedItem().toString() 
        );
        String detalles = controller.getDetallesCálculo(vehiculo);
        // Mostrar los detalles del cálculo en un cuadro de diálogo
        JOptionPane.showMessageDialog(this, detalles, "Detalles del Cálculo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazUsuario::new);
    }
}
