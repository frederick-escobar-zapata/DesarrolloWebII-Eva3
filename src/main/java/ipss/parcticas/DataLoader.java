package ipss.parcticas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import ipss.parcticas.model.Estudiante;
import ipss.parcticas.model.Empresa;
import ipss.parcticas.model.ProfesorSupervisor;
import ipss.parcticas.model.Usuario;
import ipss.parcticas.repositorios.EstudianteRepositorio;
import ipss.parcticas.repositorios.EmpresaRepositorio;
import ipss.parcticas.repositorios.ProfesorSupervisorRepositorio;
import ipss.parcticas.repositorios.UsuarioRepositorio;

@Component
public class DataLoader implements CommandLineRunner {

    private final EstudianteRepositorio estudianteRepositorio;
    private final EmpresaRepositorio empresaRepositorio;
    private final ProfesorSupervisorRepositorio profesorRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(EstudianteRepositorio estudianteRepositorio,
                      EmpresaRepositorio empresaRepositorio,
                      ProfesorSupervisorRepositorio profesorRepositorio,
                      UsuarioRepositorio usuarioRepositorio,
                      PasswordEncoder passwordEncoder) {
        this.estudianteRepositorio = estudianteRepositorio;
        this.empresaRepositorio = empresaRepositorio;
        this.profesorRepositorio = profesorRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // No sobrescribir si ya existen usuarios
        if (usuarioRepositorio.count() > 0) {
            return;
        }

        // Crear una empresa de ejemplo
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa Demo S.A.");
        empresa.setDireccion("Av. Ejemplo 123");
        empresa.setTelefono("+56912345678");
        empresa.setCorreo("contacto@empresa-demo.cl");
        empresa = empresaRepositorio.save(empresa);

        // Crear un estudiante de ejemplo
        Estudiante est = new Estudiante();
        est.setNombres("Juan");
        est.setApellidoPaterno("Perez");
        est.setApellidoMaterno("Lopez");
        est.setCarrera("Técnico en Informática");
        est.setRut("12345678-9");
        est.setCorreo("juan@example.com");
        est.setTelefono("+56911111111");
        est = estudianteRepositorio.save(est);

        // Crear un profesor supervisor de ejemplo
        ProfesorSupervisor prof = new ProfesorSupervisor();
        prof.setNombres("María");
        prof.setApellidoPaterno("Gómez");
        prof.setApellidoMaterno("Ríos");
        prof.setCarrera("Técnico en Informática");
        prof.setCorreo("maria@example.com");
        prof.setTelefono("+56922222222");
        prof = profesorRepositorio.save(prof);

        // Crear usuarios (con contraseña 'pass123')
        Usuario uEst = new Usuario();
        uEst.setCorreo("juan@example.com");
        uEst.setContrasena(passwordEncoder.encode("pass123"));
        uEst.setRol("ESTUDIANTE");
        uEst.setEstudiante(est);
        usuarioRepositorio.save(uEst);

        Usuario uProf = new Usuario();
        uProf.setCorreo("maria@example.com");
        uProf.setContrasena(passwordEncoder.encode("pass123"));
        uProf.setRol("PROFESOR");
        uProf.setProfesorSupervisor(prof);
        usuarioRepositorio.save(uProf);
    }
}
