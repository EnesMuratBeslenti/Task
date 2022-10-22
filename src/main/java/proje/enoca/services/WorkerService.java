package proje.enoca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proje.enoca.entity.Company;
import proje.enoca.entity.Worker;
import proje.enoca.repository.CompanyRepository;
import proje.enoca.repository.WorkerRepository;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkerService implements  WorkerInterface{

    @Autowired
    private final WorkerRepository workerRepository;

    @Autowired
    private final CompanyRepository companyRepository;

    @Autowired
    private final CompanyService companyService;

    public WorkerService(WorkerRepository workerRepository, CompanyRepository companyRepository, CompanyService companyService) {
        this.workerRepository = workerRepository;

        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }
    
    @Override
    public Worker   saveWorker(Worker worker,Long companyId) {
        Company company = companyRepository.findById(companyId).
        orElseThrow(() ->
                new IllegalStateException("Company with id" + companyId +"does not exist"));

        workerRepository
                .save(Worker.builder()
                                
                                .firstName(worker.getFirstName())
                                .lastName(worker.getLastName())
                                .departman(worker.getDepartman())
                                .age(worker.getAge())
                                .salary(worker.getSalary())
                                .company(company)
                                .build()
                    );

                company.setExpenses(worker.getSalary()+company.getExpenses());
                companyRepository.save(company);

        return worker;
    }

    @Override
    public void deleteWorker(Long id) {
        boolean exists = workerRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(" " + "Worker with id" + id + "does not exists");
        }
        workerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateWorker(Long workerId,
                             String firstName,
                             String lastName,
                             String departman,
                             Integer age,
                             Integer salary,
                             Long companyId) {

                Worker worker = workerRepository.findById(workerId).
                                orElseThrow(()-> new IllegalStateException("Company with id" + workerId +"does not exist"));
              if (worker != null){
                  if (!firstName.equals("null") && !firstName.matches("[a-zA-Z ]*\\d+.*")){
                      worker.setFirstName(firstName);
                  }else worker.setFirstName(worker.getFirstName());

                  if (!lastName.equals("null") && !lastName.matches("[a-zA-Z ]*\\d+.*")){
                      worker.setLastName(lastName);
                  }
                  else worker.setLastName(worker.getLastName());

                  if (!departman.equals("null") && !departman.matches("[a-zA-Z ]*\\d+.*")){
                      worker.setDepartman(departman);
                  }if (age == null){
                      worker.setAge(worker.getAge());
                  }else {
                      worker.setAge(age);
                  }if (salary == null ){
                      worker.setSalary(worker.getSalary());
                  }else {
                      worker.setSalary(salary);
                  }
                  Company company1 = companyRepository.findById(companyId).
                          orElseThrow(()-> new IllegalStateException("Company with id" + companyId +"does not exist"));
                  if (companyId == null){
                      worker.setCompany(worker.getCompany());
                  }else{
                      worker.setCompany(company1);
                  }

              }
    }

    @Override
    public Optional<Worker> getIdWorker(Long id) {
        Optional<Worker> worker = workerRepository.findById(id);

        return Optional.ofNullable(worker.orElse(null));
    }

    @Override
    public List<Worker> getListWorker() {
        return workerRepository.findAll();
    }

     @Override
     public List<Worker> getSalarySortedWorkers() {
        return  workerRepository.findAll().stream()
                .filter(worker -> Objects.nonNull(worker.getSalary()))
                .filter(worker -> worker.getSalary() > 0)
                .sorted(Comparator.comparingInt(Worker :: getSalary).reversed())
                .collect(Collectors.toList());
     }

    @Override
    public List<Worker> getAgeSortedWorkers() {
        return workerRepository.findAll().stream()
                .filter(worker -> Objects.nonNull(worker.getAge()))
                .sorted(Comparator.comparingInt(Worker::getAge))
                .collect(Collectors.toList());


    }


}
