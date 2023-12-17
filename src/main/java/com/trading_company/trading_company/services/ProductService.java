package com.trading_company.trading_company.services;

import com.trading_company.trading_company.dtos.ProductDTO;
import com.trading_company.trading_company.exeption.CompanyNotFoundExeption;
import com.trading_company.trading_company.exeption.CountProductNegativeExeption;
import com.trading_company.trading_company.exeption.ProductNotFoundExeption;
import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.models.Product;
import com.trading_company.trading_company.repositories.CompanyRepository;
import com.trading_company.trading_company.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private FilesService filesService;

    public Product createProduct(Product product, Integer company_id) throws CompanyNotFoundExeption {
        Company company = companyRepository.findById(company_id).get();
        if (company == null){
            throw new CompanyNotFoundExeption("Компания не найдена");
        }
        product.setCompany(company);
        return productRepository.save(product);
    }

    public List<ProductDTO> getAllProduct() throws IOException {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductDTO> newProducts = new ArrayList<>();

        for(Product item: products){
            byte[] photo = (filesService.getFile(item.getPhoto()));
            ProductDTO newProduct = new ProductDTO(item.getId(), item.getName(), item.getVat(), item.getCategory(), item.getCharacteristic(), item.getUnit(), item.getPrice(), item.getCountOnWarehouse(), photo, item.getCompany());

            newProducts.add(newProduct);
        }

        return newProducts;
    }

    public Optional<Product> getOneProduct(Integer id) throws ProductNotFoundExeption {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new ProductNotFoundExeption("Товар не найден");
        }
        return product;
    }

    public Product addCountProduct(Integer id, Integer count) throws ProductNotFoundExeption, CountProductNegativeExeption {
        Product product = productRepository.findById(id).get();
        if (product == null){
            throw new ProductNotFoundExeption("Товар не существует");
        }
        if (count < 1){
            throw new CountProductNegativeExeption("Колличество товара записано неверно!");
        }

        product.setCountOnWarehouse(product.getCountOnWarehouse() + count);

        return productRepository.save(product);
    }

    public Product updateProduct(Product product) throws Exception {
        try{
            productRepository.save(product);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
        return product;
    }

    public void deleteProduct(int id) throws Exception {
        try {
            productRepository.deleteById(id);
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка");
        }
    }

    public List<ProductDTO> getAllByCompany(Integer id) throws Exception {
        try{
            List<Product> products = (List<Product>) productRepository.findByCompanyId(id);
            List<ProductDTO> newProducts = new ArrayList<>();

            for(Product item: products){
                byte[] photo = (filesService.getFile(item.getPhoto()));
                ProductDTO newProduct = new ProductDTO(item.getId(), item.getName(), item.getVat(), item.getCategory(), item.getCharacteristic(), item.getUnit(), item.getPrice(), item.getCountOnWarehouse(), photo, item.getCompany());

                newProducts.add(newProduct);
            }

            return newProducts;
        }
        catch (Exception e)
        {
            throw new Exception("Произошла ошибка");
        }
    }
}
