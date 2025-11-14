package repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import modelo.Parada;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
    
}