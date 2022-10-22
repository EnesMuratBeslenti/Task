package proje.enoca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proje.enoca.entity.Company;
import proje.enoca.entity.Worker;
import proje.enoca.services.CompanyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class CompanyController {

    @Autowired
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping(path = "/add")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        Company result = companyService.companySave(company);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/deletecompany/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable("companyId") Long companyId) {

        Optional<Company> deleteCompany = companyService.companyGetId(companyId);
        if (deleteCompany != null) {
            companyService.companyDelete(companyId);
            return new ResponseEntity<>("company silindi", HttpStatus.OK);
        }
        return new ResponseEntity<>("companyId bulunamadı",HttpStatus.BAD_REQUEST);


    }

    @GetMapping(path = "/list")
    public List<Company> getCompanyList() {
        List<Company> getCompanyList = companyService.companyGetList();
        if (!getCompanyList.isEmpty()) {
            return getCompanyList;
        }
        return null;
    }

    @GetMapping(path = "getcompany/{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable("companyId") Long companyId) {
        Optional<Company> company = companyService.companyGetId(companyId);
        if (company != null) {
                return new ResponseEntity<>(company.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "updatecompany/")
    public void updateCompany( @RequestParam("companyId") Long companyId,
                               @RequestParam(required =true) String companyName,
                               @RequestParam(required =true) Integer budget,
                               @RequestParam(required =true) Integer employeeNumbers){
        companyService.companyUpdate(companyId,companyName,budget,employeeNumbers);

    }

    @DeleteMapping(path = "/deleteCompanyInWorker/{workerId}")
    public ResponseEntity<?> deleteCompanyInWorker(@PathVariable("workerId") Long workerId) {
        Optional<Worker> worker = companyService.deleteWorker(workerId);

        if (worker != null){
            return new ResponseEntity<>("worker delete in company", HttpStatus.OK);
        } else
            return new ResponseEntity<>("worker is not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/getworkerbycompany")
    public  ResponseEntity<ArrayList<Worker>>getCompanyList(@RequestParam Long companyId) {
        ArrayList<Worker> workers = (ArrayList<Worker>) companyService.getWorkerByCompany(companyId);
        return  new ResponseEntity<>(workers, HttpStatus.OK);

    }

    @GetMapping(path = "/getsortedbudgetcompany")
    public ResponseEntity<ArrayList<Company>> getSortedBudgetCompany(){

        ArrayList<Company> companies = (ArrayList<Company>) companyService.getBudgetSortedCompany();
            return new ResponseEntity<>(companies,HttpStatus.OK);



    }
    @GetMapping(path = "getexpensessalary")
    public ResponseEntity<Integer> getexpensessalary(@RequestParam Long companyId){

        return new ResponseEntity<>(companyService.getExpensesSalary(companyId),HttpStatus.OK);

    }


}

