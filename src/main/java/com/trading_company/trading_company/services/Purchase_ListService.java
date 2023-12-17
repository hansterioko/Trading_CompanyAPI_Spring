package com.trading_company.trading_company.services;

import com.trading_company.trading_company.dtos.ProductDTO;
import com.trading_company.trading_company.dtos.Purchase_ListDTO;
import com.trading_company.trading_company.models.Product;
import com.trading_company.trading_company.models.Purchase_List;
import com.trading_company.trading_company.models.Shipment;
import com.trading_company.trading_company.repositories.Purchase_ListRepository;
import com.trading_company.trading_company.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Purchase_ListService {
    @Autowired
    private Purchase_ListRepository purchase_listRepository;
    @Autowired
    private FilesService filesService;

    public Purchase_List createPurchase_List(Purchase_List purchase_list) {

        return purchase_listRepository.save(purchase_list);
    }

    public Optional getOnePurchase_List(int id){
        Optional<Purchase_List> purchase_list = purchase_listRepository.findById(id);

//        if(company == null){
//            throw new CompanyNotFoundExeption("Компания не найдена");
//        }
        return purchase_list;
    }

    public List<Purchase_List> getAllPurchase_List() throws Exception {
        try{
            List<Purchase_List> purchase_lists = (List<Purchase_List>) purchase_listRepository.findAll();
            return purchase_lists;
        }
        catch (Exception e)
        {
            throw new Exception("Произошла ошибка");
        }
    }

    public void deletePurchase_List(int id) throws Exception {
        try {
            purchase_listRepository.deleteById(id);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }

    public Purchase_List updatePurchase_List(Purchase_List purchase_list) throws Exception {
        try{
            purchase_listRepository.save(purchase_list);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
        return purchase_list;
    }

    public List<Purchase_ListDTO> getAllByPurchase(Integer id) throws Exception {
        try{
            List<Purchase_List> purchase_lists = purchase_listRepository.findByPurchaseId(id);
            List<Purchase_ListDTO> newPurchase_Lists = new ArrayList<>();

            for(Purchase_List item: purchase_lists){
                byte[] photo = (filesService.getFile(item.getProduct().getPhoto()));
                ProductDTO newProduct = new ProductDTO(item.getId(), item.getProduct().getName(),
                        item.getProduct().getVat(), item.getProduct().getCategory(),
                        item.getProduct().getCharacteristic(), item.getProduct().getUnit(),
                        item.getProduct().getPrice(), item.getProduct().getCountOnWarehouse(), photo,
                        item.getProduct().getCompany());

                Purchase_ListDTO newPurchase_List = new Purchase_ListDTO(item.getId(), item.getCount(),
                        item.getPurchase(), newProduct);

                newPurchase_Lists.add(newPurchase_List);
            }

            return newPurchase_Lists;
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }
}
