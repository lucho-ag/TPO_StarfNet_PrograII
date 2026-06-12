package entidades;

public class Habilidad {
    private int id;
    private String nombre;
    private String categoria;

    public Habilidad(int id, String nombre, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }

    @Override
    public String toString() {
        return "[" + categoria + "] " + nombre;
    }
}
