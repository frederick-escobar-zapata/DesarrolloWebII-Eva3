package ipss.parcticas.controladores;

import ipss.parcticas.model.DetallePractica;
import ipss.parcticas.model.Estudiante;
import ipss.parcticas.model.Practica;
import ipss.parcticas.repositorios.DetallePracticaRepositorio;
import ipss.parcticas.repositorios.EstudianteRepositorio;
import ipss.parcticas.repositorios.PracticaRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiante")
public class EstudianteDetalleControlador {

    // Aquí tengo los repositorios necesarios para cargar estudiante, práctica y sus detalles
    private final EstudianteRepositorio estudianteRepositorio;
    private final PracticaRepositorio practicaRepositorio;
    private final DetallePracticaRepositorio detallePracticaRepositorio;

    // En el constructor amarro el controlador con los repositorios
    public EstudianteDetalleControlador(EstudianteRepositorio estudianteRepositorio,
                                        PracticaRepositorio practicaRepositorio,
                                        DetallePracticaRepositorio detallePracticaRepositorio) {
        this.estudianteRepositorio = estudianteRepositorio;
        this.practicaRepositorio = practicaRepositorio;
        this.detallePracticaRepositorio = detallePracticaRepositorio;
    }

    // Aquí muestro la lista de detalles de una práctica específica para un estudiante
    @GetMapping("/{idEstudiante}/practicas/{idPractica}/detalles")
    public String verDetalles(@PathVariable Long idEstudiante,
                              @PathVariable Long idPractica,
                              Model model) {
        // Primero verifico que el estudiante exista
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        // Luego traigo la práctica asociada
        Practica practica = practicaRepositorio.findById(idPractica)
                .orElseThrow(() -> new IllegalArgumentException("Práctica no encontrada"));

        // Cargo la práctica, el estudiante y la lista de detalles ordenados por fecha
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("practica", practica);
        model.addAttribute("detalles",
                detallePracticaRepositorio.findByPracticaOrderByFechaAsc(practica));

        return "estudiante/detalles-lista";
    }

    // Aquí preparo el formulario para que el estudiante pueda registrar un nuevo detalle de su práctica
    @GetMapping("/{idEstudiante}/practicas/{idPractica}/detalles/nuevo")
    public String nuevoDetalle(@PathVariable Long idEstudiante,
                               @PathVariable Long idPractica,
                               Model model) {
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Practica practica = practicaRepositorio.findById(idPractica)
                .orElseThrow(() -> new IllegalArgumentException("Práctica no encontrada"));

        // Creo un detalle vacío y le asocio la práctica para el binding
        DetallePractica detalle = new DetallePractica();
        detalle.setPractica(practica);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("practica", practica);
        model.addAttribute("detalle", detalle);

        return "estudiante/detalles-form";
    }

    // Aquí guardo el nuevo detalle que el estudiante ingresa
    @PostMapping("/{idEstudiante}/practicas/{idPractica}/detalles")
    public String guardarDetalle(@PathVariable Long idEstudiante,
                                 @PathVariable Long idPractica,
                                 @ModelAttribute("detalle") DetallePractica detalle) {

        // Vuelvo a cargar estudiante y práctica para asegurarme de que existen
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Practica practica = practicaRepositorio.findById(idPractica)
                .orElseThrow(() -> new IllegalArgumentException("Práctica no encontrada"));

        // Aquí asocio correctamente el detalle con el estudiante y la práctica
        detalle.setEstudiante(estudiante);
        detalle.setPractica(practica);

        // Ahora sí guardo el detalle en la base de datos
        detallePracticaRepositorio.save(detalle);

        // Al terminar, vuelvo a la lista de detalles de la misma práctica
        return "redirect:/estudiante/" + idEstudiante + "/practicas/" + idPractica + "/detalles";
    }
}
