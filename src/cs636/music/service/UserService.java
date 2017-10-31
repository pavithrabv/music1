package cs636.music.service;

import cs636.music.dao.*;

import cs636.music.domain.*;
import cs636.music.service.data.UserData;

import java.sql.SQLException;

/**
 * Created by pavithra on 10/27/17.
 */
public class UserService {
    //private DbDAO db;
    //private AdminDAO adminDb;
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
        User newuser = null;
        try{
            newuser = userDb.findUser(email_address);
            if(newuser == null){
                newuser = new User;
                newuser.setFirstname(firstName);
                newuser.setLastname(lastName);
                newuser.setEmailAddress(email_address);
                userDb.insertUser(newuser);

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

    public Cart addLineItemsToCart(Cart cart, Product product, int numberOfItems){
        long product_id = product.getId();
        LineItem item = cart.findItem(product_id);
        if(item != null){
            int quantity = item.getQuantity();
            item.setQuantity(quantity + numberOfItems);
        }else {
            item = new LineItem();

        }



    }



    }
