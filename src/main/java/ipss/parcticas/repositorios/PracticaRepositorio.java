package ipss.parcticas.repositorios;

import ipss.parcticas.model.Practica;
import ipss.parcticas.model.Estudiante;
import ipss.parcticas.model.Empresa;
import ipss.parcticas.model.ProfesorSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PracticaRepositorio extends JpaRepository<Practica, Long> {

    // Aquí obtengo todas las prácticas asociadas a un estudiante
    List<Practica> findByEstudiante(Estudiante estudiante);

    // Aquí filtro prácticas por empresa donde se realizan
    List<Practica> findByEmpresa(Empresa empresa);

    // Aquí filtro prácticas por profesor supervisor responsable
    List<Practica> findByProfesorSupervisor(ProfesorSupervisor profesorSupervisor);

    // Aquí filtro prácticas por estado (En curso, Finalizada, etc.)
    List<Practica> findByEstado(String estado);

    // Aquí obtengo prácticas entre dos fechas de inicio (útil para reportes)
    List<Practica> findByFechaInicioBetween(LocalDate desde, LocalDate hasta);
}
