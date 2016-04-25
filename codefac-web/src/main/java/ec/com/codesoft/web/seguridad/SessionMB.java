/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.seguridad;

import ec.com.codesoft.model.Configuracion;
import ec.com.codesoft.model.Perfil;
import ec.com.codesoft.model.Usuario;
import ec.com.codesoft.modelo.servicios.SeguridadServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transaction;
import javax.websocket.Session;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class SessionMB implements Serializable {

    /**
     * Configuraciones del sistema
     */
    private Configuracion configuracion;
    /**
     * Nick del usuario que intenta loguearse
     */
    private String nick;
    /**
     * Clave del usuario que intenta loguearse
     */
    private String clave;

    /**
     * Variable que me permite obtener el nombre del perfil
     */
    private String perfilNombre;
    /**
     * Referencia al objeto que se encuentra logueado en el sistema
     */
    private Usuario usuarioLogin;

    /**
     * Objeto que contiene el perfil del usuario logueado
     */
    private Perfil perfilBuscado;

    private String urlSonido = "http://api.voicerss.org/?key=3edcc88b461e4b94a0473bc28382ba91&src=Bienvenido a Codefac &hl=es-mx";

    private Session session;
    private Transaction transaccion;

    @EJB
    private SeguridadServicio seguridadServicio;

    private int x=12;
    private int y=10;

    public SessionMB() {
        HttpSession miSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        miSession.setMaxInactiveInterval(7200);
    }

    @PostConstruct
    private void postConstruct() {
        usuarioLogin = new Usuario();
    }

    public String iniciarSesion() {
        System.out.println("verificando inicio session ...");
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioAux = seguridadServicio.loguear(nick, clave);
        //System.out.println("Entro");
        if (usuarioAux != null) {
            //System.out.println("Usuario encontrado");
            //System.out.println(usuarioAux.getNick());

            this.usuarioLogin = usuarioAux;

            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            httpSession.setAttribute("usuario", this.usuarioLogin);

            System.out.println(perfilNombre);
            perfilBuscado = usuarioLogin.buscarPerfil(perfilNombre);

            //cuando no tiene permisos para entrar en el perfil
            if (perfilBuscado == null) {
                this.nick = "";
                this.clave = "";
                context.addMessage(null, new FacesMessage("No tiene permisos para acceder al perfil", "!Vuelva a intentar!"));
                return "login";
            } else {
                context.addMessage(null, new FacesMessage("Bienvenido al Sistema Codefac", "!Buen dia!"));
                httpSession.setAttribute("perfil", this.perfilBuscado);
                //cuando el perfil encontrado existe dirige a la pagina correspondiente
                switch (perfilBuscado.getTipo()) {
                    case "admin":
                        System.out.println("accediendo al administrador");
                        return "/admin/indexAdmin.xhtml";

                    case "operador":
                        return "/operador/index.xhtml";

                    case "empleado":
                        return "/empleado/indexEmpleado.xhtml";
                }

            }

        } else {
            this.nick = "";
            this.clave = "";
            context.addMessage(null, new FacesMessage("Los datos ingresados son incorrectos", "!Vuelva a intentar!"));
            return "login";

        }
        return "login";
    }

    public String cerrarSesion() {
        System.out.println("cerrar sesion");
        this.nick = null;
        this.clave = null;

        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.invalidate();

        return "/login.xhtml";
    }

    public void cambiarSonido() {
        //urlSonido = "http://api.voicerss.org/?key=3edcc88b461e4b94a0473bc28382ba91&src=no podemos ver el partido de barcelona &hl=es-mx";
        RequestContext.getCurrentInstance().execute("alert('que mas ve')");
    }

    public void cambiarSonido2() {
        urlSonido = "http://api.voicerss.org/?key=3edcc88b461e4b94a0473bc28382ba91&src=ya estamos viendo el partido de barcelona &hl=es-mx";
    }

    public void submit() {
        System.out.println("x:");
        System.out.println("y:");
    }

    public Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public SeguridadServicio getSeguridadServicio() {
        return seguridadServicio;
    }

    public void setSeguridadServicio(SeguridadServicio seguridadServicio) {
        this.seguridadServicio = seguridadServicio;
    }

    public String getPerfilNombre() {
        return perfilNombre;
    }

    public void setPerfilNombre(String perfilNombre) {
        this.perfilNombre = perfilNombre;
    }

    public Perfil getPerfilBuscado() {
        return perfilBuscado;
    }

    public void setPerfilBuscado(Perfil perfilBuscado) 
    {
        this.perfilBuscado = perfilBuscado;
    }

    public String getUrlSonido() {
        return urlSonido;
    }

    public void setUrlSonido(String urlSonido) {
        this.urlSonido = urlSonido;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    

}
