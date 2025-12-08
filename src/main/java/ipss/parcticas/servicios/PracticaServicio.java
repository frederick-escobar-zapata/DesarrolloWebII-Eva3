package ipss.parcticas.servicios;

import ipss.parcticas.model.Practica;
import ipss.parcticas.model.Estudiante;
import ipss.parcticas.model.Empresa;
import ipss.parcticas.model.ProfesorSupervisor;
import ipss.parcticas.model.DetallePractica;
import ipss.parcticas.repositorios.PracticaRepositorio;
import ipss.parcticas.repositorios.DetallePracticaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PracticaServicio {

    // Aquí inyecto el repositorio de prácticas y de detalles para centralizar la lógica de negocio
    private final PracticaRepositorio practicaRepositorio;
    private final DetallePracticaRepositorio detallePracticaRepositorio;

    // En el constructor conecto este servicio con los repositorios que necesito
    public PracticaServicio(PracticaRepositorio practicaRepositorio,
                            DetallePracticaRepositorio detallePracticaRepositorio) {
        this.practicaRepositorio = practicaRepositorio;
        this.detallePracticaRepositorio = detallePracticaRepositorio;
    }

    // Aquí obtengo todas las prácticas registradas
    public List<Practica> listarTodas() {
        return practicaRepositorio.findAll();
    }

    // Aquí busco una práctica específica por su id
    public Optional<Practica> buscarPorId(Long id) {
        return practicaRepositorio.findById(id);
    }

    // Aquí traigo todas las prácticas asociadas a un estudiante
    public List<Practica> listarPorEstudiante(Estudiante estudiante) {
        return practicaRepositorio.findByEstudiante(estudiante);
    }

    // Aquí filtro prácticas por empresa
    public List<Practica> listarPorEmpresa(Empresa empresa) {
        return practicaRepositorio.findByEmpresa(empresa);
    }

    // Aquí filtro prácticas por profesor supervisor
    public List<Practica> listarPorProfesor(ProfesorSupervisor profesorSupervisor) {
        return practicaRepositorio.findByProfesorSupervisor(profesorSupervisor);
    }

    // Aquí guardo una práctica nueva o actualizada
    public Practica guardar(Practica practica) {
        return practicaRepositorio.save(practica);
    }

    // Aquí elimino una práctica y, antes, borro sus detalles asociados para no dejar datos huérfanos
    @Transactional
    public void eliminar(Long id) {
        Optional<Practica> opt = practicaRepositorio.findById(id);
        if (opt.isEmpty()) {
            return;
        }

        Practica practica = opt.get();

        // Primero borro los detalles de la práctica
        List<DetallePractica> detalles =
                detallePracticaRepositorio.findByPracticaOrderByFechaAsc(practica);
        if (!detalles.isEmpty()) {
            detallePracticaRepositorio.deleteAll(detalles);
        }

        // Luego borro la práctica principal
        practicaRepositorio.delete(practica);
    }
}
