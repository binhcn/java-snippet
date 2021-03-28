package dev.binhcn.behavior.command.bank;

public class OpenAccount implements Command {
 
    private Account account;
 
    public OpenAccount(Account account) {
        this.account = account;
    }
 
    @Override
    public void execute() {
        account.open();
    }
}