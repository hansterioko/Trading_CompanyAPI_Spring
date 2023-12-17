package com.trading_company.trading_company.services;

import com.trading_company.trading_company.dtos.ProductDTO;
import com.trading_company.trading_company.dtos.Shipment_ListDTO;
import com.trading_company.trading_company.models.Shipment_List;
import com.trading_company.trading_company.models.Shipment_List;
import com.trading_company.trading_company.repositories.Shipment_ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Shipment_ListService {
    @Autowired
    Shipment_ListRepository shipment_listRepository;
    @Autowired
    FilesService filesService;
    
    public Shipment_List createShipment_List(Shipment_List shipment_list) {

        return shipment_listRepository.save(shipment_list);
    }

    public Optional getOneShipment_List(int id){
        Optional<Shipment_List> shipment_list = shipment_listRepository.findById(id);

//        if(company == null){
//            throw new CompanyNotFoundExeption("Компания не найдена");
//        }
        return shipment_list;
    }

    public List<Shipment_List> getAllShipment_List() throws Exception {
        try{
            List<Shipment_List> shipment_lists = (List<Shipment_List>) shipment_listRepository.findAll();
            
            return shipment_lists;
        }
        catch (Exception e)
        {
            throw new Exception("Произошла ошибка");
        }
    }

    public void deleteShipment_List(int id) throws Exception {
        try {
            shipment_listRepository.deleteById(id);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }

    public Shipment_List updateShipment_List(Shipment_List shipment_list) throws Exception {
        try{
            shipment_listRepository.save(shipment_list);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
        return shipment_list;
    }

    public List<Shipment_ListDTO> getAllByShipment(Integer id) throws Exception {
        try{
            List<Shipment_List> shipment_lists = shipment_listRepository.findByShipmentId(id);
            List<Shipment_ListDTO> newShipment_Lists = new ArrayList<>();

            for(Shipment_List item: shipment_lists){
                byte[] photo = (filesService.getFile(item.getProduct().getPhoto()));
                ProductDTO newProduct = new ProductDTO(item.getId(), item.getProduct().getName(),
                        item.getProduct().getVat(), item.getProduct().getCategory(),
                        item.getProduct().getCharacteristic(), item.getProduct().getUnit(),
                        item.getProduct().getPrice(), item.getProduct().getCountOnWarehouse(), photo,
                        item.getProduct().getCompany());

                Shipment_ListDTO newShipment_List = new Shipment_ListDTO(item.getId(), item.getPrice(), item.getCount(),
                        item.getShipment(), newProduct);

                newShipment_Lists.add(newShipment_List);
            }

            return newShipment_Lists;
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }
}
