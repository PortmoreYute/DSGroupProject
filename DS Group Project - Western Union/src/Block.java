
@SuppressWarnings("unused")
public class Block {

    private String id;
    private String status;
    private Block nextBlock;
    private Block prevBlock;
    private Transaction pendingTransaction [] = new Transaction[3];
    private int numOfTransaction;

    public Block(String id, String status, Block nextBlock, Transaction[] pendingTransaction, int numOfTransaction) {
        this.id = id;
        this.status = status;
        this.nextBlock = nextBlock;
        this.pendingTransaction = pendingTransaction;
        this.numOfTransaction = numOfTransaction;
    }

    public Block() {
        this.id = "B";
        this.status = "pending";
        this.nextBlock = null;
        this.prevBlock = null;
        for(int i=0;i<3;i++)
            this.pendingTransaction[i] = new Transaction();
        this.numOfTransaction = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }

    public Block getPrevBlock() {
        return prevBlock;
    }

    public void setPrevBlock(Block prevBlock) {
        this.prevBlock = prevBlock;
    }

    public Transaction[] getPendingTransaction() {
        return pendingTransaction;
    }

    public void setPendingTransaction(Transaction[] pendingTransaction) {
        this.pendingTransaction = pendingTransaction;
    }
    //Function to set a transaction to a specific index of the transaction array in the block
    public void setSpecificTransaction(Transaction currTransaction, int indexToSet){
        this.pendingTransaction[indexToSet] = currTransaction;
        this.numOfTransaction += 1;
    }
    public int getNumOfTransaction() {
        return numOfTransaction;
    }

    public void setNumOfTransaction(int numOfTransaction) {
        this.numOfTransaction = numOfTransaction;
    }

    public void checkStatus(){
        boolean check = false;
        for(int i=0;i<3;i++){
            if (pendingTransaction[i].getStatus().equals("Valid")){
                check = true;
            }
            else{
                check = false;
                break;
            }
        }
        if(check){
            setStatus("Mined");
            uniqueId();
        }
    }

    private void uniqueId(){
        for(int i=0; i<3;i++){
            this.id += pendingTransaction[i].getTransactionHash();
        }
    }

    public void displayBlock(){
        System.out.println("ID: " + id +"\t Status: "+ status + "\tNumber of Transactions: " + numOfTransaction);
    }
}
