package ipss.parcticas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    // Aquí guardo el id único del usuario en la tabla usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí uso el correo como nombre de usuario para el login
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 255)
    private String correo;

    // Aquí almaceno la contraseña encriptada (BCrypt)
    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    // Aquí defino el rol del usuario (ESTUDIANTE o PROFESOR) para decidir el panel
    @Column(name = "rol", nullable = false, length = 50)
    private String rol;

    // Relación con Estudiante: si el usuario es estudiante, enlazo su ficha acá
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    // Relación con ProfesorSupervisor: si el usuario es profesor, enlazo su ficha acá
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_supervisor_id")
    private ProfesorSupervisor profesorSupervisor;

    // A partir de aquí dejo solo getters y setters sin más comentarios
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public ProfesorSupervisor getProfesorSupervisor() {
        return profesorSupervisor;
    }

    public void setProfesorSupervisor(ProfesorSupervisor profesorSupervisor) {
        this.profesorSupervisor = profesorSupervisor;
    }
}