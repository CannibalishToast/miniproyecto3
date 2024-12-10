package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class FacturaForm extends JFrame {
    private JTextField txtNombreCliente, txtIdentificacion, txtDireccion;
    private JLabel lblProducto, lblPrecio, lblImpuesto, lblTotal;
    private JButton btnGuardar, btnCancelar;
    private Producto productoSeleccionado;
    private final String archivoJSON = "facturas.json";

    public FacturaForm(Producto producto) {
        this.productoSeleccionado = producto;

        setTitle("Crear Factura");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));

        // Crear formulario
        txtNombreCliente = new JTextField();
        txtIdentificacion = new JTextField();
        txtDireccion = new JTextField();
        lblProducto = new JLabel("Producto: " + producto.getNombre());
        lblPrecio = new JLabel("Precio: " + producto.getPrecio());
        lblImpuesto = new JLabel("Impuesto: " + calcularImpuesto(producto.getPrecio()));
        lblTotal = new JLabel("Total: " + calcularTotal(producto.getPrecio()));

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(this::guardarFactura);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this::regresar);

        add(new JLabel("Nombre del Cliente:"));
        add(txtNombreCliente);
        add(new JLabel("Identificación:"));
        add(txtIdentificacion);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(lblProducto);
        add(lblPrecio);
        add(lblImpuesto);
        add(lblTotal);
        add(btnGuardar);
        add(btnCancelar);
    }

    private double calcularImpuesto(double precio) {
        return precio * 0.12; // 12% de impuesto
    }

    private double calcularTotal(double precio) {
        return precio + calcularImpuesto(precio);
    }

    private void guardarFactura(ActionEvent e) {
        try {
            String nombreCliente = txtNombreCliente.getText();
            String identificacion = txtIdentificacion.getText();
            String direccion = txtDireccion.getText();
            double impuesto = calcularImpuesto(productoSeleccionado.getPrecio());
            double total = calcularTotal(productoSeleccionado.getPrecio());

            Factura factura = new Factura(nombreCliente, identificacion, direccion, productoSeleccionado, impuesto, total);
            List<Factura> facturas = new ArrayList<>();

            try {
                facturas = JsonHandler.leerFacturas(archivoJSON);
            } catch (Exception ex) {
                // Si no existen facturas previas, iniciamos la lista vacía
            }

            facturas.add(factura);
            JsonHandler.guardarFacturas(facturas, archivoJSON);

            JOptionPane.showMessageDialog(this, "Factura guardada exitosamente.");
            regresar(null); // Volver a la pantalla anterior después de guardar
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la factura: " + ex.getMessage());
        }
    }

    private void regresar(ActionEvent e) {
        CompraProductos compraProductos = new CompraProductos();
        compraProductos.setVisible(true);
        this.dispose(); // Cierra la ventana actual
    }
}
