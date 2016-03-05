/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author carlo
 */
public class ProductoModelo extends ReporteJasper<ProductoModelo> {

    private String codigo;
    private String nombre;
    private String descripcion;
    private String costo;
    private String iva;
    private String precio;

    private List<ProductoModelo> lista;

    public ProductoModelo(String codigo, String nombre, String descripcion, String costo, String iva, String precio,String raiz) {
        super(raiz);
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.iva = iva;
        this.precio = precio;
        this.lista = new ArrayList<ProductoModelo>();
    }

    public ProductoModelo(String raiz) 
    {
        super(raiz);
        this.lista = new ArrayList<ProductoModelo>();
    }

    @Override
    public String getPath() {
        return "reportes/reporteProductos.jasper";
    }

    @Override
    public List<ProductoModelo> getLista() {
        return lista;
    }

    @Override
    public Map<String, Object> getParametros() {
        Map<String, Object> lista = new HashMap<String, Object>();

        if (this.codigo != null) {
            lista.put("codigo", codigo);
        } else {
            lista.put("codigo", "");
        }

        if (nombre != null) {
            lista.put("nombre", nombre);
        } else {
            lista.put("nombre", "");
        }

        if (descripcion != null) {
            lista.put("descripcion", descripcion);
        } else {
            lista.put("descripcion", "");
        }

        if (costo != null) {
            lista.put("costo", costo);
        } else {
            lista.put("costo", "0");
        }

        if (iva != null) {
            lista.put("iva", iva);
        } else {
            lista.put("iva", "0");
        }

        if (precio != null) {
            lista.put("precio", precio);
        } else {
            lista.put("precio", "0");
        }

        return lista;
    }

}
