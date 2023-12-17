package com.trading_company.trading_company.services;

import com.trading_company.trading_company.exeption.CompanyAlreadyExist;
import com.trading_company.trading_company.exeption.CompanyNotFoundExeption;
import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.models.Purchase;
import com.trading_company.trading_company.models.Shop;
import com.trading_company.trading_company.repositories.CompanyRepository;
import com.trading_company.trading_company.repositories.PurchaseRepository;
import com.trading_company.trading_company.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    public Shop createShop(Shop shop) {

        return shopRepository.save(shop);
    }

    public Optional getOneShop(int id){
        Optional<Shop> shop = shopRepository.findById(id);

//        if(company == null){
//            throw new CompanyNotFoundExeption("Компания не найдена");
//        }
        return shop;
    }

    public List<Shop> getAllShop() throws Exception {
        try{
            List<Shop> shops = (List<Shop>) shopRepository.findAll();
            return shops;
        }
        catch (Exception e)
        {
            throw new Exception("Произошла ошибка");
        }
    }

    public void deleteShop(int id) throws Exception {
        try {
            shopRepository.deleteById(id);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }

    public Shop updateShop(Shop shop) throws Exception {
        try{
            shopRepository.save(shop);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
        return shop;
    }
}
