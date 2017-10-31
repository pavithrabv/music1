package cs636.music.dao;

import cs636.music.domain.Product;
import cs636.music.domain.Track;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;




import static cs636.music.dao.DBConstants.*;


/**
 * Created by pavithra on 10/27/17.
 */
public class ProductDAO {
    private Connection connection;

    public ProductDAO(DbDAO db) {
        connection = db.getConnection();

    }

    public Product findProductByCode(String productCode) throws SQLException {
        Product product = null;
        Statement stmt = connection.createStatement();
        try {
            String sqlString = " select * from " + PRODUCT_TABLE + " P , " + TRACK_TABLE + " T " +
                    " where T.product_id = P.product_id and P.product_code = " + productCode + " order by T.track_number";
            ResultSet set = stmt.executeQuery(sqlString);
            if (set.next()) {// if the result is not empty
                product = new Product(set.getInt("product_id"),set.getString("product_code"),
                        set.getString("product_description"), set.getBigDecimal("product_price"), null);
                Set<Track> productTracks = new HashSet<Track>();
                Track newTrack = new Track();
                newTrack.setId(set.getInt("track_id"));
                newTrack.setProduct(product);
                newTrack.setTrackNumber(set.getInt("track_number"));
                newTrack.setTitle(set.getString("title"));
                newTrack.setSampleFilename(set.getString("sample_filename"));

                productTracks.add(newTrack);

                while (set.next()) {
                    newTrack = new Track();
                    newTrack.setId(set.getInt("track_id"));
                    newTrack.setProduct(product);
                    newTrack.setTrackNumber(set.getInt("track_number"));
                    newTrack.setTitle(set.getString("title"));
                    newTrack.setSampleFilename(set.getString("sample_filename"));

                    productTracks.add(newTrack);
                }
                product.setTracks(productTracks);
            }
            set.close();
        }finally {
            stmt.close();
        }
        return product;
    }




    public Product findProductByPID(int product_id) throws SQLException{
        Product product = null;
        Statement stmt = connection.createStatement();
        try {
            String sqlString = " select * from " + PRODUCT_TABLE + " P , " + TRACK_TABLE + " T " +
                    " where T.product_id = P.product_id and P.product_id = " + product_id + " order by T.track_number";
            ResultSet set = stmt.executeQuery(sqlString);
            if (set.next()) {// if the result is not empty
                product = new Product(set.getInt("product_id"), set.getString("product_code"),
                        set.getString("product_description"), set.getBigDecimal("product_price"), null);
                Set<Track> productTracks = new HashSet<Track>();
                Track newTrack = new Track();
                newTrack.setId(set.getInt("track_id"));
                newTrack.setProduct(product);
                newTrack.setTrackNumber(set.getInt("track_number"));
                newTrack.setTitle(set.getString("title"));
                newTrack.setSampleFilename(set.getString("sample_filename"));

                productTracks.add(newTrack);

                while (set.next()) {
                    newTrack = new Track();
                    newTrack.setId(set.getInt("track_id"));
                    newTrack.setProduct(product);
                    newTrack.setTrackNumber(set.getInt("track_number"));
                    newTrack.setTitle(set.getString("title"));
                    newTrack.setSampleFilename(set.getString("sample_filename"));

                    productTracks.add(newTrack);
                }
                product.setTracks(productTracks);
            }
            set.close();
        }finally {
            stmt.close();
        }
        return product;
        }

    public Track findTrackByTID(int track_id) throws SQLException{
        Product product ;
        Track tracks = null;
        Statement stmt = connection.createStatement();
        try {
            String sqlString = " select * from " + PRODUCT_TABLE + " P , " + TRACK_TABLE + " T where " +
                    "T.product_id = P.product_id and T.track_id = " + track_id + " order by T.track_number";
            ResultSet set = stmt.executeQuery(sqlString);
            if (set.next()) {
                product = this.findProductByPID(set.getInt("product_id"));
                if(product != null){
                    tracks = product.findTrackbyID(track_id);
                }
            }
            set.close();

    }finally {
            stmt.close();
        }
        return tracks;
        }
}


