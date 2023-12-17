package com.trading_company.trading_company.controllers;

import com.trading_company.trading_company.models.Shipment_List;
import com.trading_company.trading_company.services.Shipment_ListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shipmentLists")
public class Shipment_ListController {
    @Autowired
    Shipment_ListService shipment_listService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Shipment_List shipment_list){
        try{
            shipment_listService.createShipment_List(shipment_list);
            return ResponseEntity.ok("Поставка создана");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok(shipment_listService.getOneShipment_List(id));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(shipment_listService.getAllShipment_List(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        try {
            Optional<Shipment_List> shipment_list = shipment_listService.getOneShipment_List(id);

            if (shipment_list.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            shipment_listService.deleteShipment_List(shipment_list.get().getId());

            return new ResponseEntity("Закупка удалена", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/innerShipment/{id}")
    public ResponseEntity getByShipment(@PathVariable Integer id){
        try{
            return new ResponseEntity(shipment_listService.getAllByShipment(id), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid Shipment_List newShipment_List){
        try {
            Optional<Shipment_List> shipment_list = shipment_listService.getOneShipment_List(newShipment_List.getId());

            if (shipment_list.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            return new ResponseEntity(shipment_listService.updateShipment_List(newShipment_List), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }
}
