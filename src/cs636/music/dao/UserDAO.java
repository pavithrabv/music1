package cs636.music.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import static cs636.music.dao.DBConstants.*;

import cs636.music.domain.User;


/**
 * Created by pavithra on 10/27/17.
 */
public class UserDAO {

    private Connection connection;

    public UserDAO(DbDAO db)  {
        connection = db.getConnection();
    }

    /**
     * Get the available user id
     * @return the user id available
     * @throws SQLException
     */

    private int findNextUserId() throws SQLException
    {
        int nextUserID;
        Statement stmt = connection.createStatement();
        try {
            ResultSet set = stmt.executeQuery(" select user_id from " + SYS_TABLE);
            set.next();
            nextUserID = set.getInt("user_id");
        } finally {
            stmt.close();
        }
        advanceUserID(); // the id has been taken, so set +1 for next one
        return nextUserID;
    }

    /**
     * Increase user_id by 1 in the system table
     * @throws SQLException
     */

    private void advanceUserID() throws SQLException
    {
        Statement stmt = connection.createStatement();
        try {
            stmt.executeUpdate(" update " + SYS_TABLE + " set user_id = user_id + 1");
        } finally {
            stmt.close();
        }
    }

    /**
     * Insert user data into site_user table
     * @param user
     * @throws SQLException
     */


    public void insertUser(User user)throws SQLException{
        Statement stmt = connection.createStatement();
        long userID =  findNextUserId();
        user.setId(userID);

        try {
            String sqlString = "insert into " + USER_TABLE +" (user_id, firstname, lastname, email_address) VALUES ( " +
                    user.getId() + ", " + user.getFirstname() + " , " + user.getLastname() + " , " +
                    user.getEmailAddress() + ")";
            stmt.execute(sqlString);

        }finally {
            stmt.close();
        }
    }

    public User findUser (String email) throws SQLException{
        User user = null;
        Statement stmt = connection.createStatement();
        try {
            String sqlString = " select * from " + USER_TABLE + " where email_address = " + email;
            ResultSet set = stmt.executeQuery(sqlString);

            if (set.next()) {
                user = new User();
                user.setId(set.getLong("user_id"));
                user.setFirstname(set.getString("firstname"));
                user.setLastname(set.getString("lastname"));
                user.setEmailAddress(set.getString("email_address"));
                set.close();
            }
        }
        finally {
            stmt.close();
        }

            return user;
        }


    public User findUserByID(int user_id) throws SQLException{
        User user = null;
        Statement stmt = connection.createStatement();
        try {
            String sqlString = " select * from " + USER_TABLE + " where user_id = " + user_id;
            ResultSet set = stmt.executeQuery(sqlString);

            if (set.next()) {
                user = new User();
                user.setId(set.getLong("user_id"));
                user.setFirstname(set.getString("firstname"));
                user.setLastname(set.getString("lastname"));
                user.setEmailAddress(set.getString("email_address"));
                set.close();
            }
        }
        finally {
            stmt.close();
        }

        return user;
    }
}
