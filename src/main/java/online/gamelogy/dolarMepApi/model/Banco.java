package online.gamelogy.dolarMepApi.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL)
    private List<Cotizacion> historialCotizaciones;

    public Banco(String nombre) {
        this.nombre = nombre;
    }
}

