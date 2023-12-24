package com.trading_company.trading_company.services;

import com.trading_company.trading_company.exeption.CompanyAlreadyExist;
import com.trading_company.trading_company.exeption.CompanyNotFoundExeption;
import com.trading_company.trading_company.exeption.CountProductNegativeExeption;
import com.trading_company.trading_company.exeption.ProductNotFoundExeption;
import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.models.Purchase;
import com.trading_company.trading_company.models.Purchase_List;
import com.trading_company.trading_company.repositories.CompanyRepository;
import com.trading_company.trading_company.repositories.ProductRepository;
import com.trading_company.trading_company.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductService productService;

    public Purchase createPurchase(Purchase purchase) throws CountProductNegativeExeption, ProductNotFoundExeption {
        for (Purchase_List item:
             purchase.getPurchase_list()) {
            item.setPurchase(purchase);
            productService.addCountProduct(item.getProduct().getId(), item.getCount());
        }

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
//            List<Purchase> purchases = (List<Purchase>) purchaseRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<Purchase> purchases = (List<Purchase>) purchaseRepository.findAllByOrderByDateDesc();
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
