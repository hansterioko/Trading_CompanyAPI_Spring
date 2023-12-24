package com.trading_company.trading_company.controllers;

import com.trading_company.trading_company.dtos.ProductDTO;
import com.trading_company.trading_company.exeption.CompanyNotFoundExeption;
import com.trading_company.trading_company.exeption.ProductNotFoundExeption;
import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.models.Product;
import com.trading_company.trading_company.services.GetValidErrors;
import com.trading_company.trading_company.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    GetValidErrors getValidErrors;

    @PostMapping("/create")
    public ResponseEntity create(@RequestPart @Valid Product product, @RequestPart(required = false) MultipartFile photo, BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(getValidErrors.getErrors(bindingResult));
            }

            productService.createProduct(product, photo);
            return ResponseEntity.ok("Товар добавлен");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/innerCompany/{id}")
    public ResponseEntity getByCompany(@PathVariable Integer id){
        try{
            return new ResponseEntity(productService.getAllByCompany(id), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(productService.getOneProduct(id));
        }
        catch (ProductNotFoundExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getAll(){
        try {
            return ResponseEntity.ok(productService.getAllProduct());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        try {
            Optional<ProductDTO> product = productService.getOneProduct(id);

            if (product.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            productService.deleteProduct(product.get().getId());

            return new ResponseEntity("Товар удалён", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid Product newproduct){
        try {
            Optional<ProductDTO> product = productService.getOneProduct(newproduct.getId());

            if (product.isEmpty()){
                return ResponseEntity.internalServerError().body("Произошла ошибка");
            }

            return new ResponseEntity(productService.updateProduct(newproduct), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Произошла ошибка");
        }
    }
}
