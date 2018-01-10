
@SuppressWarnings("unused")
class Account {

    private String id;
    private String username;
    private String password;
    private float balance;

    public Account() {
        this.id = null;
        this.username = null;
        this.password = null;
        this.balance = 500;
    }

    public Account(String id, String username, String password, float balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void displayAccount(){
        System.out.println("ID:" + id +" Username:" + username + " Password:" +password + " Balance:" + balance);
    }
}
