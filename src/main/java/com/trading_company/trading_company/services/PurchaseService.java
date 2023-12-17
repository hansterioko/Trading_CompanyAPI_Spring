package com.trading_company.trading_company.services;

import com.trading_company.trading_company.exeption.CompanyAlreadyExist;
import com.trading_company.trading_company.exeption.CompanyNotFoundExeption;
import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.models.Purchase;
import com.trading_company.trading_company.repositories.CompanyRepository;
import com.trading_company.trading_company.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public Purchase createPurchase(Purchase purchase) {

        return purchaseRepository.save(purchase);
    }

    public Optional getOnePurchase(int id){
        Optional<Purchase> purchase = purchaseRepository.findById(id);

//        if(company == null){
//            throw new CompanyNotFoundExeption("Компания не найдена");
//        }
        return purchase;
    }

    public List<Purchase> getAllPurchases() throws Exception {
        try{
            List<Purchase> purchases = (List<Purchase>) purchaseRepository.findAll();
            return purchases;
        }
        catch (Exception e)
        {
            throw new Exception("Произошла ошибка");
        }
    }

    public void deletePurchase(int id) throws Exception {
        try {
            purchaseRepository.deleteById(id);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }

    public Purchase updatePurchase(Purchase purchase) throws Exception {
        try{
            purchaseRepository.save(purchase);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
        return purchase;
    }
}
