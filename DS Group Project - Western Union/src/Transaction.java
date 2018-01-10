import java.util.Date;

@SuppressWarnings("unused")
public class Transaction {

    private String transactionHash;
    private String sender;
    private String recipient;
    private float amount;
    private String status;
    private Date  dateCreated;

    public Transaction() {
        this.transactionHash = null;
        this.sender = null;
        this.recipient = null;
        this.amount = 0.0f;
        this.status = "default";
        this.dateCreated = new Date ();
    }

    public Transaction(String sender, String recipient, float amount, Date dateCreated) {
        this.transactionHash = "T" +sender.hashCode()+ recipient.hashCode() + dateCreated.hashCode();
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.status = "Pending";
        this.dateCreated = dateCreated;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void displayTransaction(){
        System.out.println("Transaction Id: "+ transactionHash+
                "\nSender:" + sender + " Recipient:" +recipient+
                "\nAmount:" + amount + " Status:" +status+
                "\nDate:" + dateCreated.toString()+"" +
                "\n");
    }

    public void displayTransactionFromFile(){
        System.out.println("Transaction Id: "+ transactionHash+
                "\nSender:" + sender + " Recipient:" +recipient+
                "\nAmount:" + amount + " Status:" +status+
                "\n");
    }
}
