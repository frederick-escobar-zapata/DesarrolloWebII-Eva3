package ipss.parcticas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "detalle_practica")
public class DetallePractica {

    // Aquí guardo el id único de cada registro de detalle
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí indico la fecha en que se realizó este detalle de la práctica
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    // Aquí guardo la descripción de las actividades realizadas ese día
    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion;

    // Aquí guardo las horas trabajadas ese día (puede ser opcional)
    @Column(name = "horas", nullable = true)
    private Integer horas;

    // Aquí enlazo el detalle con la práctica correspondiente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practica_id", nullable = false)
    private Practica practica;

    // Aquí enlazo el detalle con el estudiante que lo registró
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Practica getPractica() {
        return practica;
    }

    public void setPractica(Practica practica) {
        this.practica = practica;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}