package repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import modelo.Monopatin;
import modelo.Parada;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
    public List<Monopatin>getMonopatinesAll();
}