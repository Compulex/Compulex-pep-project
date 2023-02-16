package Service;

import DAO.AccountDAO;
import Model.Account;

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
     * @param account
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
     * get account by id
     * @param account id
     * @return account
     */
    public Account getAccountById(int account_id){
        return accountDAO.getAccountById(account_id);
    }


}//end class
