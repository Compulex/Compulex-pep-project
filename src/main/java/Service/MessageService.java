package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }//constructor

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }//param constructor

    /**
     * insert a new message
     * @param message
     * @return Message
     */
    public Message addMessage(Message message){
        return messageDAO.insertMessage(message);
    }

    /**
     * using MessageDAO to retreive all messages 
     * @return messages in the database
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * get a message by id
     * @param message id
     * @return message
     */
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    /**
     * delete message by id
     * @param id
     */
    public void deleteMessage(int message_id){
        messageDAO.deleteMessage(message_id);
    }

    /**
     * update message 
     * @param id message
     * @param Message
     
     */
    public void updateMessage(int message_id, Message message){
        messageDAO.updateMessage(message_id, message);
    }
    
    /**
     * get user messages by account id
     * @param account id
     * @return messages List
     */
    public List<Message> getAllUserMessages(int account_id){
        return messageDAO.getAllMessagesFromUser(account_id);
    }
    
}
