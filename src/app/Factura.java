package app;

public class Factura {
    private String nombreCliente;
    private String identificacion;
    private String direccion;
    private Producto producto;
    private double impuesto;
    private double total;

    public Factura(String nombreCliente, String identificacion, String direccion, Producto producto, double impuesto, double total) {
        this.nombreCliente = nombreCliente;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.producto = producto;
        this.impuesto = impuesto;
        this.total = total;
    }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public double getImpuesto() { return impuesto; }
    public void setImpuesto(double impuesto) { this.impuesto = impuesto; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
