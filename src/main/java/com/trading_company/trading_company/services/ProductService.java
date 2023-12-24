package com.trading_company.trading_company.services;

import com.trading_company.trading_company.dtos.ProductDTO;
import com.trading_company.trading_company.exeption.CountProductNegativeExeption;
import com.trading_company.trading_company.exeption.ProductNotFoundExeption;
import com.trading_company.trading_company.models.Product;
import com.trading_company.trading_company.repositories.CompanyRepository;
import com.trading_company.trading_company.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Product createProduct(Product product, MultipartFile photo) throws IOException {
        if (photo != null){

            String destinationPath = "src/main/resources/static/images/" + getPhotoName();

            product.setPhoto(destinationPath);
            filesService.saveImage(photo, destinationPath);
        }
        else{
            product.setPhoto("src/main/resources/static/images/noPhoto.jpeg");
        }

        return productRepository.save(product);
    }

    public String getPhotoName() {
        List<Product> productsList = productRepository.findAllByOrderByIdDesc();
        String namePhoto = "photo0";

        if (!productsList.isEmpty()){
            for (Product products:
                 productsList) {

                if (!products.getPhoto().equals("src/main/resources/static/images/noPhoto.jpeg")) {

                    Integer idPhoto = Integer.valueOf(products.getPhoto().substring(38, products.getPhoto().lastIndexOf('.'))) + 1;
                    namePhoto = "photo" + idPhoto;
                    break;

                }
            }
        }


        return namePhoto + ".jpeg";
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

    public Optional<ProductDTO> getOneProduct(Integer id) throws ProductNotFoundExeption, IOException {
        Optional<Product> product = productRepository.findById(id);

        byte[] photo = (filesService.getFile(product.get().getPhoto()));
        Optional<ProductDTO> newProduct = Optional.of(new ProductDTO(product.get().getId(), product.get().getName(), product.get().getVat(), product.get().getCategory(),
                product.get().getCharacteristic(), product.get().getUnit(), product.get().getPrice(),
                product.get().getCountOnWarehouse(), photo, product.get().getCompany()));
        
        if (product.isEmpty()){
            throw new ProductNotFoundExeption("Товар не найден");
        }
        return newProduct;
    }

    public Product addCountProduct(Integer id, Integer count) throws ProductNotFoundExeption, CountProductNegativeExeption {
        Product product = productRepository.findById(id).get();
        if (product == null){
            throw new ProductNotFoundExeption("Товар не существует");
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
            Product product = productRepository.findById(id).get();

            if (!product.getPhoto().equals("src/main/resources/static/images/noPhoto.jpeg"))
            filesService.deleteFile(product.getPhoto());
            productRepository.delete(product);
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
