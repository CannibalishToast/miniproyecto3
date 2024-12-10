package app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class JsonHandler {
    private static final Gson gson = new Gson();

    public static void guardarFacturas(List<Factura> facturas, String archivo) throws IOException {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(facturas, writer);
        }
    }

    public static List<Factura> leerFacturas(String archivo) throws IOException {
        try (Reader reader = new FileReader(archivo)) {
            Type listType = new TypeToken<List<Factura>>() {}.getType();
            return gson.fromJson(reader, listType);
        }
    }
}
