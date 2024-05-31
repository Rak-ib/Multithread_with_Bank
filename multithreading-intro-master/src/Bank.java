import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Bank {
    String accountNumber;

    OperationsQueue operationsQueue;

    int balance = 0;
    boolean flag=true;
    boolean flag1=false;
    boolean flag2=false;
    private final Lock balanceLock = new ReentrantLock();


    public Bank(String accountNumber, OperationsQueue operationsQueue) {
        this.accountNumber = accountNumber;
        this.operationsQueue = operationsQueue;
    }

    // A deposit function that will run in parallel on a separate thread. It will be a loop where in each iteration, it read the amount from the operationQueue and deposit the amount.
    public void deposit() {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        while (true) {
            //Lock

            // while((flag)){
            //     System.out.println("deposite");
            // }
            // if(flag){
            //     try {
            //         wait(10);
            //     } catch (Exception e) {
            //         // TODO: handle exception
            //     }
            // }
            


            //Unlock
            // if(flag1==false)
            // flag=(flag^flag);
            int amount = operationsQueue.getNextItem();
            if(amount == -9999) {
                operationsQueue.add(-9999);
                flag=true;
                flag2=true;
                break;
            }
            balanceLock.lock();
            try{
                if (amount>0) {
                    // balance = operationsQueue.updateValance(balance,amount);
                    balance=balance+amount;
                    System.out.println("from deposite-------"+"Deposited: " + amount + " Balance: " + balance);
                }
                else{
                    operationsQueue.add(amount);
                    System.out.println("from deposite ------ operation added back "+amount);
                }
            }finally{
                balanceLock.unlock();
            }

            
            
            

        }
    }

    // A withdraw function that will run in parallel on a separate thread.
    // It will be a loop where in each iteration, it read the amount from the operationQueue and withdraw the amount.
    public void withdraw() {
        while (true) {
            // while(!flag){
            //     System.out.println("withdraw");
            // }
            // if(!flag){
            //     try {
            //         wait(10);
            //     } catch (Exception e) {
            //         // TODO: handle exception
            //     }
            // }
            int amount = operationsQueue.getNextItem();
            




            // if(flag2==false)
            // flag=(flag^flag);
            if(amount == -9999) {
                operationsQueue.add(-9999);
                flag=false;
                flag1=true;
                break;
            }

            balanceLock.lock();
            try {
                if(balance+amount<0){

                    System.out.println("-------withdraw----- Not enough balance to deposite "+amount);
                    continue;
                }
    
                if (amount<0) {
                    // balance = operationsQueue.updateValance(balance,amount);
                    balance=balance+amount;
                    System.out.println("------withdraw----- Withdrawn: " + amount + " Balance: " + balance);
                }
                else{
                    operationsQueue.add(amount);
                    System.out.println("----withedraw-----operation added back "+amount);
                }
            } finally {
                balanceLock.unlock();
                // TODO: handle exception
            }
            

        }
    }
}
