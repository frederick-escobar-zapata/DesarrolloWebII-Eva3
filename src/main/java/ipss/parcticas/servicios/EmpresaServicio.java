package ipss.parcticas.servicios;

import ipss.parcticas.model.Empresa;
import ipss.parcticas.repositorios.EmpresaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServicio {

    // Aquí inyecto el repositorio de empresas para encapsular la lógica de negocio
    private final EmpresaRepositorio empresaRepositorio;

    // En el constructor conecto el servicio con el repositorio de empresas
    public EmpresaServicio(EmpresaRepositorio empresaRepositorio) {
        this.empresaRepositorio = empresaRepositorio;
    }

    // Aquí devuelvo todas las empresas registradas en el sistema
    public List<Empresa> listarTodas() {
        return empresaRepositorio.findAll();
    }

    // Aquí busco una empresa por su id
    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepositorio.findById(id);
    }

    // Aquí permito buscar empresas por nombre (usando contains/ignore case)
    public List<Empresa> buscarPorNombre(String nombre) {
        return empresaRepositorio.findByNombreContainingIgnoreCase(nombre);
    }

    // Aquí guardo una empresa nueva o actualizada
    public Empresa guardar(Empresa empresa) {
        return empresaRepositorio.save(empresa);
    }

    // Aquí elimino una empresa por su id
    public void eliminarPorId(Long id) {
        empresaRepositorio.deleteById(id);
    }
}
