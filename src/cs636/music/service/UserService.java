package cs636.music.service;

import cs636.music.dao.*;

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

    public void registerUser(String firstName, String lastName, String email) throws ServiceException{

    }


}
