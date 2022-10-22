package proje.enoca.controller;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proje.enoca.entity.Worker;
import proje.enoca.services.CompanyService;
import proje.enoca.services.WorkerService;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("api/v2")
public class WorkerController {

    @Autowired
    private final WorkerService workerService;


    @Autowired
    private final CompanyService companyService;

    public WorkerController(WorkerService workerService, CompanyService companyService) {
        this.workerService = workerService;
        this.companyService = companyService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Worker> addWorker(@RequestBody Worker worker,@RequestParam() Long companyId) {

        Worker result = workerService.saveWorker(worker,companyId);
        if (result != null ) {

            return new ResponseEntity<>(result, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




    @DeleteMapping(path = "/deleteworker")
    public ResponseEntity<?> deleteWorker(@RequestParam(required = true) Long workerId) {


        Optional<Worker> deleteWorker = workerService.getIdWorker(workerId);
            if (!deleteWorker.isEmpty()) {
            workerService.deleteWorker(workerId);
            return new ResponseEntity<>("worker silindi", HttpStatus.OK);
        }
        return new ResponseEntity<>("worker yok",HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "/list")
    public List<Worker> getWorkerList() {
        List<Worker> getWorkerList = workerService.getListWorker();
        if (!getWorkerList.isEmpty()) {
            return getWorkerList;

        }
        return null;
    }

    @GetMapping(path = "/getworker")
    public ResponseEntity<Worker> getCompany(@RequestParam() Long workerId) {
        Optional<Worker> worker = workerService.getIdWorker(workerId);

            if (worker.isPresent()) {
                return new ResponseEntity<>(worker.get(), HttpStatus.OK);
            }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "updatecompany/{workerId}")
    public void updateCompany(@PathVariable("workerId") Long workerId,
                              @RequestParam(required =false) String firstName,
                              @RequestParam(required =false) String lastName,
                              @RequestParam(required =false) String departman,
                              @RequestParam(required =false) Integer age,
                              @RequestParam(required =false) Integer salary,
                              @RequestParam(required =false) Long companyId){

        workerService.updateWorker(workerId,firstName,lastName,departman,age,salary,companyId);

    }

    @GetMapping(path = "getSortedSalaryWorkers")
    public ResponseEntity<ArrayList<Worker>> getSortedSalaryWorkers(){
        ArrayList<Worker> workers = (ArrayList<Worker>) workerService.getSalarySortedWorkers();
        return new ResponseEntity<>(workers,HttpStatus.OK);
    }

    @GetMapping(path = "getsortedageworkers")
    public ResponseEntity<ArrayList<Worker>> getSortedAgeWorkers(){
        ArrayList<Worker> workers = (ArrayList<Worker>) workerService.getAgeSortedWorkers();
        return new ResponseEntity<>(workers,HttpStatus.OK);
    }

}
