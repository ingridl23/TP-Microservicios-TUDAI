package repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import modelo.Monopatin;


public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
}