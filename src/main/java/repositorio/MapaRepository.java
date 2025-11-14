package repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import modelo.Mapa;


public interface MapaRepository extends JpaRepository<Mapa, Long> {
}
