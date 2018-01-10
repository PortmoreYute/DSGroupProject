import java.util.Scanner;

@SuppressWarnings("CanBeFinal")
class Menu {

    private Account currAccount;
    private RemittanceFile rm = new RemittanceFile();
    private Block pendingBlock = new Block();
    private Scanner userInput = new Scanner(System.in);
    private String opt;


    public Menu() {
    }

    void loginMenu() {
        do {
            System.out.println("**********Login Menu**********");
            System.out.println("Please Select an Option");
            System.out.println("1:Login \t 2:Create Account \t 3:Exit");
            opt = userInput.next();
            switch (opt) {
                case "1":
                    System.out.println("Login In Option Selected");
                    currAccount = rm.login();
                    if (currAccount.getId() != null) {
                        processMenu();
                    }
                    break;
                case "2":
                    System.out.println("Create Account Option Selected");
                    rm.createAccount();

                    break;
                case "3":
                    System.out.println("Exit Option Selected");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Menu option");
                    System.out.println("Please Select a valid option");
                    break;
            }
        } while (continueChoice());
    }

    private void processMenu() {
        System.out.println("**********Process Menu**********");
        System.out.println("Please Select an Option");
        System.out.println("1:Account Transactions \t 2:Auditing \t 3:Exit");
        opt = userInput.next();

        switch (opt) {
            case "1":
                System.out.println("Account Transactions Selected");
                accountTransactionMenu();
                break;
            case "2":
                System.out.println("Auditing Selected");
                auditMenu();
                break;
            case "3":
                System.out.println("Exit Option Selected");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Menu option");
                System.out.println("Please Select a valid option");
                break;
        }

    }

    private void accountTransactionMenu() {
        System.out.println("**********Account Transaction Menu**********");
        System.out.println("Please Select an Option");
        System.out.println("1:Account Details \t 2:View Received Transactions \t 3:View Sent Transactions \t 4:Send BTC \t 5:Exit");
        opt = userInput.next();

        switch (opt) {
            case "1":
                System.out.println("Account Details");
                currAccount.displayAccount();
                break;
            case "2":
                System.out.println("View Received Transactions");
                rm.checkTransactionFile(currAccount, "Received");
                break;
            case "3":
                System.out.println("View Sent Transactions");
                rm.checkTransactionFile(currAccount, "Sent");
                break;
            case "4":
                System.out.println("Send BTC");
                blockProgress();
                break;
            case "5":
                System.out.println("Exit Option Selected");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Menu option");
                System.out.println("Please Select a valid option");
                break;
        }
    }

    private void auditMenu() {

        System.out.println("**********Audit Menu**********");
        System.out.println("Please Select an Option");
        System.out.println("1:View All Pending Transaction \t 2:View Verified Blocks \t 3:Find Block \t 4:Find Transaction \t 5:View All Accounts \t 6:Exit");
        opt = userInput.next();
        Transaction test = new Transaction();
        switch (opt) {
            case "1":
                System.out.println("View All Pending Transaction");
                pendingTransactions();
                break;
            case "2":
                System.out.println("View Verified Blocks");
                rm.viewBlocks("view");
                break;
            case "3":
                System.out.println("Find Block");
                rm.viewBlocks("find");
                break;
            case "4":
                System.out.println("Find Transaction");
                rm.viewTransaction();
                break;
            case "5":
                System.out.println("View All Accounts");
                rm.buildAccountArray();
                break;
            case "6":
                System.out.println("Exit Option Selected");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Menu option");
                System.out.println("Please Select a valid option");
                break;
        }
    }

    private boolean continueChoice() {
        Boolean choice;

        System.out.println("Do you want to return to the Login Menu?");
        System.out.println("1:Login Menu \t 2:Exit");
        opt = userInput.next();

        switch (opt) {
            case "1":
                System.out.println("Login Menu");
                choice = true;
                break;
            case "2":
                System.out.println("Exit Option Selected");
                choice = false;
                break;
            default:
                System.out.println("Invalid Menu option");
                System.out.println("Please Select a valid option");
                choice = true;
                break;
        }
        return choice;
    }

    //For the Purpose of closing the scanner later
    public Scanner getUserInput() {
        return userInput;
    }


    private void blockProgress() {
        for (int i = 0; i < 3; i++) {
            if (pendingBlock.getPendingTransaction()[i].getStatus().equals("default")) {
                pendingBlock.setSpecificTransaction(rm.sendBTC(currAccount), i);
                break;
            }
        }
        //checks if block is full
        if (pendingBlock.getNumOfTransaction() > 2) {
            validateBlock();
        }
    }

    private void validateBlock() {
        for (int i = 0; i < 3; i++) {
            if (rm.availableBalance(pendingBlock.getPendingTransaction()[i].getSender(), pendingBlock.getPendingTransaction()[i].getAmount())) {
                if (pendingBlock.getPendingTransaction()[i].getStatus().equals("Pending")) {
                    pendingBlock.getPendingTransaction()[i].setStatus("Valid");
                    rm.editAccountRecord(pendingBlock.getPendingTransaction()[i]);
                    rm.writeTransactionFile(pendingBlock.getPendingTransaction()[i]);
                }
            } else {
                pendingBlock.getPendingTransaction()[i].setStatus("default");
            }
        }
        pendingBlock.checkStatus();
        if (pendingBlock.getStatus().equals("Mined")){
            rm.writeBlock(pendingBlock);
            setUpNewBlock();
        }
    }

    private void setUpNewBlock(){
        pendingBlock.setNextBlock(new Block());
        pendingBlock.getNextBlock().setPrevBlock(pendingBlock);
        pendingBlock = pendingBlock.getNextBlock();
    }

    private void pendingTransactions() {
        for (int i = 0; i < 3; i++) {
            if (pendingBlock.getPendingTransaction()[i].getStatus().equals("Pending")) {
                pendingBlock.getPendingTransaction()[i].displayTransaction();
            }
        }
    }
}
