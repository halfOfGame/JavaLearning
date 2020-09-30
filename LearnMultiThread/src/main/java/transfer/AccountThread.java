package transfer;

public class AccountThread implements Runnable{
    private Account account = new Account();

    @Override
    public void run() {
        if (account.transfer() >= 400)
            System.out.println();
    }
}
