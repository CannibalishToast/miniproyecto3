package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ProductoForm extends JFrame {
    private JTextField txtCodigo, txtNombre, txtPrecio, txtCategoria;
    private JButton btnCrearProducto, btnActualizar, btnEliminar, btnIrCompra, btnIrFacturas;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private List<Producto> productos;

    private final String archivoXML = "productos.xml";

    public ProductoForm() {
        setTitle("Gestión de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        productos = new ArrayList<>();
        cargarProductos();

        // Panel superior: Formulario para agregar productos
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Crear Producto"));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtCategoria = new JTextField();

        btnCrearProducto = new JButton("Crear Producto");
        btnCrearProducto.addActionListener(this::crearProducto);

        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(txtCodigo);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(btnCrearProducto);

        add(panelFormulario, BorderLayout.NORTH);

        // Panel central: Tabla para mostrar productos
        String[] columnas = {"Código", "Nombre", "Precio", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        cargarTabla();

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior: Botones para actualizar, eliminar, navegar a compra y facturas
        JPanel panelBotones = new JPanel();

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(this::actualizarProducto);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this::eliminarProducto);

        btnIrCompra = new JButton("Ir a Compra");
        btnIrCompra.addActionListener(this::irPantallaCompra);

        btnIrFacturas = new JButton("Ir a Facturas");
        btnIrFacturas.addActionListener(this::irPantallaFacturas);

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnIrCompra);
        panelBotones.add(btnIrFacturas);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarProductos() {
        try {
            productos = XmlHandler.leerProductos(archivoXML);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
        }
    }

    private void guardarProductos() {
        try {
            XmlHandler.guardarProductos(productos, archivoXML);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar productos: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        for (Producto producto : productos) {
            modeloTabla.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getCategoria()
            });
        }
    }

    private void crearProducto(ActionEvent e) {
        try {
            String codigo = txtCodigo.getText();
            validarCodigoProducto(codigo);

            String nombre = txtNombre.getText();

            String precioTexto = txtPrecio.getText();
            double precio = validarPrecio(precioTexto);

            String categoria = txtCategoria.getText();

            Producto producto = new Producto(codigo, nombre, precio, categoria, null);
            productos.add(producto);
            guardarProductos();
            cargarTabla();

            JOptionPane.showMessageDialog(this, "Producto creado exitosamente.");
            limpiarFormulario();
        } catch (CodigoProductoInvalidoException | PrecioNegativoException | PrecioNoNumericoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear producto: " + ex.getMessage());
        }
    }

    private void actualizarProducto(ActionEvent e) {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para actualizar.");
            return;
        }

        try {
            String codigo = txtCodigo.getText();
            validarCodigoProducto(codigo);

            String nombre = txtNombre.getText();

            String precioTexto = txtPrecio.getText();
            double precio = validarPrecio(precioTexto);

            String categoria = txtCategoria.getText();

            Producto producto = productos.get(filaSeleccionada);
            producto.setCodigo(codigo);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setCategoria(categoria);

            guardarProductos();
            cargarTabla();

            JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente.");
            limpiarFormulario();
        } catch (CodigoProductoInvalidoException | PrecioNegativoException | PrecioNoNumericoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar producto: " + ex.getMessage());
        }
    }

    private void eliminarProducto(ActionEvent e) {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
            return;
        }

        productos.remove(filaSeleccionada);
        guardarProductos();
        cargarTabla();
        JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.");
    }

    private void irPantallaCompra(ActionEvent e) {
        CompraProductos compraProductos = new CompraProductos();
        compraProductos.setVisible(true);
        this.dispose(); // Cierra la ventana actual
    }

    private void irPantallaFacturas(ActionEvent e) {
        FacturaViewer facturaViewer = new FacturaViewer();
        facturaViewer.setVisible(true);
        this.dispose(); // Cierra la ventana actual
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCategoria.setText("");
    }

    // Métodos de validación con excepciones personalizadas
    private void validarCodigoProducto(String codigo) throws CodigoProductoInvalidoException {
        if (codigo == null || !codigo.matches(".*\\d{3,}.*")) {
            throw new CodigoProductoInvalidoException("El código del producto debe contener al menos 3 números.");
        }
    }

    private double validarPrecio(String precioTexto) throws PrecioNegativoException, PrecioNoNumericoException {
        try {
            double precio = Double.parseDouble(precioTexto);
            if (precio < 0) {
                throw new PrecioNegativoException("El precio no puede ser negativo.");
            }
            return precio;
        } catch (NumberFormatException ex) {
            throw new PrecioNoNumericoException("El precio debe ser un número válido.");
        }
    }

    // Excepciones personalizadas como clases internas
    private static class CodigoProductoInvalidoException extends Exception {
        public CodigoProductoInvalidoException(String mensaje) {
            super(mensaje);
        }
    }

    private static class PrecioNegativoException extends Exception {
        public PrecioNegativoException(String mensaje) {
            super(mensaje);
        }
    }

    private static class PrecioNoNumericoException extends Exception {
        public PrecioNoNumericoException(String mensaje) {
            super(mensaje);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductoForm().setVisible(true));
    }
}
