package cs636.music.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import cs636.music.dao.*;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;

/**
 * 
 * Provide admin level services to user app through accessing DAOs 
 * @author Chung-Hsien (Jacky) Yu
 */
public class AdminService {
	
	private DbDAO db;
	private AdminDAO adminDb;
	private DownloadDAO downloadDb;
	private InvoiceDAO invoiceDb;
	private LineItemDAO lineItemDb;
	private ProductDAO productDb;



	/**
	 * construct a admin service provider 
	 * @param dbDao
	 */
	public AdminService(DbDAO dbDao, AdminDAO adminDao, DownloadDAO downloadDao, InvoiceDAO invoiceDao,
						LineItemDAO lineItemDao, ProductDAO productDao ) {
		db = dbDao;	
		adminDb = adminDao;
		downloadDb = downloadDao;
		invoiceDb = invoiceDao;
		lineItemDb = lineItemDao;
		productDb = productDao;

	}
	
	/**
	 * Clean all user table, not product and system table to empty
	 * and then set the index numbers back to 1
	 * @throws ServiceException
	 */
	public void initializeDB()throws ServiceException {
		try {
			db.initializeDb();
		} catch (SQLException e)
		{
			throw new ServiceException("Can't initialize DB: ", e);
		}	
	}
	
	/**
	 * process the invoice
	 * @param invoiceId
	 * @throws ServiceException
	 */
	public void processInvoice(long invoiceId) throws ServiceException {
		System.out.println("TEMP: processing invoice");
		try{
			Invoice invoice = invoiceDb.findInvoice(invoiceId);
			invoiceDb.updateInvoice(invoice);
		}catch (SQLException e ){
			throw new ServiceException("Error processing invoice: ", e);
		}

	}

	/**
	 * Get a list of all invoices
	 * @return list of all invoices in InvoiceData objects
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofInvoices() throws ServiceException {
		System.out.println("TEMP: getting invoices");
		Set <Invoice> allInvoices = null;
		try{
			allInvoices = invoiceDb.findAllInvoices();
		}catch (SQLException e ){
			throw new ServiceException("Error getting list of invoices: ", e);
		}
		Set<InvoiceData> AllInvoicesList = new HashSet<InvoiceData>();
		for(Invoice currentInvoice: allInvoices){
			AllInvoicesList.add(new InvoiceData(currentInvoice));
		}
		return AllInvoicesList;
	}
	
	/**
	 * Get a list of all unprocessed invoices
	 * @return list of all unprocessed invoices in InvoiceData objects
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofUnprocessedInvoices() throws ServiceException {
		System.out.println("TEMP: getting unprocessed invoices");
		Set <Invoice> allUnprocessedInvoices = null;
		try{
			allUnprocessedInvoices = invoiceDb.findAllUnprocessedInvoices();
		}catch (SQLException e ){
			throw new ServiceException("Error getting list of Unprocessed invoices: ", e);
		}
		Set<InvoiceData> AllUnprocessedInvoicesList = new HashSet<InvoiceData>();
		for(Invoice currentInvoice: allUnprocessedInvoices){
			AllUnprocessedInvoicesList.add(new InvoiceData(currentInvoice));
		}
		return AllUnprocessedInvoicesList;
	}
	
	/**
	 * Get a list of all downloads
	 * @return list of all downloads
	 * @throws ServiceException
	 */
	public Set<DownloadData> getListofDownloads() throws ServiceException {
		System.out.println("TEMP: getting downloads");
		Set <Download> allDownloads = null;
		try{
			allDownloads = downloadDb.findAllDownloads();
		}catch (SQLException e ){
			throw new ServiceException("Error getting list of Downloads: ", e);
		}
		Set<DownloadData> AllDownloadsList = new HashSet<DownloadData>();
		for(Download currentDownload: allDownloads){
			AllDownloadsList.add(new DownloadData(currentDownload));
		}
		return AllDownloadsList;
	}
	
	
	/**
	 * Check login user
	 * @param username
	 * @param password
	 * @return true if useranme and password exist, otherwise return false
	 * @throws ServiceException
	 */
	public Boolean checkLogin(String username,String password) throws ServiceException {
		try {
			return adminDb.findAdminUser(username, password);
		} catch (SQLException e)
		{
			throw new ServiceException("Check login error: ", e);
		}
	}
	
}
