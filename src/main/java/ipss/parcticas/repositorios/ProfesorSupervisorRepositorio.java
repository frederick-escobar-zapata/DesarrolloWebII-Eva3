package ipss.parcticas.repositorios;

import ipss.parcticas.model.ProfesorSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesorSupervisorRepositorio extends JpaRepository<ProfesorSupervisor, Long> {

    // Aquí obtengo todos los profesores supervisores filtrando por carrera
    List<ProfesorSupervisor> findByCarrera(String carrera);
}
