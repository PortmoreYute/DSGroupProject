import java.io.*;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

class RemittanceFile {
    public RemittanceFile() {

    }

    void createAccount() {
        Scanner accInput = new Scanner(System.in);
        Account newAccount = new Account();
        String nameToCheck = " ";
        Boolean nameTaken = true;

        while (nameTaken) {
            System.out.println("Please Enter A Username");
            nameToCheck = accInput.next();
            nameTaken = validKey(nameToCheck, "Username");
            if (nameTaken) {
                System.out.println("The username entered is taken");
            }
        }
        newAccount.setId("A" + nameToCheck.hashCode() + Date.from(Instant.now()).hashCode());
        newAccount.setUsername(nameToCheck);
        System.out.println("Please Enter A Password");
        newAccount.setPassword(accInput.next());
        writeAccount(newAccount);
    }

    //Checks Accounts File to Verify If Username or Id Entered Is Valid
    private boolean validKey(String valueToCheck, String key) {
        Scanner inFile;
        Account tempAcc = new Account();
        File accFile = new File("Account.txt");

        if (accFile.exists()) {
            try {
                inFile = new Scanner(accFile);
                while (inFile.hasNext()) {
                    tempAcc.setId(inFile.next());
                    tempAcc.setUsername(inFile.next());
                    tempAcc.setPassword(inFile.next());
                    tempAcc.setBalance(inFile.nextFloat());
                    if (key.equals("Username")) {
                        if (tempAcc.getUsername().equals(valueToCheck)) {
                            inFile.close();
                            return true;
                        }
                    }
                    if (key.equals("Id")) {
                        if (tempAcc.getId().equals(valueToCheck)) {
                            inFile.close();
                            return true;
                        }
                    }

                }
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Could Note Find User Account File");
        }
        return false;
    }

    //Stores the New Account The User Created In The Account File
    private void writeAccount(Account newAccount) {
        File accFile = new File("Account.txt");

        try {
            if (!accFile.exists()) {
                accFile.createNewFile();
            }
            FileWriter accFW = new FileWriter(accFile, true);
            BufferedWriter accBW = new BufferedWriter(accFW);
            accBW.write(newAccount.getId() + " ");
            accBW.write(newAccount.getUsername() + " ");
            accBW.write(newAccount.getPassword() + " ");
            accBW.write(String.format("%.2f", newAccount.getBalance()));
            accBW.newLine();
            accBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Displays login Screen and Accepts User Password and Username
    Account login() {
        Scanner accInput = new Scanner(System.in);
        Account validAccount = new Account();
        String nameToCheck;
        String passToCheck;
        Boolean inCorrCred = true;

        while (inCorrCred) {
            System.out.println("Please Enter A Username");
            nameToCheck = accInput.next();
            System.out.println("Please Enter A Password");
            passToCheck = accInput.next();
            // check cred and returns an account
            validAccount = checkCred(nameToCheck, passToCheck);
            // if ID has null value then that means no valid account was found
            if (validAccount.getId() != null) {
                inCorrCred = false;
            }
            if (inCorrCred) {
                System.out.println("The username or password is incorrect");
            }
        }
        System.out.println("Successful");
        return validAccount;

    }

    //Checks if Username and Password is found in the Accounts records and Returns the account details if found
    private Account checkCred(String username, String password) {
        Scanner inFile;
        Account tempAcc = new Account();
        File accFile = new File("Account.txt");

        if (accFile.exists()) {
            try {
                inFile = new Scanner(accFile);
                while (inFile.hasNext()) {
                    tempAcc.setId(inFile.next());
                    tempAcc.setUsername(inFile.next());
                    tempAcc.setPassword(inFile.next());
                    tempAcc.setBalance(inFile.nextFloat());

                    if (tempAcc.getUsername().equals(username) && tempAcc.getPassword().contentEquals(password)) {
                        inFile.close();
                        return tempAcc;
                    }
                }
                tempAcc.setId(null);
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Users Exist On This System");
        }
        return tempAcc;
    }

    Transaction sendBTC(Account currAccount) {
        Scanner userInput = new Scanner(System.in);
        //Transaction tempTransaction = new Transaction();
        Transaction tempTransaction;
        float balanceToCheck;
        String idToCheck;
        Boolean invalidID = true;

        System.out.println("Please Enter Amount You Wish To send");
        balanceToCheck = userInput.nextFloat();

        //Checks if the amount they want to send against their Balance
        if (balanceToCheck < currAccount.getBalance() || balanceToCheck == currAccount.getBalance()) {
            //Loops Until A Valid Id Is Entered
            while (invalidID) {
                System.out.println("Please Enter Id Of The Person You Wish to Send BTC to");
                idToCheck = userInput.next();
                validKey(idToCheck, "Id");
                if (validKey(idToCheck, "Id")) {
                    //Creates A Possible Transaction
                    tempTransaction = new Transaction(currAccount.getId(), idToCheck, balanceToCheck, Date.from(Instant.now()));
                    return tempTransaction;
                } else {
                    System.out.println("No Such Id Exists");
                    invalidID = loopChoice();
                }
            }
        } else {
            System.out.println("You Do Not Have Enough BTC To Send The Value Entered");
            System.out.println("Please Enter A Valid Amount");
        }
        // return  tempTransaction;
        return null;
    }

    private boolean loopChoice() {
        Scanner opt = new Scanner(System.in);
        System.out.println("Do you want to attempt to enter the correct Input again? ");
        System.out.println("Y or y: To retry \t Another Key to exit");
        return opt.next().equals("Y") || opt.next().equals("y");


    }

    Boolean availableBalance(String id, float amount) {
        Scanner inFile;
        Account tempAcc = new Account();
        File accFile = new File("Account.txt");
        Boolean enough = false;

        if (accFile.exists()) {
            try {
                inFile = new Scanner(accFile);
                while (inFile.hasNext()) {
                    tempAcc.setId(inFile.next());
                    tempAcc.setUsername(inFile.next());
                    tempAcc.setPassword(inFile.next());
                    tempAcc.setBalance(inFile.nextFloat());

                    if (tempAcc.getId().equals(id)) {
                        if (tempAcc.getBalance() == amount || tempAcc.getBalance() > amount) {
                            enough = true;
                        }
                        break;
                    }
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Users Exist On This System");
        }
        return enough;
    }

    void editAccountRecord(Transaction currTransaction) {
        Scanner inFile;
        File accFile = new File("Account.txt");
        File tempFile = new File("Temp.txt");
        Account tempRecord = new Account();
        try {
            inFile = new Scanner(accFile);

            while (inFile.hasNext()) {
                tempRecord.setId(inFile.next());
                tempRecord.setUsername(inFile.next());
                tempRecord.setPassword(inFile.next());
                tempRecord.setBalance(inFile.nextFloat());

                if (currTransaction.getSender().equals(tempRecord.getId())) {
                    // subtract amount from balance
                    tempRecord.setBalance(tempRecord.getBalance() - currTransaction.getAmount());
                }

                if (currTransaction.getRecipient().equals(tempRecord.getId())) {
                    // add amount to balance
                    tempRecord.setBalance(tempRecord.getBalance() + currTransaction.getAmount());
                }
                writeTempFile(tempRecord);
            }
            inFile.close();
            accFile.delete();
            tempFile.renameTo(new File("Account.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void writeTempFile(Account newAccount) {
        File accFile = new File("Temp.txt");

        try {
            if (!accFile.exists()) {
                accFile.createNewFile();
            }
            FileWriter accFW = new FileWriter(accFile, true);
            BufferedWriter accBW = new BufferedWriter(accFW);
            accBW.write(newAccount.getId() + " ");
            accBW.write(newAccount.getUsername() + " ");
            accBW.write(newAccount.getPassword() + " ");
            accBW.write(String.format("%.2f", newAccount.getBalance()));
            accBW.newLine();
            accBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeTransactionFile(Transaction currTransaction) {
        File transactionFile = new File("Transaction.txt");

        try {
            if (!transactionFile.exists()) {
                transactionFile.createNewFile();
            }
            FileWriter transactionFW = new FileWriter(transactionFile, true);
            BufferedWriter transactionBW = new BufferedWriter(transactionFW);
            transactionBW.write(currTransaction.getTransactionHash() + " ");
            transactionBW.write(currTransaction.getSender() + " ");
            transactionBW.write(currTransaction.getRecipient() + " ");
            transactionBW.write(currTransaction.getStatus() + " ");
            transactionBW.write(String.format("%.2f", currTransaction.getAmount()) + " ");
            transactionBW.write(currTransaction.getDateCreated().toString());
            transactionBW.newLine();
            transactionBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void checkTransactionFile(Account currAccount, String action) {
        Scanner inFile;
        Transaction currTransaction = new Transaction();
        File transactionFile = new File("Transaction.txt");
        // String dateCreated = null;

        if (transactionFile.exists()) {
            try {
                inFile = new Scanner(transactionFile);
                while (inFile.hasNext()) {
                    currTransaction.setTransactionHash(inFile.next());
                    currTransaction.setSender(inFile.next());
                    currTransaction.setRecipient(inFile.next());
                    currTransaction.setStatus(inFile.next());
                    currTransaction.setAmount(inFile.nextFloat());
                    for (int i = 0; i < 6; i++)
                        inFile.next();

                    if (action.equals("Sent")) {
                        if (currAccount.getId().equals(currTransaction.getSender())) {
                            currTransaction.displayTransactionFromFile();
                        }
                    }
                    if (action.equals("Received")) {
                        if (currAccount.getId().equals(currTransaction.getRecipient())) {
                            currTransaction.displayTransactionFromFile();
                        }
                    }
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Users Exist On This System");
        }

    }

    void viewTransaction() {
        Scanner inFile;
        Scanner userInput = new Scanner(System.in);
        String transactionID;
        Transaction currTransaction = new Transaction();
        File transactionFile = new File("Transaction.txt");
        // String dateCreated = null;
        System.out.println("Please Enter Transaction Id");
        transactionID = userInput.next();

        if (transactionFile.exists()) {
            try {
                inFile = new Scanner(transactionFile);
                while (inFile.hasNext()) {
                    currTransaction.setTransactionHash(inFile.next());
                    currTransaction.setSender(inFile.next());
                    currTransaction.setRecipient(inFile.next());
                    currTransaction.setStatus(inFile.next());
                    currTransaction.setAmount(inFile.nextFloat());
                    for (int i = 0; i < 6; i++)
                        inFile.next();
                    if (currTransaction.getTransactionHash().equals(transactionID)) {
                        currTransaction.displayTransactionFromFile();
                    }
                }
                inFile.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Users Exist On This System");
        }
    }

    void writeBlock(Block minedBlock){
        File blockFile = new File("Block.txt");

        try {
            /*if (!blockFile.exists()) {
                blockFile.createNewFile();
            }*/
            FileWriter blockFW = new FileWriter(blockFile, true);
            BufferedWriter blockBW = new BufferedWriter(blockFW);
            blockBW.write(minedBlock.getId() + " ");
            blockBW.write(minedBlock.getStatus()+ " ");
            for(int i=0;i<3;i++)
                blockBW.write(minedBlock.getPendingTransaction()[i].getTransactionHash() + " ");
            blockBW.newLine();
            blockBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void viewBlocks(String action){
        Scanner inFile;
        Scanner userInput = new Scanner(System.in);
        File blockFile = new File("Block.txt");
        String blockID, status;
        String searchID = null;
        String transactionID [] = new String[3];

        if (action.equals("find")) {
            System.out.println("Please Enter The Block ID you are searching For");
            searchID = userInput.next();
        }

        try {
            inFile = new Scanner(blockFile);


            while (inFile.hasNext()) {
                if (action.equals("view")) {
                    System.out.println(inFile.next());
                }
                if (action.equals("find")) {
                    blockID = inFile.next();
                    status = inFile.next();
                    transactionID[0] = inFile.next();
                    transactionID[1] = inFile.next();
                    transactionID[2] = inFile.next();
                    if (blockID.equals(searchID)) {
                        System.out.println(blockID + " " + status + " " + transactionID[0] + " " + transactionID[1] + " " + transactionID[2]);
                    }
                    break;
                }
            }
            inFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    int countRecords (){
        Scanner inFile;
        int count=0;
        Account tempAcc = new Account();
        File accFile = new File("Account.txt");

        if (accFile.exists()) {
            try {
                inFile = new Scanner(accFile);
                while (inFile.hasNext()) {
                    tempAcc.setId(inFile.next());
                    tempAcc.setUsername(inFile.next());
                    tempAcc.setPassword(inFile.next());
                    tempAcc.setBalance(inFile.nextFloat());
                    count += 1;
                }

                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Users Exist On This System");
        }
        return  count;
    }

    void buildAccountArray(){
        int size = countRecords();
        Account accountArr [] = new Account[size];
        int index=0;
        Scanner inFile;

        File accFile = new File("Account.txt");

        for (int i = 0; i<size; i++)
            accountArr[i] = new Account();

        if (accFile.exists()) {
            try {
                inFile = new Scanner(accFile);
                while (inFile.hasNext()) {
                    accountArr[index].setId(inFile.next());
                    accountArr[index].setUsername(inFile.next());
                    accountArr[index].setPassword(inFile.next());
                    accountArr[index].setBalance(inFile.nextFloat());
                    index += 1;
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Users Exist On This System");
        }
        accountArr = sortAccountArray(accountArr,size);
        System.out.println("\n Sorted Account");
        for(int i=0;i<size;i++){
            accountArr[i].displayAccount();
        }
        //writeSortedAccount(accountArr,size);
    }

    Account[] sortAccountArray(Account[] records,int size){
        Account tempAccount;

        for (int i = 0; i < size; i++) {
            for (int v = 1; v < size; v++) {
                if (records[v - 1].getBalance() < records[v].getBalance()) {
                    tempAccount = records[v - 1];
                    records[v - 1] = records[v];
                    records[v] = tempAccount;
                }

            }
        }
        return records;
    }

    //To write sorted to file if needs be
    void writeSortedAccount(Account[] records,int size){

        File accFile = new File("SortedAccount.txt");

        try {
            if (!accFile.exists()) {
                accFile.createNewFile();
            }
            FileWriter accFW = new FileWriter(accFile);
            BufferedWriter accBW = new BufferedWriter(accFW);
            for(int i=0;i<size;i++) {
                accBW.write(records[i].getId() + " ");
                accBW.write(records[i].getUsername() + " ");
                accBW.write(records[i].getPassword() + " ");
                accBW.write(String.format("%.2f", records[i].getBalance()));
            }
            accBW.newLine();
            accBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
