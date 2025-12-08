package ipss.parcticas.repositorios;

import ipss.parcticas.model.DetallePractica;
import ipss.parcticas.model.Practica;
import ipss.parcticas.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePracticaRepositorio extends JpaRepository<DetallePractica, Long> {

    // Aquí obtengo los detalles de una práctica ordenados cronológicamente
    List<DetallePractica> findByPracticaOrderByFechaAsc(Practica practica);

    // Aquí filtro detalles por estudiante y práctica, también ordenados por fecha
    List<DetallePractica> findByEstudianteAndPracticaOrderByFechaAsc(Estudiante estudiante, Practica practica);
}
