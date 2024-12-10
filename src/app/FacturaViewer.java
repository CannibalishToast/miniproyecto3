package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FacturaViewer extends JFrame {
    private JTable tablaFacturas;
    private DefaultTableModel modeloTabla;
    private final String archivoJSON = "facturas.json";

    public FacturaViewer() {
        setTitle("Visualización de Facturas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configuración de la tabla
        String[] columnas = {"Cliente", "Identificación", "Dirección", "Producto", "Precio", "Impuesto", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaFacturas = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar datos de las facturas
        cargarFacturas();

        // Botón de regresar al menú principal
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> regresarAlMenu());
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnRegresar);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarFacturas() {
        try {
            List<Factura> facturas = JsonHandler.leerFacturas(archivoJSON);
            modeloTabla.setRowCount(0); // Limpiar la tabla
            for (Factura factura : facturas) {
                modeloTabla.addRow(new Object[]{
                        factura.getNombreCliente(),
                        factura.getIdentificacion(),
                        factura.getDireccion(),
                        factura.getProducto().getNombre(),
                        factura.getProducto().getPrecio(),
                        factura.getImpuesto(),
                        factura.getTotal()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar facturas: " + e.getMessage());
        }
    }

    private void regresarAlMenu() {
        ProductoForm productoForm = new ProductoForm();
        productoForm.setVisible(true);
        this.dispose();
    }
}
