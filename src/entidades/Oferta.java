package entidades;
import estructurasTDA.IColaUsuarios;
import estructurasTDA.ColaUsuarios;

public class Oferta {
    private int id;
    private int idReclutador;
    private String titulo;
    private String descripcion;
    private String habilidadesRequeridas;
    private String estado;
    private IColaUsuarios colaPostulantes;

    public Oferta(int id, int idReclutador, String titulo,
                  String descripcion, String habilidadesRequeridas) {
        this.id = id;
        this.idReclutador = idReclutador;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.habilidadesRequeridas = habilidadesRequeridas;
        this.estado = "ACTIVA";
        this.colaPostulantes = new ColaUsuarios();
    }

    public int getId() { return id; }
    public int getIdReclutador() { return idReclutador; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getHabilidadesRequeridas() { return habilidadesRequeridas; }
    public String getEstado() { return estado; }
    public IColaUsuarios getColaPostulantes() { return colaPostulantes; }
    public void cerrar() { this.estado = "CERRADA"; }

    @Override
    public String toString() {
        return "[" + id + "] " + titulo +
                " | Habilidades: " + habilidadesRequeridas +
                " | Estado: " + estado;
    }
}