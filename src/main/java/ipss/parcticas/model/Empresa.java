package ipss.parcticas.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {

    // Aquí guardo el id único de la empresa
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aquí guardo el nombre de la empresa
    @Column(nullable = false, length = 255)
    private String nombre;

    // Aquí guardo la dirección física de la empresa
    @Column(nullable = false, length = 255)
    private String direccion;

    // Aquí guardo el teléfono de contacto de la empresa
    @Column(nullable = false, length = 255)
    private String telefono;

    // Aquí guardo el correo de contacto de la empresa
    @Column(nullable = false, length = 255)
    private String correo;

    // Aquí mantengo la lista de prácticas asociadas a esta empresa
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Practica> practicas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Practica> getPracticas() {
        return practicas;
    }

    public void setPracticas(List<Practica> practicas) {
        this.practicas = practicas;
    }
}