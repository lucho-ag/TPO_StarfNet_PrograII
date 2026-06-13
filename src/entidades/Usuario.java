package entidades;

import estructurasTDA.PilaCambios;
import estructurasTDA.IColaUsuarios;
import estructurasTDA.ColaUsuarios;

public class Usuario {
    private int id;
    private String nombreUsuario;
    private String nombre;
    private String email;
    private String contrasenia;
    private String profesion;
    private String ciudad;
    private String resumen;
    private String rol;
    private boolean activo;
    private PilaCambios historial;
    private IColaUsuarios solicitudesPendientes;
    private Habilidad[] misHabilidades = new Habilidad[15];
    private int cantHabilidades = 0;

    public Usuario(int id, String nombreUsuario, String nombre, String email, String contrasenia, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.activo = true;
        this.historial = new PilaCambios();
        this.solicitudesPendientes = new ColaUsuarios();
    }

    public boolean agregarHabilidad(Habilidad hab) {
        for(int i=0; i<cantHabilidades; i++){
            if (misHabilidades[i].getId() == hab.getId()) {
                return false;
            }
        }
        if (cantHabilidades < 15) {
            misHabilidades[cantHabilidades++] = hab;
            return true;
        }
        return false;
    }

    public String obtenerHabilidadesString() {
        if (cantHabilidades == 0) return "Ninguna habilidad cargada.";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cantHabilidades; i++) {
            sb.append(misHabilidades[i].getNombre()).append(", ");
        }
        return sb.toString();
    }

    public int getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }
    public PilaCambios getHistorial() { return historial; }
    public IColaUsuarios getSolicitudesPendientes() { return solicitudesPendientes; }
    public String getProfesion() { return profesion != null ? profesion : "-"; }
    public String getCiudad() { return ciudad != null ? ciudad : "-"; }
    public String getResumen() { return resumen != null ? resumen : "-"; }

    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setProfesion(String profesion) { this.profesion = profesion; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setResumen(String resumen) { this.resumen = resumen; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public boolean validarContrasenia(String intento) {
        return this.contrasenia.equals(intento);
    }

    @Override
    public String toString() {
        return "================================\n" +
                "  Usuario  : @" + nombreUsuario + "\n" +
                "  Nombre   : " + nombre + "\n" +
                "  Profesión: " + getProfesion() + "\n" +
                "  Habilidades: " + obtenerHabilidadesString() + "\n" +
                "  Ciudad   : " + getCiudad() + "\n" +
                "  Resumen  : " + getResumen() + "\n" +
                "  Rol      : " + rol + "\n" +
                "  Estado   : " + (activo ? "Activo" : "Inactivo") + "\n" +
                "================================";
    }
}