package com.trading_company.trading_company.services;

import com.trading_company.trading_company.exeption.CountProductNegativeExeption;
import com.trading_company.trading_company.exeption.ProductNotFoundExeption;
import com.trading_company.trading_company.models.Purchase;
import com.trading_company.trading_company.models.Purchase_List;
import com.trading_company.trading_company.models.Shipment;
import com.trading_company.trading_company.models.Shipment_List;
import com.trading_company.trading_company.repositories.PurchaseRepository;
import com.trading_company.trading_company.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ProductService productService;

    public Shipment createShipment(Shipment shipment) throws CountProductNegativeExeption, ProductNotFoundExeption {
        System.out.println(shipment.getShipment_list());
        for (Shipment_List item:
                shipment.getShipment_list()) {
            item.setShipment(shipment);
            productService.addCountProduct(item.getProduct().getId(), -item.getCount());
        }

        return shipmentRepository.save(shipment);
    }

    public Optional getOneShipment(int id){
        Optional<Shipment> shipment = shipmentRepository.findById(id);

//        if(company == null){
//            throw new CompanyNotFoundExeption("Компания не найдена");
//        }
        return shipment;
    }

    public List<Shipment> getAllShipments() throws Exception {
        try{
            List<Shipment> shipments = (List<Shipment>) shipmentRepository.findAll();
            return shipments;
        }
        catch (Exception e)
        {
            throw new Exception("Произошла ошибка");
        }
    }

    public void deleteShipment(int id) throws Exception {
        try {
            shipmentRepository.deleteById(id);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }

    public Shipment updateShipment(Shipment shipment) throws Exception {
        try{
            shipmentRepository.save(shipment);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
        return shipment;
    }
}
