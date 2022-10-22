package proje.enoca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proje.enoca.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Long> {


}
