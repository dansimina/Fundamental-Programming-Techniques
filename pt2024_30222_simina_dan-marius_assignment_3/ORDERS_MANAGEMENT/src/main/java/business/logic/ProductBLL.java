package business.logic;

import data.access.ProductDAO;
import data.access.validators.*;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The class implements validation and database operations for the "product" data type.
 */
public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new QuantityValidator());
        validators.add(new PriceValidator());
        productDAO = new ProductDAO();
    }

    /**
     * Retrieve all records from the database.
     *
     * @return a List with all the found records.
     */
    public List<Product> findProductAll() {
        List<Product> products = productDAO.findAll();
        if (products == null) {
            throw new NoSuchElementException("The product table is empty");
        }
        return products;
    }

    /**
     * Retrieve the record with the ID provided as a parameter from the database.
     *
     * @return the object with the entered ID if it exists, otherwise return null.
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }

    /**
     * Apply the validation methods to the received object, and if the object meets all conditions, insert it into the database.
     *
     * @return the object if it has been successfully inserted; otherwise, return null.
     */
    public Product insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        return productDAO.insert(product);
    }

    /**
     * Apply the validation methods to the received object, and if the object meets all conditions, update it in the database.
     *
     * @return the updated object if the update was successful; otherwise, return null.
     */
    public Product updateProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }

        return productDAO.update(product);
    }

    /**
     * Delete the object provided as a parameter from the database.
     *
     * @return the deleted object if the deletion was successful; otherwise, return null.
     */
    public Product deleteProduct(Product product) {
        return productDAO.delete(product);
    }
}
