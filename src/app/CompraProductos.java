package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CompraProductos extends JFrame {
    private JPanel panelProductos;
    private List<Producto> productos;
    private final String archivoXML = "productos.xml";

    public CompraProductos() {
        setTitle("Compra de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelProductos = new JPanel();
        panelProductos.setLayout(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelProductos);
        add(scrollPane, BorderLayout.CENTER);

        cargarProductos();
        mostrarProductos();
    }

    private void cargarProductos() {
        try {
            productos = XmlHandler.leerProductos(archivoXML);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
        }
    }

    private void mostrarProductos() {
        panelProductos.removeAll();
        for (Producto producto : productos) {
            JPanel panelProducto = new JPanel(new BorderLayout());
            JLabel lblProducto = new JLabel(
                    "<html><b>" + producto.getNombre() + "</b><br>Precio: $" + producto.getPrecio() + "</html>"
            );
            JButton btnComprar = new JButton("Comprar");
            btnComprar.addActionListener(e -> comprarProducto(producto));

            panelProducto.add(lblProducto, BorderLayout.CENTER);
            panelProducto.add(btnComprar, BorderLayout.EAST);
            panelProducto.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            panelProductos.add(panelProducto);
        }
        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private void comprarProducto(Producto producto) {
        FacturaForm facturaForm = new FacturaForm(producto);
        facturaForm.setVisible(true);
        this.dispose();
    }
}
