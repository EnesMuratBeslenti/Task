package proje.enoca.services;

import proje.enoca.entity.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerInterface {

    Worker saveWorker(Worker worker,Long companyId);
    void deleteWorker(Long id);
    void updateWorker(Long workerId,String firstName,String lastName,String departman,Integer age,Integer salary,Long companyId);
    Optional<Worker> getIdWorker(Long workerId);
    List<Worker> getListWorker();
    List<Worker> getSalarySortedWorkers();

    List<Worker> getAgeSortedWorkers();

}
