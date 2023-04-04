package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    Account accountEmpty;
    Account newAccount;
    Message messageEmpty;
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        
        //empty objects
        this.accountEmpty = new Account();
        this.messageEmpty = new Message();
    }//constructor

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        app.post("/register", this::registerHandler); //req 1
        
        app.post("/login", this::loginHandler); //req 2
        
        app.post("/messages", this::newMessageHandler); //req 3
        
        app.get("/messages", this::getAllMessagesHandler); //req 4
        
        //req 5
        app.get("/messages/{message_id}", ctx ->{
            String message_id = ctx.pathParam("message_id");
            getMessageByIdHandler(ctx, Integer.parseInt(message_id));
        });
        
        //req 6
        app.delete("/messages/{message_id}", ctx ->{
            String message_id = ctx.pathParam("message_id");
            deleteMessageHandler(Integer.parseInt(message_id));
        });
        
        //req 7
        app.patch("/messages/{message_id}", ctx ->{
            String message_id = ctx.pathParam("message_id");
            updateMessageHandler(ctx, Integer.parseInt(message_id));
        });
        
        //req 8
        app.get("/accounts/{account_id}/messages", ctx ->{
            String account_id = ctx.pathParam("account_id");
            getUserMsgsHandler(ctx, Integer.parseInt(account_id));
        });
        return app;
    }

    /**
     * handler to register endpoint when creating a new acount
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account addedAcct = accountService.addAccount(account);

        //there's no existing account, no username duplicates
        if(addedAcct == null){
            context.status(400);
        }
        else{
            newAccount = addedAcct; //for login

            //username can't be blank
            if(addedAcct.getUsername().equals("")){
                context.status(400);
            }
            if(addedAcct.equals(accountEmpty)){
                context.status(400);
            }
            //password has to be more than 4chars length
            if(addedAcct.getPassword().length() < 4){
                context.status(400);
            }
            else{
                context.json(om.writeValueAsString(addedAcct));
            }
        }
    }//registerHandler

    /**
     * handler for login checking for an existing account
     */
    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);

        //check if the registered account matches login
        if(accountService.loginAccount(newAccount, account)){
            context.json(om.writeValueAsString(newAccount));
        }
        else{
            context.status(401);
        }
    }//loginHandler

    /**
     * handler to add a new message
     */
    private void newMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        Message addedMsg = messageService.addMessage(message);

        if(addedMsg == null){
            context.status(400);
        }
        else{
            //message too long
            if(addedMsg.getMessage_text().length() < 255){ 
                context.json(om.writeValueAsString(addedMsg));
            }
            //check if message blank 
            if(addedMsg.getMessage_text().equals("")){
                context.status(400);
            }
            else{
                context.json(om.writeValueAsString(addedMsg));
            }
        }
    }//newMessageHandler

    /**
     * gets all messages in the database
     */
    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }//getAllMessagesHandler

    /*
     * get message by id
     */
    private void getMessageByIdHandler(Context context, int mid){
        Message message = messageService.getMessageById(mid);
        context.json(message);
    }//getMessageByIdHandler

    /**
     * delete message by id
     */
    private void deleteMessageHandler(int mid){
        //Message message = messageService.getMessageById(mid);
        messageService.deleteMessage(mid);
    }//deleteMessageHandler
    
    /**
     * handler to update a message
     */
    private void updateMessageHandler(Context context, int mid) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        Message updatedMsg = messageService.updateMessage(mid, message);
        
        //check if message exists, text is not blank and text is no longer than 255 characters
        if(updatedMsg == null){
            context.status(400);
        }
        else{
            if(updatedMsg.equals(messageEmpty)){
                context.status(400);
            }
            //message can't be blank 
            if(updatedMsg.getMessage_text().equals("")){
                context.status(400);
            }
            //message text must be under 255 chars
            if(updatedMsg.getMessage_text().length() < 255){ 
                context.json(om.writeValueAsString(updatedMsg));
            }
        }
    }//updateMessageHandler
    
    /**
     * handler for retreiving all messages from user
     */
    private void getUserMsgsHandler(Context context, int aid){
        List<Message> userMessages = messageService.getAllUserMessages(aid);
        context.json(userMessages);
    }//getUserMsgsHandler
        
}

