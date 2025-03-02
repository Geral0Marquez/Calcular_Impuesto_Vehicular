package app;

import view.InterfazUsuario;

public class Main {
    public static void main(String[] args) {
        // Iniciar la interfaz de usuario en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> new InterfazUsuario());
    }
}
