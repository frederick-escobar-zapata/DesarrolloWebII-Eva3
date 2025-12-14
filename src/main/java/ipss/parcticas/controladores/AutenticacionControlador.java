package ipss.parcticas.controladores;

import ipss.parcticas.model.Usuario;
import ipss.parcticas.servicios.AutenticacionServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AutenticacionControlador {

    // Aquí tengo el servicio que valida usuario y contraseña contra la BD
    private final AutenticacionServicio autenticacionServicio;

    public AutenticacionControlador(AutenticacionServicio autenticacionServicio) {
        this.autenticacionServicio = autenticacionServicio;
    }

    // Aquí redirijo la raíz de la app directamente al formulario de login
    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    // Aquí muestro el formulario de login y, si viene un error, agrego el mensaje al modelo
    @GetMapping("/login")
    public String mostrarFormularioLogin(@RequestParam(value = "error", required = false) String error,
                                         Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
        }
        // Esta vista corresponde al archivo login.html
        return "login";
    }

    // Aquí proceso el submit del login y decido a dónde envío al usuario según su rol
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String contrasena,
                                HttpSession session) {

        // Primero intento autenticar al usuario con el servicio
        Optional<Usuario> usuarioOpt = autenticacionServicio.autenticar(correo, contrasena);

        // Si las credenciales son incorrectas, vuelvo al login con error
        if (usuarioOpt.isEmpty()) {
            return "redirect:/login?error=true";
        }

        Usuario usuario = usuarioOpt.get();

        // Guardamos info básica en sesión para controlar permisos en los controladores
        session.setAttribute("rol", usuario.getRol());
        if ("ESTUDIANTE".equalsIgnoreCase(usuario.getRol())) {
            Long idEstudiante = usuario.getEstudiante() != null ? usuario.getEstudiante().getId() : null;
            if (idEstudiante != null) {
                session.setAttribute("estudianteId", idEstudiante);
                return "redirect:/estudiante/" + idEstudiante + "/practicas";
            }
            // Si por alguna razón no tiene estudiante asociado, considero esto un error
            return "redirect:/login?error=true";
        } else if ("PROFESOR".equalsIgnoreCase(usuario.getRol())) {
            Long idProfesor = usuario.getProfesorSupervisor() != null ? usuario.getProfesorSupervisor().getId() : null;
            if (idProfesor != null) {
                session.setAttribute("profesorId", idProfesor);
            }
            // Si es profesor, lo mando al panel principal de profesor
            return "redirect:/profesor";
        }

        // Si el rol no es reconocido, lo trato como login inválido
        return "redirect:/login?error=true";
    }
}
