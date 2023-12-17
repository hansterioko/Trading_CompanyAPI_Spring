package com.trading_company.trading_company.services;

import com.trading_company.trading_company.exeption.CompanyAlreadyExist;
import com.trading_company.trading_company.exeption.CompanyNotFoundExeption;
import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) throws CompanyAlreadyExist {
            if (companyRepository.findByName(company.getName()) != null){
                throw new CompanyAlreadyExist("Компания с таким названием уже существует");
            }
            return companyRepository.save(company);
    }

    public Optional getOneCompany(int id) throws CompanyNotFoundExeption {
        Optional<Company> company = companyRepository.findById(id);

//        if(company == null){
//            throw new CompanyNotFoundExeption("Компания не найдена");
//        }
        return company;
    }

    public List<Company> getAllCompanies() throws Exception {
        try{
            List<Company> companies = (List<Company>) companyRepository.findAll();
            return companies;
        }
        catch (Exception e)
        {
            throw new Exception("Произошла ошибка");
        }
    }

    public void deleteCompany(int id) throws Exception {
        try {
            companyRepository.deleteById(id);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }

    public Company updateCompany(Company company) throws Exception {
        try{
            companyRepository.save(company);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
        return company;
    }
}
