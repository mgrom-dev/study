public class Account
{
    private long money;
    private String accNumber;
    private boolean isBlock;

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
        this.isBlock = false;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }
}
