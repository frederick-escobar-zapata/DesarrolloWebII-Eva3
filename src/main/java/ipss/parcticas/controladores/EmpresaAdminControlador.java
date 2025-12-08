package ipss.parcticas.controladores;

import ipss.parcticas.model.Empresa;
import ipss.parcticas.repositorios.EmpresaRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profesor/empresas")
public class EmpresaAdminControlador {

	private final EmpresaRepositorio empresaRepositorio;

	// Aquí inyecto el repositorio de empresas para hacer el CRUD
	public EmpresaAdminControlador(EmpresaRepositorio empresaRepositorio) {
		this.empresaRepositorio = empresaRepositorio;
	}

	// Aquí muestro todas las empresas registradas en el sistema
	@GetMapping
	public String listar(Model model) {
		model.addAttribute("empresas", empresaRepositorio.findAll());
		return "profesor/empresas-lista";
	}

	// Aquí preparo el formulario vacío para crear una nueva empresa
	@GetMapping("/nueva")
	public String nueva(Model model) {
		model.addAttribute("empresa", new Empresa());
		return "profesor/empresas-form";
	}

	// Aquí guardo una empresa nueva o una edición de empresa existente
	@PostMapping
	public String guardar(@ModelAttribute Empresa empresa) {
		empresaRepositorio.save(empresa);
		return "redirect:/profesor/empresas";
	}

	// Aquí cargo una empresa desde la BD para poder editarla
	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		Empresa emp = empresaRepositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
		model.addAttribute("empresa", emp);
		return "profesor/empresas-form";
	}

	// Aquí elimino una empresa por su id
	@PostMapping("/{id}/eliminar")
	public String eliminar(@PathVariable Long id) {
		empresaRepositorio.deleteById(id);
		return "redirect:/profesor/empresas";
	}
}
