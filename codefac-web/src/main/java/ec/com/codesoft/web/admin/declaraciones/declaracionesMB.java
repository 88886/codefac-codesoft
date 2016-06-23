/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.declaraciones;

import ec.com.codesoft.model.Compra;
import ec.com.codesoft.model.Declaraciones;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.web.seguridad.SistemaMB;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped
public class declaracionesMB implements Serializable {

    private List<String> meses;
    private List<String> anos;
    private String mesSeleccionado;
    private Date fechaInicial;
    private Date fechaFinal;
    private BigDecimal subTotalVentas;
    private BigDecimal subTotalCompras;
    private BigDecimal subTotalNotas;
    private BigDecimal ivaCompras;
    private BigDecimal ivaNotas;
    private BigDecimal ivaVentas;
    private Declaraciones declaraciones;
    private Declaraciones declaracionAnterior;
    private Boolean mostrarCampo601;

    private BigDecimal ivaFavor;

    private List<Venta> ventasObtenidas;
    private List<Venta> notasObtenidas;
    private List<Compra> comprasObtenidas;

    @EJB
    private FacturaServicio facturaServicio;

    @ManagedProperty(value = "#{sistemaMB}")
    private SistemaMB sistemaMB;

    @PostConstruct
    public void inicializar() {
        meses = new ArrayList<>();
        //llenarMeses();
        declaraciones = new Declaraciones();
        declaracionAnterior = facturaServicio.obtenerUltimaDeclaracion();
        mostrarCampo601 = false;

    }

    public void llenarMeses() {
        meses.add("Enero");
        meses.add("Febrero");
        meses.add("Marzo");
        meses.add("Abril");
        meses.add("Mayo");
        meses.add("Junio");
        meses.add("Julio");
        meses.add("Agosto");
        meses.add("Septiembre");
        meses.add("Octubre");
        meses.add("Noviembre");
        meses.add("Diciembre");

    }

    public void buscarDatos() {
        ventasObtenidas = new ArrayList<>();
        notasObtenidas = new ArrayList<>();
        comprasObtenidas = new ArrayList<>();
        ventasObtenidas = facturaServicio.obtnerSubtotalesVentas(fechaFinal);
        comprasObtenidas = facturaServicio.obtnerSubtotalesCompras(fechaFinal);
        notasObtenidas = facturaServicio.obtnerTotalesNotas(fechaFinal);
        System.out.println("Ventas " + ventasObtenidas.toString());
        subTotalVentas = new BigDecimal("0.0");
        subTotalCompras = new BigDecimal("0.0");
        subTotalNotas = new BigDecimal("0.0");
        ivaCompras = new BigDecimal("0.0");
        ivaVentas = new BigDecimal("0.0");
        ivaNotas = new BigDecimal("0.0");

        //suma subtotales ventas
        for (Venta ventas : ventasObtenidas) {
            subTotalVentas = subTotalVentas.add(ventas.getTotal().subtract(ventas.getIva()));
        }

        //suma iva ventas
        for (Venta ventas : ventasObtenidas) {
            ivaVentas = ivaVentas.add(ventas.getIva());
        }

        //suma subtotales compras
        for (Compra compras : comprasObtenidas) {
            subTotalCompras = subTotalCompras.add(compras.getTotal().subtract(compras.getIva()));
        }

        //suma iva compras
        for (Compra compras : comprasObtenidas) {
            ivaCompras = ivaCompras.add(compras.getIva());
        }

        //suma subtotales notas
        for (Venta notas : notasObtenidas) {
            subTotalNotas = subTotalNotas.add(notas.getTotal().subtract(notas.getIva()));
        }

        //suma iva notas
        for (Venta notas : notasObtenidas) {
            ivaNotas = ivaNotas.add(notas.getIva());
        }

        declaraciones.setSubtotalCompras(subTotalCompras);
        declaraciones.setSubtotalVentas(subTotalVentas);
        declaraciones.setSubTotalNotas(subTotalNotas);

        verificarTipoIva();

    }

    public void verificarTipoIva() {
        ivaFavor = new BigDecimal("0.0");

        if (subTotalCompras.compareTo(subTotalVentas) == 1) {
            ivaFavor = ivaCompras.subtract(ivaVentas);
            declaraciones.setIvaGenerado(ivaFavor);
            declaraciones.setEstado("a favor");
            mostrarCampo601 = false;
        } else if (subTotalCompras.compareTo(subTotalVentas) == 0) {
            ivaFavor = new BigDecimal("0.0");
            declaraciones.setIvaGenerado(ivaFavor);
            declaraciones.setEstado("igual");
            mostrarCampo601 = false;
        } else if (subTotalCompras.compareTo(subTotalVentas) == -1) {
            ivaFavor = new BigDecimal("0.0");
            declaraciones.setIvaGenerado(ivaVentas.subtract(ivaCompras));
            declaraciones.setEstado("en contra");
            mostrarCampo601 = true;

        }
    }

    public void prueba() {
        System.out.println("Prueba");
    }

    public void generarXML() {
        System.out.println("Generar XML");
        try {
            Date fecha = new Date();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // elemento raiz
            Document doc = docBuilder.newDocument();

            //formulario
            Element formulario = (Element) doc.createElement("formulario");
            doc.appendChild(formulario);

            Attr attroot = doc.createAttribute("version");
            attroot.setValue("0.2");
            formulario.setAttributeNode(attroot);

            //cabecera
            Element cabecera = (Element) doc.createElement("cabecera");
            formulario.appendChild(cabecera);
            //elementos de la cabecera

            Element cod_vers_formulario = (Element) doc.createElement("codigo_version_formulario");
            cod_vers_formulario.appendChild(doc.createTextNode("04201603"));
            cabecera.appendChild(cod_vers_formulario);

            Element ruc = (Element) doc.createElement("ruc");
            ruc.appendChild(doc.createTextNode(sistemaMB.getEmpresa().getRucEmpresa()));
            cabecera.appendChild(ruc);

            Element moneda = (Element) doc.createElement("codigo_moneda");
            moneda.appendChild(doc.createTextNode("1"));
            cabecera.appendChild(moneda);

            //detalle
            Element detalle = (Element) doc.createElement("detalle");
            formulario.appendChild(detalle);

            //elementos de detalle
            //anio
            Element c102 = (Element) doc.createElement("campo");
            c102.appendChild(doc.createTextNode(String.valueOf(fecha.getYear())));
            detalle.appendChild(c102);
            Attr attrc102 = doc.createAttribute("numero");
            attrc102.setValue("102");
            c102.setAttributeNode(attrc102);

            //mes
            Element c101 = (Element) doc.createElement("campo");
            c101.appendChild(doc.createTextNode(String.valueOf(fecha.getMonth() + 1)));
            detalle.appendChild(c101);
            Attr attrc101 = doc.createAttribute("numero");
            attrc101.setValue("101");
            c101.setAttributeNode(attrc101);
            
            //campo 31 //tipo de documento (original o sustitutivo)
            Element c31 = (Element) doc.createElement("campo");
            c31.appendChild(doc.createTextNode("O"));
            detalle.appendChild(c31);
            Attr attrc31 = doc.createAttribute("numero");
            attrc31.setValue("31");
            c31.setAttributeNode(attrc31);
            
            //campo 201 ruc
            Element c201 = (Element) doc.createElement("campo");
            c201.appendChild(doc.createTextNode(sistemaMB.getEmpresa().getRucEmpresa()));
            detalle.appendChild(c201);
            Attr attrc201 = doc.createAttribute("numero");
            attrc201.setValue("201");
            c201.setAttributeNode(attrc201);
            
            //campo 202 nombre del sujeto
            Element c202 = (Element) doc.createElement("campo");
            c202.appendChild(doc.createTextNode(sistemaMB.getEmpresa().getNombre()));
            detalle.appendChild(c202);
            Attr attrc202 = doc.createAttribute("numero");
            attrc202.setValue("202");
            c202.setAttributeNode(attrc202);
            
            //campo 411 suma de las ventas
            Element c411 = (Element) doc.createElement("campo");
            c411.appendChild(doc.createTextNode(subTotalVentas.toString()));
            detalle.appendChild(c411);
            Attr attrc411 = doc.createAttribute("numero");
            attrc411.setValue("411");
            c411.setAttributeNode(attrc411);
            
            //campo 421 //iva generado de las ventas
            Element c421 = (Element) doc.createElement("campo");
            c421.appendChild(doc.createTextNode(ivaVentas.toString()));
            detalle.appendChild(c421);
            Attr attrc421 = doc.createAttribute("numero");
            attrc421.setValue("421");
            c421.setAttributeNode(attrc421);
            
            //campo 401 //suma de todas las ventas subtotales
            Element c401 = (Element) doc.createElement("campo");
            c401.appendChild(doc.createTextNode(subTotalVentas.toString()));
            detalle.appendChild(c401);
            Attr attrc401 = doc.createAttribute("numero");
            attrc401.setValue("401");
            c401.setAttributeNode(attrc401);

            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            Date now = new Date();
            //nombre del archivo con la fecha actual
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            String strFilename = dateFormat.format(now);
            StreamResult result = new StreamResult(new File("C:\\Users\\Suco\\Desktop\\" + strFilename + ".xml"));

            // Si se quiere mostrar por la consola...
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

    public void modificarFichero() throws TransformerConfigurationException, TransformerException, IOException, SAXException {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/dim/prueba.xml")));

            //Obtenemos todas las habitaciones de la casa
            System.out.println("Documento" + document);
            NodeList listaNodos = document.getDocumentElement().getElementsByTagName("Detalle");
            Node nodo;
            System.out.println("listaNodos " + listaNodos.getLength());
            System.out.println("Antes del 1 for");
            for (int i = 0; i < listaNodos.getLength(); i++) {
                System.out.println("Dentro del 1 for" + i);
                nodo = listaNodos.item(i);

                //Obtenemos una lista de todas las características de cada habitación
                NodeList listaCaracteristicas = nodo.getChildNodes();
                Node caracteristica;

                for (int z = 0; z < listaCaracteristicas.getLength(); z++) {
                    System.out.println("Entro al for");
                    //Obtenemos cada característica individual
                    caracteristica = listaCaracteristicas.item(z);
                    //Si la característica es el color y su valor es azul la eliminamos
                    if (caracteristica.getNodeName().equals("campo") && caracteristica.getTextContent().equals("[102]")) {
                        //El padre del nodo es Habitación y su padre Casa. Eliminamos Habitación de Casa.
                        Element c101 = (Element) document.createElement("campo");
                        c101.appendChild(document.createTextNode("hooooooooola cambie"));
                        Attr attrc101 = document.createAttribute("numero");
                        attrc101.setValue("101");
                        c101.setAttributeNode(attrc101);
                        caracteristica.getParentNode().getParentNode().replaceChild(c101, nodo); //removeChild(caracteristica.getParentNode());
                    }
                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(declaracionesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getMeses() {
        return meses;
    }

    public void setMeses(List<String> meses) {
        this.meses = meses;
    }

    public List<String> getAnos() {
        return anos;
    }

    public void setAnos(List<String> anos) {
        this.anos = anos;
    }

    public String getMesSeleccionado() {
        return mesSeleccionado;
    }

    public void setMesSeleccionado(String mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getSubTotalVentas() {
        return subTotalVentas;
    }

    public void setSubTotalVentas(BigDecimal subTotalVentas) {
        this.subTotalVentas = subTotalVentas;
    }

    public List<Compra> getComprasObtenidas() {
        return comprasObtenidas;
    }

    public void setComprasObtenidas(List<Compra> comprasObtenidas) {
        this.comprasObtenidas = comprasObtenidas;
    }

    public BigDecimal getSubTotalCompras() {
        return subTotalCompras;
    }

    public void setSubTotalCompras(BigDecimal subTotalCompras) {
        this.subTotalCompras = subTotalCompras;
    }

    public BigDecimal getIvaCompras() {
        return ivaCompras;
    }

    public void setIvaCompras(BigDecimal ivaCompras) {
        this.ivaCompras = ivaCompras;
    }

    public BigDecimal getIvaNotas() {
        return ivaNotas;
    }

    public void setIvaNotas(BigDecimal ivaNotas) {
        this.ivaNotas = ivaNotas;
    }

    public BigDecimal getIvaVentas() {
        return ivaVentas;
    }

    public void setIvaVentas(BigDecimal ivaVentas) {
        this.ivaVentas = ivaVentas;
    }

    public Declaraciones getDeclaracionAnterior() {
        return declaracionAnterior;
    }

    public void setDeclaracionAnterior(Declaraciones declaracionAnterior) {
        this.declaracionAnterior = declaracionAnterior;
    }

    public Declaraciones getDeclaraciones() {
        return declaraciones;
    }

    public void setDeclaraciones(Declaraciones declaraciones) {
        this.declaraciones = declaraciones;
    }

    public Boolean getMostrarCampo601() {
        return mostrarCampo601;
    }

    public void setMostrarCampo601(Boolean mostrarCampo601) {
        this.mostrarCampo601 = mostrarCampo601;
    }

    public BigDecimal getIvaFavor() {
        return ivaFavor;
    }

    public void setIvaFavor(BigDecimal ivaFavor) {
        this.ivaFavor = ivaFavor;
    }

    public SistemaMB getSistemaMB() {
        return sistemaMB;
    }

    public void setSistemaMB(SistemaMB sistemaMB) {
        this.sistemaMB = sistemaMB;
    }

}
