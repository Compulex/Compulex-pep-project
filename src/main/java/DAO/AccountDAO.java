package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AccountDAO{
    /**
     * add account
     * @param account
     * @return Account 
     */
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
           //sql statement
           String sql = "insert into account(username, password) values(?, ?);"; 
           PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

           //setting preparedStatements String
           preparedStatement.setString(1, account.getUsername());
           preparedStatement.setString(2, account.getPassword());
           
           preparedStatement.executeUpdate();

           //account_id is auto generated
           ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
           if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
        return null;
    }//insertAccount

    public Account getAccountById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //sql statement
            String sql = "select * from account where account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //prepared statement setInt
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
        return null;
    }//getAccountById

    /**
     * check for duplicate usernames, if count for username is one return true
     * @param username
     * @return boolean
     */
    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //sql statement
            String sql = "select * from account where username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //prepared statement setString
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
        return null;
    }
}//end class
