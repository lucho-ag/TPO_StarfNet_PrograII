package entidades;

public class PerfilEstado {
    private String nombre;
    private String profesion;
    private String ciudad;
    private String resumen;

    public PerfilEstado(String nombre, String profesion,
                        String ciudad, String resumen) {
        this.nombre = nombre;
        this.profesion = profesion;
        this.ciudad = ciudad;
        this.resumen = resumen;
    }

    // Getters para todos los campos
    public String getNombre() { return nombre; }
    public String getProfesion() { return profesion; }
    public String getCiudad() { return ciudad; }
    public String getResumen() { return resumen; }
}
