package ipss.parcticas.repositorios;

import ipss.parcticas.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {

    // Aquí busco un estudiante por su RUT
    Optional<Estudiante> findByRut(String rut);

    // Aquí obtengo todos los estudiantes que pertenecen a una carrera específica
    List<Estudiante> findByCarrera(String carrera);
}
