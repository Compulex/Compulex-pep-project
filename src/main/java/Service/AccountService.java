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
     * insert a new account
     * @param account
     * @return Account 
     */
    public Account addAccount(Account account){
        return accountDAO.insertAccount(account);
    }//addAccount

}//end class
