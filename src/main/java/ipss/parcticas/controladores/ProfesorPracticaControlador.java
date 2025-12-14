package ipss.parcticas.controladores;

import ipss.parcticas.model.Practica;
import ipss.parcticas.model.DetallePractica;
import ipss.parcticas.model.Estudiante;
import ipss.parcticas.repositorios.EmpresaRepositorio;
import ipss.parcticas.repositorios.EstudianteRepositorio;
import ipss.parcticas.repositorios.ProfesorSupervisorRepositorio;
import ipss.parcticas.repositorios.DetallePracticaRepositorio;
import ipss.parcticas.servicios.PracticaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/profesor")
public class ProfesorPracticaControlador {

    // Aquí inyecto todos los componentes que necesito para trabajar con prácticas
    private final PracticaServicio practicaServicio;
    private final EstudianteRepositorio estudianteRepositorio;
    private final EmpresaRepositorio empresaRepositorio;
    private final ProfesorSupervisorRepositorio profesorSupervisorRepositorio;
    private final DetallePracticaRepositorio detallePracticaRepositorio;

    // En este constructor amarro el servicio de práctica y los repositorios de apoyo
    public ProfesorPracticaControlador(PracticaServicio practicaServicio,
                                       EstudianteRepositorio estudianteRepositorio,
                                       EmpresaRepositorio empresaRepositorio,
                                       ProfesorSupervisorRepositorio profesorSupervisorRepositorio,
                                       DetallePracticaRepositorio detallePracticaRepositorio) {
        this.practicaServicio = practicaServicio;
        this.estudianteRepositorio = estudianteRepositorio;
        this.empresaRepositorio = empresaRepositorio;
        this.profesorSupervisorRepositorio = profesorSupervisorRepositorio;
        this.detallePracticaRepositorio = detallePracticaRepositorio;
    }

    // Aquí muestro el panel principal del profesor
    @GetMapping
    public String inicioProfesor() {
        return "profesor/inicio";
    }

    // Aquí redirijo /profesor/nueva a la ruta real del formulario para evitar conflictos con {id}
    @GetMapping("/nueva")
    public String redirigirNueva() {
        return "forward:/profesor/practicas/nueva";
    }

    // Aquí preparo el formulario para crear una nueva práctica
    @GetMapping("/practicas/nueva")
    public String mostrarFormularioNueva(Model model, HttpSession session) {
        String rol = session.getAttribute("rol") != null ? session.getAttribute("rol").toString() : null;
        if (!"PROFESOR".equalsIgnoreCase(rol)) {
            return "redirect:/login?error=true";
        }
        // Creo una práctica vacía y cargo las listas necesarias para el combo
        model.addAttribute("practica", new Practica());
        model.addAttribute("estudiantes", estudianteRepositorio.findAll());
        model.addAttribute("empresas", empresaRepositorio.findAll());
        model.addAttribute("profesores", profesorSupervisorRepositorio.findAll());
        return "profesor/practicas-form";
    }

    // Aquí listo todas las prácticas, con la opción de filtrar por RUT de estudiante
    @GetMapping("/practicas")
    public String listarTodas(@RequestParam(value = "rut", required = false) String rut, Model model) {
        List<Practica> practicas;

        // Si el usuario ingresa un RUT, filtro por ese estudiante
        if (rut != null && !rut.isBlank()) {
            Estudiante est = estudianteRepositorio.findByRut(rut).orElse(null);
            if (est != null) {
                practicas = practicaServicio.listarPorEstudiante(est);
            } else {
                // Si no encuentro estudiante, devuelvo lista vacía
                practicas = List.of();
            }
            model.addAttribute("rutBuscado", rut);
        } else {
            // Si no hay filtro, traigo todas las prácticas
            practicas = practicaServicio.listarTodas();
        }

        model.addAttribute("practicas", practicas);
        return "profesor/practicas-lista";
    }

    // Aquí guardo una práctica nueva o editada, validando los datos del formulario
    @PostMapping("/practicas")
    public String guardar(@Valid @ModelAttribute("practica") Practica practica,
                          BindingResult bindingResult,
                          Model model,
                          HttpSession session) {

        String rol = session.getAttribute("rol") != null ? session.getAttribute("rol").toString() : null;
        if (!"PROFESOR".equalsIgnoreCase(rol)) {
            return "redirect:/login?error=true";
        }

        // Si hay errores de validación, vuelvo a mostrar el formulario con las listas cargadas
        if (bindingResult.hasErrors()) {
            model.addAttribute("estudiantes", estudianteRepositorio.findAll());
            model.addAttribute("empresas", empresaRepositorio.findAll());
            model.addAttribute("profesores", profesorSupervisorRepositorio.findAll());
            return "profesor/practicas-form";
        }

        // Si todo está bien, delego el guardado al servicio de prácticas
        practicaServicio.guardar(practica);
        return "redirect:/profesor/practicas";
    }

    // Aquí muestro el detalle segmentado de una práctica (estudiante, profesor, empresa, etc.)
    @GetMapping("/practicas/{id}")
    public String verPractica(@PathVariable Long id, Model model) {
        Practica practica = practicaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Práctica no encontrada"));

        // Cargo la práctica y sus relaciones para mostrarlas de forma separada en la vista
        model.addAttribute("practica", practica);
        model.addAttribute("estudiante", practica.getEstudiante());
        model.addAttribute("empresa", practica.getEmpresa());
        model.addAttribute("profesor", practica.getProfesorSupervisor());

        return "profesor/practicas-detalle";
    }

    // Aquí preparo el formulario para editar una práctica existente
    @GetMapping("/practicas/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, HttpSession session) {
        String rol = session.getAttribute("rol") != null ? session.getAttribute("rol").toString() : null;
        if (!"PROFESOR".equalsIgnoreCase(rol)) {
            return "redirect:/login?error=true";
        }
        Practica practica = practicaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Práctica no encontrada"));

        // Cargó la práctica y las listas de apoyo para los combos
        model.addAttribute("practica", practica);
        model.addAttribute("estudiantes", estudianteRepositorio.findAll());
        model.addAttribute("empresas", empresaRepositorio.findAll());
        model.addAttribute("profesores", profesorSupervisorRepositorio.findAll());
        return "profesor/practicas-form";
    }

    // Aquí elimino una práctica completa desde el panel del profesor
    @PostMapping("/practicas/{id}/eliminar")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        String rol = session.getAttribute("rol") != null ? session.getAttribute("rol").toString() : null;
        if (!"PROFESOR".equalsIgnoreCase(rol)) {
            return "redirect:/login?error=true";
        }
        practicaServicio.eliminar(id);
        return "redirect:/profesor/practicas";
    }

    // Aquí muestro los detalles (registros diarios) que el estudiante ha reportado para una práctica
    @GetMapping("/practicas/{id}/detalles")
    public String verDetalles(@PathVariable Long id, Model model) {
        Practica practica = practicaServicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Práctica no encontrada"));

        // Traigo los detalles ordenados por fecha para que el profesor revise el avance
        List<DetallePractica> detalles =
                detallePracticaRepositorio.findByPracticaOrderByFechaAsc(practica);

        model.addAttribute("practica", practica);
        model.addAttribute("detalles", detalles);

        return "profesor/detalles-lista";
    }
}
