package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    /**
     * add a new message
     * @param message
     * @return Message
     */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //sql statement
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //setting preparedStatements int string long
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            //message_id is auto generated
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
        return null;
    }//insertMessage
    
    /**
     * returns all the messages from the database
     * @return List<messages>
     */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            //sql statement
            String sql = "select * from message;";
            PreparedStatement  preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
        return messages;
    }//getAllMessages

    /**
     * returns a message by id
     * @param message_id
     * @return message object
     */
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //sql statement
            String sql = "select * from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //prepared statement setInt
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
        return null;
    }//getMessageById

    /**
     * deletes message from database
     */
    public void deleteMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //sql statement
            String sql = "delete from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //prepared statement setInt
            preparedStatement.setInt(1, message_id);

            preparedStatement.executeUpdate();
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
    }//deleteMessage

    /**
     * update message in database
     * @param id 
     * @param message object
     */
    public void updateMessage(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //sql statement
            String sql = "update message set posted_by = ?, message_text = ?, time_posted_epoch = ? where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //preparedStatements setInt setString setLong
            preparedStatement.setInt(1, message.posted_by);
            preparedStatement.setString(2, message.message_text);
            preparedStatement.setLong(3, message.time_posted_epoch);
            preparedStatement.setInt(4, message_id);

            preparedStatement.executeUpdate();
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
    }//updateMessage

    /**
     * retreive all messages from one user
     * @param account id
     * @return messages List<Message>
     */
    public List<Message> getAllMessagesFromUser(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            //sql statement
            String sql = "select * from message where posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //preparedStatement setInt
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
        }
        return messages;
    }//getAllMessagesFromUser
}
