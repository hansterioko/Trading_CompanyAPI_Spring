package com.trading_company.trading_company.controllers;

import com.trading_company.trading_company.exeption.CompanyAlreadyExist;
import com.trading_company.trading_company.exeption.CompanyNotFoundExeption;
import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.models.Purchase;
import com.trading_company.trading_company.services.GetValidErrors;
import com.trading_company.trading_company.services.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/purchases")
@CrossOrigin(maxAge = 1600, origins = "*")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @Autowired
    GetValidErrors getValidErrors;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid Purchase purchase, BindingResult bindingResult){
        try{

            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(getValidErrors.getErrors(bindingResult));
            }

            purchaseService.createPurchase(purchase);
            return ResponseEntity.ok("Закупка создана");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok(purchaseService.getOnePurchase(id));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(purchaseService.getAllPurchases(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        try {
            Optional<Purchase> purchase = purchaseService.getOnePurchase(id);

            if (purchase.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            purchaseService.deletePurchase(purchase.get().getId());

            return new ResponseEntity("Закупка удалена", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid Purchase newPurchase){
        try {
            Optional<Purchase> purchase = purchaseService.getOnePurchase(newPurchase.getId());

            if (purchase.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            return new ResponseEntity(purchaseService.updatePurchase(newPurchase), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }
}
