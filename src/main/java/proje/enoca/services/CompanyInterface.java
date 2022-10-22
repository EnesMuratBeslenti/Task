package proje.enoca.services;

import proje.enoca.entity.Company;
import proje.enoca.entity.Worker;

import java.util.List;
import java.util.Optional;

public interface CompanyInterface {

    Company companySave(Company company);
    void companyDelete(Long id);
    void companyUpdate(Long companyId,String companyName,Integer budget,Integer employeeNumbers);
    Optional<Company> companyGetId(Long id);
    List<Company> companyGetList();

    boolean addWorker(Long companyId);
    Optional<Worker> deleteWorker(Long workerId);

    List<Worker> getWorkerByCompany(Long companyId);

    List<Company> getBudgetSortedCompany();

    Integer getExpensesSalary(Long companyId);

    Integer setExpensesCompany(Long companyId);



}
