// File: src/Bank.java

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {
    private final List<Account> accounts;
    private static final Logger logger = Logger.getLogger(Bank.class.getName());

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(account.getAccountNumber())) {
                logger.log(Level.INFO, "Account with account number " + account.getAccountNumber() + " already exists.");
                return;
            }
        }
        this.accounts.add(account);
        logger.log(Level.INFO, "Account added: " + account);
    }

    public boolean removeAccount(String accountNumber) {
        Iterator<Account> iterator = accounts.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            Account account = iterator.next();
            if (account.getAccountNumber().equals(accountNumber)) {
                iterator.remove();
                logger.log(Level.INFO, "Account removed: " + account);
                removed = true;
                break;
            }
        }
        if (!removed) {
            logger.log(Level.INFO, "Account with account number " + accountNumber + " not found.");
        }
        return removed;
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public void displayAccounts() {
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    public void saveState() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("state.csv"))) {
            for (Account account : accounts) {
                writer.write(account.toCSV());
                writer.newLine();
            }
            logger.log(Level.INFO, "State saved to state.csv");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving state to file", e);
        }
    }

    public void restoreState() {
        accounts.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("state.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                accounts.add(Account.fromCSV(line));
            }
            logger.log(Level.INFO, "State restored from state.csv");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error restoring state from file", e);
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();

        Account acc1 = new Account("123", "John Doe", 1000.0);
        Account acc2 = new Account("124", "Jane Smith", 2000.0);

        bank.addAccount(acc1);
        bank.addAccount(acc2);

        bank.displayAccounts();

        List<Account> accountsList = bank.getAccounts(); // Using the getAccounts() method
        System.out.println("Accounts list: " + accountsList);

        bank.saveState();

        bank.removeAccount("123");

        bank.displayAccounts();

        bank.restoreState();

        bank.displayAccounts();
    }
}
