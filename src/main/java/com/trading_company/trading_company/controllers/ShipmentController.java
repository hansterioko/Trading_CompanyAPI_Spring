package com.trading_company.trading_company.controllers;

import com.trading_company.trading_company.models.Shipment;
import com.trading_company.trading_company.services.GetValidErrors;
import com.trading_company.trading_company.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {
    @Autowired
    ShipmentService shipmentService;

    @Autowired
    GetValidErrors getValidErrors;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid Shipment shipment, BindingResult bindingResult){
        try{

            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(getValidErrors.getErrors(bindingResult));
            }

            shipmentService.createShipment(shipment);
            return ResponseEntity.ok("Поставка создана");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok(shipmentService.getOneShipment(id));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(shipmentService.getAllShipments(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        try {
            Optional<Shipment> shipment = shipmentService.getOneShipment(id);

            if (shipment.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            shipmentService.deleteShipment(shipment.get().getId());

            return new ResponseEntity("Поставка удалена", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid Shipment newShipment, BindingResult bindingResult){
        try {
            Optional<Shipment> shipment = shipmentService.getOneShipment(newShipment.getId());

            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(getValidErrors.getErrors(bindingResult));
            }

            if (shipment.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            return new ResponseEntity(shipmentService.updateShipment(newShipment), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }
}
