package proje.enoca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proje.enoca.entity.Company;
import proje.enoca.entity.Worker;
import proje.enoca.repository.CompanyRepository;
import proje.enoca.repository.WorkerRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyService implements CompanyInterface {


    @Autowired
    private final WorkerRepository workerRepository;
    @Autowired
    private final CompanyRepository companyRepository;

    public CompanyService(WorkerRepository workerRepository, CompanyRepository companyRepository) {

        this.workerRepository = workerRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public Company companySave(Company company) {

        Company company1 = new Company();
        company1.setCompanyName(company.getCompanyName());
        company1.setEmployeeNumbers(company.getEmployeeNumbers());
            if (company.getAvabileEmployye() != null && !company.getAvabileEmployye().equals("null")) {
                company1.setAvabileEmployye(company.getAvabileEmployye());
                }else{
                    company1.setAvabileEmployye(0);
                  }
            if ( company.getBudget() == null){
                company1.setBudget(0);
                }else{
                    company1.setBudget(company.getBudget());
                 }

            if (company.getExpenses() == null){
                company1.setExpenses(0);
                }else
                    company1.setExpenses(company.getExpenses());


        companyRepository.save(company1);
        return company1;
    }

    @Override
    public void companyDelete(Long id) {
        boolean exists = companyRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(" " + "Company with id" + id + "does not exists");
        }
        companyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void companyUpdate(Long companyId,
                              String companyName,
                              Integer budget,
                              Integer employeeNumbers) {

        Company company = companyRepository.findById(companyId).
                orElseThrow(() -> new IllegalStateException("Company with id" + companyId + "does not exist"));
        if (companyName != null && !companyName.matches("[a-zA-Z ]*\\d+.*")) {
            company.setCompanyName(companyName);
        }
        if ((employeeNumbers != null)) {
            company.setEmployeeNumbers(employeeNumbers);
        }else company.setEmployeeNumbers(company.getEmployeeNumbers());
        if (budget != null){
            company.setBudget(budget);
        }else
            company.setBudget(company.getBudget());
    }

    @Override
    public Optional<Company> companyGetId(Long workerId) {
        Optional<Company> company = companyRepository.findById(workerId);
        return company.map(Optional::of).orElse(null);
    }

    @Override
    public List<Company> companyGetList() {

        return companyRepository.findAll();
    }

    @Override

    public boolean addWorker(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            if (company.get().getAvabileEmployye() < company.get().getEmployeeNumbers()) {
                company.get().setAvabileEmployye(company.get().getAvabileEmployye() + 1);
                companyRepository.save(company.get());
            }
            return true;
        }
        return false;
    }

    @Override
    public Optional<Worker> deleteWorker(Long workerId) {
        Optional<Worker> worker = workerRepository.findById(workerId);
        if (!worker.isEmpty()) {
            Optional<Company> company = companyRepository.findById(worker.get().getCompany().getCompanyId());
            workerRepository.deleteById(workerId);
            company.get().setAvabileEmployye(company.get().getAvabileEmployye() - 1);
            companyRepository.save(company.get());
        }
        return worker.map(Optional::of).orElse(null);
    }

    @Override
    public List<Worker> getWorkerByCompany(Long companyId) {
        return  workerRepository.findAll().stream()
                .filter(worker -> Objects.nonNull(worker.getCompany()))
                .filter(worker -> Objects.equals(worker.getCompany().getCompanyId(), companyId))
                .collect(Collectors.toList());

    }

    @Override
    public List<Company> getBudgetSortedCompany() {
        return companyRepository.findAll().stream()
                .filter(company ->Objects.nonNull(company.getBudget()))
                .filter(company -> company.getBudget() > 0)
                .sorted(Comparator.comparingInt(Company::getBudget).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Integer getExpensesSalary(Long companyId) {
     Integer expensesSalary=
                    getWorkerByCompany(companyId).stream()
                     .filter(worker -> Objects.nonNull(worker.getSalary()))
                     .filter(worker -> worker.getSalary() >0)
                    .mapToInt(worker -> worker.getSalary()).sum();

        return  expensesSalary;
    }

    @Override
    public Integer setExpensesCompany(Long companyId) {

        return null;
    }


}

