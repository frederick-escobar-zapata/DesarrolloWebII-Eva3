package ipss.parcticas.controladores;

import ipss.parcticas.model.Estudiante;
import ipss.parcticas.model.Usuario;
import ipss.parcticas.repositorios.EstudianteRepositorio;
import ipss.parcticas.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/profesor/estudiantes")
public class EstudianteAdminControlador {

    // Aquí tengo el repositorio de estudiantes, de usuarios y el encoder de contraseñas
    private final EstudianteRepositorio estudianteRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    // En el constructor conecto el controlador con estos tres componentes
    public EstudianteAdminControlador(EstudianteRepositorio estudianteRepositorio,
                                      UsuarioRepositorio usuarioRepositorio,
                                      PasswordEncoder passwordEncoder) {
        this.estudianteRepositorio = estudianteRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    // Aquí muestro todos los estudiantes que puedo gestionar desde el panel del profesor
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteRepositorio.findAll());
        return "profesor/estudiantes-lista";
    }

    // Aquí preparo el formulario vacío para registrar un nuevo estudiante
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "profesor/estudiantes-form";
    }

    // Aquí guardo (creo o actualizo) un estudiante y le genero su usuario si aún no existe
    @PostMapping
    public String guardar(@ModelAttribute Estudiante estudiante) {
        // Primero guardo el estudiante en la tabla estudiante
        Estudiante guardado = estudianteRepositorio.save(estudiante);

        // Luego reviso si ya hay un usuario con ese correo; si no, lo creo
        usuarioRepositorio.findByCorreo(guardado.getCorreo())
                .orElseGet(() -> {
                    Usuario u = new Usuario();
                    // Aquí uso el correo del estudiante como usuario de inicio de sesión
                    u.setCorreo(guardado.getCorreo());
                    // Le asigno el rol ESTUDIANTE para que vaya al panel correspondiente
                    u.setRol("ESTUDIANTE");
                    // Asocio este usuario al estudiante que acabo de guardar
                    u.setEstudiante(guardado);

                    // Aquí aplico la regla de contraseña: apellido paterno + "1234"
                    String apePat = guardado.getApellidoPaterno() != null ? guardado.getApellidoPaterno() : "alumno";
                    String base = apePat.toLowerCase().replace(" ", "");
                    String rawPass = base + "1234";

                    // Encripto la contraseña antes de guardarla
                    u.setContrasena(passwordEncoder.encode(rawPass));

                    return usuarioRepositorio.save(u);
                });

        // Regreso a la lista de estudiantes una vez que termino
        return "redirect:/profesor/estudiantes";
    }

    // Aquí cargo los datos de un estudiante específico para editarlos
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Estudiante est = estudianteRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        model.addAttribute("estudiante", est);
        return "profesor/estudiantes-form";
    }

    // Aquí elimino un estudiante y, antes, también borro su usuario asociado para no romper la FK
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        Estudiante est = estudianteRepositorio.findById(id).orElse(null);
        if (est != null) {
            // Primero elimino el usuario asociado a este estudiante, si lo encuentro
            usuarioRepositorio.findByEstudiante(est)
                    .ifPresent(usuarioRepositorio::delete);

            // Luego elimino el registro del estudiante
            estudianteRepositorio.delete(est);
        }
        return "redirect:/profesor/estudiantes";
    }
}
