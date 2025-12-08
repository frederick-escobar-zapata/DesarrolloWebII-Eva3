package ipss.parcticas.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "profesor_supervisor")
public class ProfesorSupervisor {

    // Aquí guardo el id único del profesor supervisor
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí separo los nombres del profesor (uno o varios)
    @Column(name = "nombres", nullable = false, length = 255)
    private String nombres;

    // Aquí guardo el apellido paterno del profesor
    @Column(name = "apellido_paterno", nullable = false, length = 255)
    private String apellidoPaterno;

    // Aquí guardo el apellido materno del profesor
    @Column(name = "apellido_materno", nullable = false, length = 255)
    private String apellidoMaterno;

    // Aquí indico la carrera o área a la que pertenece el profesor
    @Column(name = "carrera", nullable = false, length = 255)
    private String carrera;

    // Aquí guardo el correo de contacto del profesor
    @Column(name = "correo", nullable = false, length = 255)
    private String correo;

    // Aquí guardo el teléfono del profesor
    @Column(name = "telefono", nullable = false, length = 255)
    private String telefono;

    // Aquí tengo la lista de prácticas donde este profesor actúa como supervisor
    @OneToMany(mappedBy = "profesorSupervisor", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Practica> practicas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // Aquí construyo el nombre completo del profesor a partir de los tres campos
    @Transient
    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        if (nombres != null) sb.append(nombres);
        if (apellidoPaterno != null) sb.append(" ").append(apellidoPaterno);
        if (apellidoMaterno != null) sb.append(" ").append(apellidoMaterno);
        return sb.toString().trim();
    }
}