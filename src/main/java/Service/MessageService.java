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
        deleteMessage(message_id);
    }

    
}
