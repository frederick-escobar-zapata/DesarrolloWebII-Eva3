package ipss.parcticas.servicios;

import ipss.parcticas.model.Usuario;
import ipss.parcticas.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class AutenticacionServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public AutenticacionServicio(UsuarioRepositorio usuarioRepositorio,
                                 PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> autenticar(String correo, String contrasena) {
        return usuarioRepositorio.findByCorreo(correo)
                .filter(u -> passwordEncoder.matches(contrasena, u.getContrasena()));
    }
}