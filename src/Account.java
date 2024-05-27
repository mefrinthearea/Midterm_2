// File: src/Account.java

public class Account {
    private final String accountNumber;
    private final String accountHolderName;
    private final double balance;

    public Account(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                '}';
    }

    public String toCSV() {
        return accountNumber + "," + accountHolderName + "," + balance;
    }

    public static Account fromCSV(String csv) {
        String[] parts = csv.split(",");
        return new Account(parts[0], parts[1], Double.parseDouble(parts[2]));
    }
}
