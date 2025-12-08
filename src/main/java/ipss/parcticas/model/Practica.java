package ipss.parcticas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "practica")
public class Practica {

    // Aquí guardo el id único de la práctica
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí defino la fecha de inicio de la práctica, la marco como obligatoria
    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    // Aquí defino la fecha de término de la práctica, también obligatoria
    @NotNull
    @Column(name = "fecha_termino", nullable = false)
    private LocalDate fechaTermino;

    // Aquí describo brevemente la práctica
    @NotBlank
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    // Aquí registro el estado actual de la práctica (por ejemplo: En curso, Finalizada, etc.)
    @NotBlank
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    // Aquí enlazo la práctica con el estudiante que la realiza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    // Aquí enlazo la práctica con la empresa donde se realiza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    // Aquí enlazo la práctica con el profesor supervisor responsable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_supervisor_id", nullable = false)
    private ProfesorSupervisor profesorSupervisor;

    // Aquí guardo el nombre del jefe directo en la empresa
    @NotBlank
    @Column(name = "nombre_jefe_directo", nullable = false, length = 255)
    private String nombreJefeDirecto;

    // Aquí guardo el correo de contacto del jefe directo
    @NotBlank
    @Column(name = "correo_jefe_directo", nullable = false, length = 255)
    private String correoJefeDirecto;

    // Aquí guardo el teléfono del jefe directo
    @NotBlank
    @Column(name = "telefono_jefe_directo", nullable = false, length = 50)
    private String telefonoJefeDirecto;

    // Aquí puedo registrar un resumen de las actividades realizadas en la práctica
    @Column(name = "actividades_realizadas", length = 1000)
    private String actividadesRealizadas;

    // Aquí puedo guardar observaciones que el estudiante quiera dejar sobre la práctica
    @Column(name = "observaciones_estudiante", length = 1000)
    private String observacionesEstudiante;

    // Aquí tengo la lista de detalles diarios asociados a esta práctica
    @OneToMany(mappedBy = "practica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePractica> detalles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreJefeDirecto() {
        return nombreJefeDirecto;
    }

    public void setNombreJefeDirecto(String nombreJefeDirecto) {
        this.nombreJefeDirecto = nombreJefeDirecto;
    }

    public String getCorreoJefeDirecto() {
        return correoJefeDirecto;
    }

    public void setCorreoJefeDirecto(String correoJefeDirecto) {
        this.correoJefeDirecto = correoJefeDirecto;
    }

    public String getTelefonoJefeDirecto() {
        return telefonoJefeDirecto;
    }

    public void setTelefonoJefeDirecto(String telefonoJefeDirecto) {
        this.telefonoJefeDirecto = telefonoJefeDirecto;
    }

    public String getActividadesRealizadas() {
        return actividadesRealizadas;
    }

    public void setActividadesRealizadas(String actividadesRealizadas) {
        this.actividadesRealizadas = actividadesRealizadas;
    }

    public String getObservacionesEstudiante() {
        return observacionesEstudiante;
    }

    public void setObservacionesEstudiante(String observacionesEstudiante) {
        this.observacionesEstudiante = observacionesEstudiante;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ProfesorSupervisor getProfesorSupervisor() {
        return profesorSupervisor;
    }

    public void setProfesorSupervisor(ProfesorSupervisor profesorSupervisor) {
        this.profesorSupervisor = profesorSupervisor;
    }

    public List<DetallePractica> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePractica> detalles) {
        this.detalles = detalles;
    }
}
