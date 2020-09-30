package transfer;

public class Account {
    int balance = 600;

    public void transfer(int transferAmount) {
        if (balance >= transferAmount)
            balance -= transferAmount;
    }

    public int transfer() {
        return balance;
    }
}
