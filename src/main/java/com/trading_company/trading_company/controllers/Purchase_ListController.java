package com.trading_company.trading_company.controllers;

import com.trading_company.trading_company.models.Purchase_List;
import com.trading_company.trading_company.services.Purchase_ListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/purchaseLists")
public class Purchase_ListController {
    @Autowired
    Purchase_ListService purchase_listService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Purchase_List purchase_list){
        try{
            purchase_listService.createPurchase_List(purchase_list);
            return ResponseEntity.ok("Закупка создана");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok(purchase_listService.getOnePurchase_List(id));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(purchase_listService.getAllPurchase_List(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        try {
            Optional<Purchase_List> purchase_list = purchase_listService.getOnePurchase_List(id);

            if (purchase_list.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            purchase_listService.deletePurchase_List(purchase_list.get().getId());

            return new ResponseEntity("Закупка удалена", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/innerPurchase/{id}")
    public ResponseEntity getByPurchase(@PathVariable Integer id){
        try{
            return new ResponseEntity(purchase_listService.getAllByPurchase(id), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid Purchase_List newPurchase_List){
        try {
            Optional<Purchase_List> purchase_list = purchase_listService.getOnePurchase_List(newPurchase_List.getId());

            if (purchase_list.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            return new ResponseEntity(purchase_listService.updatePurchase_List(newPurchase_List), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }
}
