package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.Objects;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }//constructor

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }//param constructor

    /**
     * insert a new account, make sure another account doesn't have same username
     * @param account object
     * @return Account 
     */
    public Account addAccount(Account account){
        Account existingAcct = accountDAO.getAccountByUsername(account.getUsername());
        //account with given username is not found
        if(existingAcct == null){
            return accountDAO.insertAccount(account);
        }
        else{
            return null;
        }
    }//addAccount

    /**
     * check if the registered account object and login account object have matching usernames and passwords
     * @param regAcct registered account
     * @param loginAcct logged in account
     * @return boolean
     */
    public boolean loginAccount(Account regAcct, Account loginAcct){
        boolean loggedIn = false;
        if(regAcct == null){
            if(loginAcct.getUsername().equals("") && loginAcct.getPassword().equals("")){
                loggedIn = false;
            }
        }
        else{
            loggedIn = Objects.equals(regAcct.getUsername(), loginAcct.getUsername()) &&
                    Objects.equals(regAcct.getPassword(), loginAcct.getPassword());
        }
        return loggedIn;
    }


    /**
     * get account by id
     * @param account_id
     * @return account
     */
    public Account getAccountById(int account_id){
        return accountDAO.getAccountById(account_id);
    }


}//end class
