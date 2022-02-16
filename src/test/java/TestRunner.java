import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestRunner {

    CustomerUser customer;

    Transaction transaction;

    @Test(priority = 0)
    public void doLogin() throws ConfigurationException, IOException {

         customer = new CustomerUser();
        customer.userLoginApi();
    }
    @Test(priority = 1)
     public void getUserList() throws IOException {

        customer = new CustomerUser();

        customer.userList();

     }
    @Test(priority = 2)
     public void searchUserInfo() throws IOException {

        customer = new CustomerUser();
        customer.searchUser();

     }
    @Test(priority = 3)
     public void searchUserByAgentInfo() throws IOException {

        customer = new CustomerUser();
        customer.searchUserByRole();
     }

    @Test(priority = 4)
    public void randomGenerateUser() throws IOException, ConfigurationException {

        customer = new CustomerUser();
        customer.generateUser();
    }

    @Test(priority = 5)
    public void getNewUser() throws IOException {

        customer = new CustomerUser();
        customer.createUser();

    }




    @Test(priority = 7 )
    public void getTransactionList() throws IOException {

        customer = new CustomerUser();
        customer.transactionSearch();
    }

    @Test(priority = 8)
    public void getTransactionStatementByAccount() throws IOException {

        transaction = new Transaction();
        transaction.transactionStatementByAccount();
    }

    @Test(priority = 9)
    public void searchTransactionListByTzxnId() throws IOException {

        transaction = new Transaction();
        transaction.searchTransactionListByTrnxId();
    }


    @Test(priority = 10)
    public void getTransactionListByUser() throws IOException {

        transaction = new Transaction();
        transaction.transactionList();
    }


    @Test(priority = 11)
    public void getBalance() throws IOException {

        transaction = new Transaction();
        transaction.checkBalance();
    }

    @Test(priority = 12)
    public void getDeposite() throws IOException {

        transaction = new Transaction();
        transaction.checkDeposite();
    }

    @Test(priority = 13)
    public void moneyTransaction() throws IOException {

        transaction = new Transaction();
        transaction.sendMoney();
    }

    @Test(priority = 14)
    public void withdrawTransaction() throws IOException {

        transaction = new Transaction();
        transaction.withdraw();
    }


}
