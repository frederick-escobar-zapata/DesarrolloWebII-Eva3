package ipss.parcticas.repositorios;

import ipss.parcticas.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {

    // Aquí busco empresas cuyo nombre contenga un texto, sin importar mayúsculas/minúsculas
    List<Empresa> findByNombreContainingIgnoreCase(String nombre);
}
