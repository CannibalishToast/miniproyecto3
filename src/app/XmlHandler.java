package app;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlHandler {
    public static List<Producto> leerProductos(String archivo) throws Exception {
        List<Producto> productos = new ArrayList<>();
        File file = new File(archivo);

        if (!file.exists()) return productos;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        NodeList nodeList = doc.getElementsByTagName("producto");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
            String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
            double precio = Double.parseDouble(element.getElementsByTagName("precio").item(0).getTextContent());
            String categoria = element.getElementsByTagName("categoria").item(0).getTextContent();
            String imagen = element.getElementsByTagName("imagen").item(0).getTextContent();
            productos.add(new Producto(codigo, nombre, precio, categoria, imagen));
        }
        return productos;
    }

    public static void guardarProductos(List<Producto> productos, String archivo) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("productos");
        doc.appendChild(root);

        for (Producto producto : productos) {
            Element prodElement = doc.createElement("producto");

            Element codigo = doc.createElement("codigo");
            codigo.appendChild(doc.createTextNode(producto.getCodigo()));
            prodElement.appendChild(codigo);

            Element nombre = doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode(producto.getNombre()));
            prodElement.appendChild(nombre);

            Element precio = doc.createElement("precio");
            precio.appendChild(doc.createTextNode(String.valueOf(producto.getPrecio())));
            prodElement.appendChild(precio);

            Element categoria = doc.createElement("categoria");
            categoria.appendChild(doc.createTextNode(producto.getCategoria()));
            prodElement.appendChild(categoria);

            Element imagen = doc.createElement("imagen");
            imagen.appendChild(doc.createTextNode(producto.getImagen()));
            prodElement.appendChild(imagen);

            root.appendChild(prodElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(archivo));
        transformer.transform(source, result);
    }
}
