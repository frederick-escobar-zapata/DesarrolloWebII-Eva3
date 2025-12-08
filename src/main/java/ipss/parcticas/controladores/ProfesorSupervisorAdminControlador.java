package ipss.parcticas.controladores;

import ipss.parcticas.model.ProfesorSupervisor;
import ipss.parcticas.model.Usuario;
import ipss.parcticas.repositorios.ProfesorSupervisorRepositorio;
import ipss.parcticas.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/profesor/profesores")
public class ProfesorSupervisorAdminControlador {

    // Aquí inyecto los repositorios y el encoder para poder gestionar profesores y sus usuarios
    private final ProfesorSupervisorRepositorio profesorSupervisorRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    // En el constructor amarro las dependencias que necesito para este CRUD
    public ProfesorSupervisorAdminControlador(ProfesorSupervisorRepositorio profesorSupervisorRepositorio,
                                              UsuarioRepositorio usuarioRepositorio,
                                              PasswordEncoder passwordEncoder) {
        this.profesorSupervisorRepositorio = profesorSupervisorRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    // Aquí muestro la lista completa de profesores supervisores para gestionarlos desde el panel del profesor
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profesores", profesorSupervisorRepositorio.findAll());
        return "profesor/profesores-lista";
    }

    // Aquí preparo el formulario vacío para crear un nuevo profesor supervisor
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("profesor", new ProfesorSupervisor());
        return "profesor/profesores-form";
    }

    // Aquí guardo (creo o actualizo) un profesor y, si no existe, le creo también su usuario con rol PROFESOR
    @PostMapping
    public String guardar(@ModelAttribute ProfesorSupervisor profesor) {
        // Primero guardo el profesor en la tabla profesor_supervisor
        ProfesorSupervisor guardado = profesorSupervisorRepositorio.save(profesor);

        // Luego, si todavía no hay un usuario con ese correo, lo creo automáticamente
        usuarioRepositorio.findByCorreo(guardado.getCorreo())
                .orElseGet(() -> {
                    Usuario u = new Usuario();
                    // Aquí uso el correo del profesor como nombre de usuario para el login
                    u.setCorreo(guardado.getCorreo());
                    // Aquí fijo el rol PROFESOR para que el sistema redirija al panel de profesor
                    u.setRol("PROFESOR");
                    // Aquí vinculo este usuario con el profesor que acabo de guardar
                    u.setProfesorSupervisor(guardado);

                    // Aquí genero la contraseña base usando el apellido paterno + "1234"
                    // Ejemplo: Escobar -> escobar1234
                    String apePat = guardado.getApellidoPaterno() != null ? guardado.getApellidoPaterno() : "profesor";
                    String base = apePat.toLowerCase().replace(" ", "");
                    String rawPass = base + "1234";

                    // Aquí encripto la contraseña antes de guardarla en la BD
                    u.setContrasena(passwordEncoder.encode(rawPass));

                    return usuarioRepositorio.save(u);
                });

        // Cuando termino, vuelvo a la lista de profesores
        return "redirect:/profesor/profesores";
    }

    // Aquí cargo un profesor por id para mostrarlo en el formulario de edición
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        ProfesorSupervisor p = profesorSupervisorRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"));
        model.addAttribute("profesor", p);
        return "profesor/profesores-form";
    }

    // Aquí me encargo de eliminar un profesor y también su usuario asociado para no romper la FK
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        // Primero busco el profesor; si existe, sigo con la eliminación
        ProfesorSupervisor p = profesorSupervisorRepositorio.findById(id).orElse(null);
        if (p != null) {
            // Aquí busco el usuario asociado a este profesor y lo elimino si existe
            usuarioRepositorio.findByProfesorSupervisor(p)
                    .ifPresent(usuarioRepositorio::delete);

            // Finalmente elimino el registro del profesor
            profesorSupervisorRepositorio.delete(p);
        }
        // Después de borrar, regreso a la lista de profesores
        return "redirect:/profesor/profesores";
    }
}
