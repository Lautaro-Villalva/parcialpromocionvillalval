package icop.parcialpromocionvillalval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import icop.parcialpromocionvillalval.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {


    boolean existsByTituloLibroAndDevueltoFalse(String tituloLibro);
}
