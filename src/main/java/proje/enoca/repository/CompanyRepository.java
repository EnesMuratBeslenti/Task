package proje.enoca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proje.enoca.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

}
