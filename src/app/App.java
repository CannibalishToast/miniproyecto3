 package app;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ProductoForm productoForm = new ProductoForm();
            productoForm.setVisible(true);
        });
    }
}
