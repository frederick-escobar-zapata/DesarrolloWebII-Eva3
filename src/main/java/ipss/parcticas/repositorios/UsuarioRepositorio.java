package ipss.parcticas.repositorios;

import ipss.parcticas.model.Usuario;
import ipss.parcticas.model.Estudiante;
import ipss.parcticas.model.ProfesorSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // Aquí busco un usuario por su correo (que yo uso como nombre de usuario para el login)
    Optional<Usuario> findByCorreo(String correo);

    // Aquí busco el usuario que está asociado a un estudiante específico
    Optional<Usuario> findByEstudiante(Estudiante estudiante);

    // Aquí busco el usuario que está asociado a un profesor supervisor específico
    Optional<Usuario> findByProfesorSupervisor(ProfesorSupervisor profesorSupervisor);
}
