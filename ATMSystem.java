import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private int userId;
    private int userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(int userId, int userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public boolean validatePin(int enteredPin) {
        return userPin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

public class ATMSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userId = 1234;
        int userPin = 5678;
        Account account = new Account(userId, userPin);

        System.out.println("Welcome to the ATM system.");
        System.out.print("Enter user ID: ");
        int enteredUserId = scanner.nextInt();
        System.out.print("Enter PIN: ");
        int enteredPin = scanner.nextInt();

        if (account.validatePin(enteredPin) && userId == enteredUserId) {
            System.out.println("Login successful.");
            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. View Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewTransactionHistory(account);
                        break;
                    case 2:
                        System.out.print("Enter the amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter the amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient's user ID: ");
                        int recipientId = scanner.nextInt();
                        System.out.print("Enter the amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        Account recipientAccount = new Account(recipientId, 0); // Simulated recipient account
                        account.transfer(recipientAccount, transferAmount);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid user ID or PIN. Exiting...");
        }
    }

    public static void viewTransactionHistory(Account account) {
        List<Transaction> transactions = account.getTransactionHistory();
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }
}
