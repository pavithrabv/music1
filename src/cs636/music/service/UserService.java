package cs636.music.service;

import cs636.music.dao.*;

import cs636.music.domain.*;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.UserData;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by pavithra on 10/27/17.
 */
public class UserService {
    private DownloadDAO downloadDb;
    private InvoiceDAO invoiceDb;
    private LineItemDAO lineItemDb;
    private ProductDAO productDb;
    private UserDAO userDb;

    /**
     * construct a user service provider
     */
    public UserService(DownloadDAO downloadDao, InvoiceDAO invoiceDao, LineItemDAO lineItemDao,
                       ProductDAO productDao, UserDAO userDao) {

        downloadDb = downloadDao;
        invoiceDb = invoiceDao;
        lineItemDb = lineItemDao;
        productDb = productDao;
        userDb = userDao;

    }

    public void registerUser(String firstName, String lastName, String email_address) throws ServiceException{
        User newUser = null;
        try{
            newUser = userDb.findUser(email_address);
            if(newUser == null){
                newUser = new User();
                newUser.setFirstname(firstName);
                newUser.setLastname(lastName);
                newUser.setEmailAddress(email_address);
                userDb.insertUser(newUser);

            }
        }catch (SQLException e){
            throw new ServiceException("Unable to Register User ",  e );
        }


    }

    public UserData getInfoOnUser(String email_address) throws ServiceException{
        User user = null;
        UserData userInfo = null;
        try{
            user = userDb.findUser(email_address);
            userInfo = new UserData(user);
        }catch (SQLException e){
            throw new ServiceException("Unable to get info on User ",  e );
        }
        return userInfo;
    }

    public Cart createNewCart(){
        return new Cart();
    }

    public void addItemsToCart(Cart cart, Product product, int numberOfItems){
        long product_id = product.getId();
        CartItem newItem = cart.findItem(product_id);
        cart.addItem(newItem);
    }

    public void removeItemFromCart(Cart cart, Product product){
        long product_id = product.getId();
        CartItem newItem = cart.findItem(product_id);
        if(newItem != null){
            cart.removeItem(product_id);
        }
    }

    public InvoiceData checkoutCart ()throws ServiceException{
        Invoice invoice = null;
        try{

        }

    }

    public Set<Product> getAllProducts() throws ServiceException{
        Set<Product> listOfProducts;
        try{
            listOfProducts= productDb.listAllProducts();
        }catch (SQLException e){
            throw new ServiceException("Error in displaying list of products ",  e );
        }
        return listOfProducts;
    }

    public Product getProductInfo(String product_code) throws ServiceException{
        Product newProduct = null;
        try{
            newProduct = productDb.findProductByCode(product_code);
        }catch (SQLException e){
            throw new ServiceException("Error in getting Product data ",  e );
        }
        return newProduct;

    }



  }
