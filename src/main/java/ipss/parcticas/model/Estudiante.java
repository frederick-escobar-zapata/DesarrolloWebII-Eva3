package ipss.parcticas.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "estudiante")
public class Estudiante {

    // Aquí guardo el id único del estudiante
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí almaceno los nombres del estudiante
    @Column(name = "nombres", nullable = false, length = 255)
    private String nombres;

    // Aquí almaceno el apellido paterno del estudiante
    @Column(name = "apellido_paterno", nullable = false, length = 255)
    private String apellidoPaterno;

    // Aquí almaceno el apellido materno del estudiante
    @Column(name = "apellido_materno", nullable = false, length = 255)
    private String apellidoMaterno;

    // Aquí guardo la carrera que estudia el alumno
    @Column(name = "carrera", nullable = false, length = 255)
    private String carrera;

    // Aquí guardo el RUT del estudiante
    @Column(name = "rut", nullable = false, length = 255)
    private String rut;

    // Aquí guardo el correo de contacto del estudiante
    @Column(name = "correo", nullable = false, length = 255)
    private String correo;

    // Aquí guardo el teléfono del estudiante
    @Column(name = "telefono", nullable = false, length = 255)
    private String telefono;

    // Aquí tengo la lista de prácticas asociadas al estudiante
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Practica> practicas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Aquí construyo el nombre completo del estudiante a partir de los tres campos
    @Transient
    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        if (nombres != null) sb.append(nombres);
        if (apellidoPaterno != null) sb.append(" ").append(apellidoPaterno);
        if (apellidoMaterno != null) sb.append(" ").append(apellidoMaterno);
        return sb.toString().trim();
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Practica> getPracticas() {
        return practicas;
    }

    public void setPracticas(List<Practica> practicas) {
        this.practicas = practicas;
    }
}