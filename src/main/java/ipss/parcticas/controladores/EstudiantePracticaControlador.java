package ipss.parcticas.controladores;

import ipss.parcticas.model.Estudiante;
import ipss.parcticas.model.Practica;
import ipss.parcticas.servicios.PracticaServicio;
import ipss.parcticas.repositorios.EstudianteRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class EstudiantePracticaControlador {

    // Aquí necesito el repositorio de estudiantes y el servicio de prácticas
    private final EstudianteRepositorio estudianteRepositorio;
    private final PracticaServicio practicaServicio;

    // En el constructor conecto el controlador con el repositorio y el servicio
    public EstudiantePracticaControlador(EstudianteRepositorio estudianteRepositorio,
                                         PracticaServicio practicaServicio) {
        this.estudianteRepositorio = estudianteRepositorio;
        this.practicaServicio = practicaServicio;
    }

    // Aquí muestro todas las prácticas asociadas a un estudiante específico
    @GetMapping("/{idEstudiante}/practicas")
    public String listarPracticasEstudiante(@PathVariable Long idEstudiante, Model model) {
        // Primero busco al estudiante por su id; si no existe, lanzo excepción
        Estudiante estudiante = estudianteRepositorio.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        // Luego obtengo todas las prácticas que tiene asignadas ese estudiante
        List<Practica> practicas = practicaServicio.listarPorEstudiante(estudiante);

        // Paso al modelo el estudiante y su lista de prácticas para la vista
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("practicas", practicas);

        return "estudiante/practicas-lista";
    }
}
