package ipss.parcticas.controladores;

import ipss.parcticas.model.Estudiante;
import ipss.parcticas.model.Practica;
import ipss.parcticas.model.Empresa;
import ipss.parcticas.model.ProfesorSupervisor;
import ipss.parcticas.servicios.PracticaServicio;
import ipss.parcticas.repositorios.EstudianteRepositorio;
import ipss.parcticas.repositorios.EmpresaRepositorio;
import ipss.parcticas.repositorios.ProfesorSupervisorRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class EstudiantePracticaControlador {

    // Aquí necesito el repositorio de estudiantes, el servicio de prácticas y repositorios de apoyo
    private final EstudianteRepositorio estudianteRepositorio;
    private final PracticaServicio practicaServicio;
    private final EmpresaRepositorio empresaRepositorio;
    private final ProfesorSupervisorRepositorio profesorSupervisorRepositorio;

    // En el constructor conecto el controlador con el repositorio y el servicio
    public EstudiantePracticaControlador(EstudianteRepositorio estudianteRepositorio,
                                         PracticaServicio practicaServicio,
                                         EmpresaRepositorio empresaRepositorio,
                                         ProfesorSupervisorRepositorio profesorSupervisorRepositorio) {
        this.estudianteRepositorio = estudianteRepositorio;
        this.practicaServicio = practicaServicio;
        this.empresaRepositorio = empresaRepositorio;
        this.profesorSupervisorRepositorio = profesorSupervisorRepositorio;
    }

    // Aquí muestro todas las prácticas asociadas a un estudiante específico
    @GetMapping("/{idEstudiante}/practicas")
    public String listarPracticasEstudiante(@PathVariable Long idEstudiante, Model model, HttpSession session) {
        // Primero busco al estudiante por su id; si no existe, lanzo excepción
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        // Autorización básica: permitir si es el mismo estudiante o si es profesor
        String rol = session.getAttribute("rol") != null ? session.getAttribute("rol").toString() : null;
        Long sesEstId = session.getAttribute("estudianteId") != null ? (Long) session.getAttribute("estudianteId") : null;
        if (!"PROFESOR".equalsIgnoreCase(rol) && (sesEstId == null || !sesEstId.equals(idEstudiante))) {
            return "redirect:/login?error=true";
        }

        // Luego obtengo todas las prácticas que tiene asignadas ese estudiante
        List<Practica> practicas = practicaServicio.listarPorEstudiante(estudiante);

        // Paso al modelo el estudiante y su lista de prácticas para la vista
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("practicas", practicas);

        return "estudiante/practicas-lista";
    }

    // Mostrar formulario para que un estudiante agregue una nueva práctica
    @GetMapping("/{idEstudiante}/practicas/nueva")
    public String mostrarFormularioNueva(@PathVariable Long idEstudiante, Model model, HttpSession session) {
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        // Sólo el estudiante propietario puede crear prácticas para su ficha
        String rol = session.getAttribute("rol") != null ? session.getAttribute("rol").toString() : null;
        Long sesEstId = session.getAttribute("estudianteId") != null ? (Long) session.getAttribute("estudianteId") : null;
        if (!"ESTUDIANTE".equalsIgnoreCase(rol) || sesEstId == null || !sesEstId.equals(idEstudiante)) {
            return "redirect:/login?error=true";
        }
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("practica", new Practica());
        model.addAttribute("empresas", empresaRepositorio.findAll());
        model.addAttribute("profesores", profesorSupervisorRepositorio.findAll());
        return "estudiante/practicas-form";
    }

    // Guardar práctica creada por el estudiante
    @PostMapping("/{idEstudiante}/practicas")
    public String guardarPracticaEstudiante(@PathVariable Long idEstudiante,
                                            @ModelAttribute("practica") Practica practica,
                                            HttpSession session) {
        // Verificar permiso: sólo el estudiante propietario puede crear
        String rol = session.getAttribute("rol") != null ? session.getAttribute("rol").toString() : null;
        Long sesEstId = session.getAttribute("estudianteId") != null ? (Long) session.getAttribute("estudianteId") : null;
        if (!"ESTUDIANTE".equalsIgnoreCase(rol) || sesEstId == null || !sesEstId.equals(idEstudiante)) {
            return "redirect:/login?error=true";
        }
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        // Aseguramos la asociación con el estudiante
        practica.setEstudiante(estudiante);
        practicaServicio.guardar(practica);

        return "redirect:/estudiante/" + idEstudiante + "/practicas";
    }

    // Ver detalle de la práctica (vista para estudiantes)
    @GetMapping("/{idEstudiante}/practicas/{idPractica}")
    public String verPracticaEstudiante(@PathVariable Long idEstudiante,
                                        @PathVariable Long idPractica,
                                        Model model) {
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        Practica practica = practicaServicio.buscarPorId(idPractica)
                .orElseThrow(() -> new IllegalArgumentException("Práctica no encontrada"));

        // Seguridad simple: verificar que la práctica pertenece al estudiante
        if (!practica.getEstudiante().getId().equals(estudiante.getId())) {
            throw new IllegalArgumentException("Acceso denegado a la práctica");
        }

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("practica", practica);
        model.addAttribute("empresa", practica.getEmpresa());
        model.addAttribute("profesor", practica.getProfesorSupervisor());

        return "estudiante/practicas-detalle";
    }
}
