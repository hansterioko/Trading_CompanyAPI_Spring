package com.trading_company.trading_company.controllers;

import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.models.Shop;
import com.trading_company.trading_company.services.GetValidErrors;
import com.trading_company.trading_company.services.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shops")
public class ShopController {
    @Autowired
    ShopService shopService;
    
    @Autowired
    GetValidErrors getValidErrors;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid Shop shop, BindingResult bindingResult){
        try{

            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(getValidErrors.getErrors(bindingResult));
            }

            shopService.createShop(shop);
            return ResponseEntity.ok("Закупка создана");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok(shopService.getOneShop(id));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(shopService.getAllShop(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        try {
            Optional<Shop> shop = shopService.getOneShop(id);

            if (shop.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            shopService.deleteShop(shop.get().getId());

            return new ResponseEntity("Закупка удалена", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid Shop newShop){
        try {
            Optional<Shop> shop = shopService.getOneShop(newShop.getId());

            if (shop.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            return new ResponseEntity(shopService.updateShop(newShop), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }
}
