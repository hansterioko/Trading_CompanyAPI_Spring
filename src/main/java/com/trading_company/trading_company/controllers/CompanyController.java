package com.trading_company.trading_company.controllers;

import com.trading_company.trading_company.exeption.CompanyAlreadyExist;
import com.trading_company.trading_company.exeption.CompanyNotFoundExeption;
import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.services.CompanyService;
import com.trading_company.trading_company.services.GetValidErrors;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    GetValidErrors getValidErrors;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid Company company, BindingResult bindingResult){
        try{

            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(getValidErrors.getErrors(bindingResult));
            }

            companyService.createCompany(company);
            return ResponseEntity.ok("Компания создана");
        }
        catch (CompanyAlreadyExist e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok(companyService.getOneCompany(id));
        }
        catch (CompanyNotFoundExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        try {
            Optional<Company> company = companyService.getOneCompany(id);

            if (company.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            companyService.deleteCompany(company.get().getId());

            return new ResponseEntity("Компания удалена", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid Company newcompany){
        try {
            Optional<Company> company = companyService.getOneCompany(newcompany.getId());

            if (company.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            return new ResponseEntity(companyService.updateCompany(newcompany), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }
}
