package atm.appatmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import atm.appatmservice.entity.Banknote;

import java.util.Collection;
import java.util.List;

@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Integer> {
    List<Banknote> findAllByIdIn(Collection<Integer> id);
    Banknote findByValue(Integer value);
}
