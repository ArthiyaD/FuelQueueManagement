/* Arthiya Devendran- w1898903, 20211208 */
import java.util.*;
import java.io.*;

public class ArrayVersion {
    //to store the names of customers in each queue
    static String[] queue1 = new String[6];
    static String[] queue2 = new String[6];
    static String[] queue3 = new String[6];
    static int Stock = 6600; //Stock of fuel
    //to store number of customers in each queue(index wise)
    static int q1 = 0;
    static int q2 = 0;
    static int q3 = 0;
    static int queue;//queue number
    static int index;
    static String CustomerName;
    static String Continue;
    static String ProgramData; //Store data to file

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the fuel queue management system! ");
        FuelQueueManagement();
        System.out.println("Thank you!");

    }

    //Prints the menu and returns the option entered
    public static String ViewMenu() {
        System.out.println("""
                                 ~MENU~
                  
                Enter;
                100 or VFQ - View all Fuel Queues.
                101 or VEQ - View all Empty Queues.
                102 or ACQ - Add customer to a Queue.
                103 or RCQ - Remove a customer from a specific location in the Queue.
                104 or PCQ - Remove a served customer.
                105 or VCS - View Customers Sorted in alphabetical order.
                106 or SPD - Store Program Data into file.
                107 or LPD - Load Program Data from file.
                108 or STK - View Remaining Fuel Stock.
                109 or AFS - Add Fuel Stock.
                999 or EXT - Exit the Program
                """);
        System.out.print("PLease enter an option: ");
        return input.next();
    }

    //Proceeds the program with the option entered
    public static void FuelQueueManagement() throws IOException {
        while (true) {
            String option = ViewMenu();
            if (option.equals("100") || option.equalsIgnoreCase("VFQ"))
                ViewAllQueues();
            else if (option.equals("101") || option.equalsIgnoreCase("VEQ"))
                ViewEmptyQueues();
            else if (option.equals("102") || option.equalsIgnoreCase("ACQ"))
                AddCustomer();
            else if (option.equals("103") || option.equalsIgnoreCase("RCQ"))
                RemoveCustomer();
            else if (option.equals("104") || option.equalsIgnoreCase("PCQ"))
                RemoveServedCustomer();
            else if (option.equals("105") || option.equalsIgnoreCase("VCS"))
                SortCustomers();
            else if (option.equals("106") || option.equalsIgnoreCase("SPD"))
                StoreDataToFile();
            else if (option.equals("107") || option.equalsIgnoreCase("LPD"))
                LoadDataFromFile();
            else if (option.equals("108") || option.equalsIgnoreCase("STK"))
                ViewStock();
            else if (option.equals("109") || option.equalsIgnoreCase("AFS"))
                AddStock();
            else if (option.equals("999") || option.equalsIgnoreCase("EXT")) {
                System.out.println("Exiting Program");
                break;
            } else
                System.out.println("Invalid option entered! please reenter option");
        }
    }

    //Displays the customers in all the queues
    public static void ViewAllQueues() {
        System.out.print("Queue 1 : ");
        ViewQueueX(queue1);
        System.out.print("Queue 2 : ");
        ViewQueueX(queue2);
        System.out.print("Queue 3 : ");
        ViewQueueX(queue3);
    }

    //Prints the customer names in a queue, parameter queue is the array of a specific queue.
    public static void ViewQueueX(String[] queue) {
        for (String customer : queue)
            if (customer != null)
                System.out.print(customer + ", ");
        System.out.println();
    }

    //Identify queues which have empty slots
    public static String EmptyQueue(String[] queue) {
        String Status = "full";
        for (String element : queue) {
            if (element == null) {
                Status = "empty";
                break;
            }
        }
        return Status;
    }

   //Displays the queues with empty slots
    public static void ViewEmptyQueues() {
        System.out.println("Empty Queues : ");
        String empty = EmptyQueue(queue1);
        if (empty.equals("empty"))
            System.out.println("Queue 1");

        empty = EmptyQueue(queue2);
        if (empty.equals("empty"))
            System.out.println("Queue 2");

        empty = EmptyQueue(queue3);
        if (empty.equals("empty"))
            System.out.println("Queue 3");
    }

    //Adds a customer to the chosen queue
    public static void AddCustomer() {
        System.out.print("Please Enter Customer Name: ");
        CustomerName = input.next().toLowerCase();
        System.out.print("Enter the queue number(1,2 or 3): ");

        main:
        while (true) {
            IntegerValidation();
            queue = input.nextInt();
            switch (queue) {
                case 1:
                    if (q1 < 6) {
                        q1 = Add(queue, queue1, q1);
                        break main;
                    } else
                        FullQueue(queue);
                    break;

                case 2:
                    if (q2 < 6) {
                        q2 = Add(queue, queue2, q2);
                        break main;
                    } else
                        FullQueue(queue);
                    break;

                case 3:
                    if (q3 < 6) {
                        q3 = Add(queue, queue3, q3);
                        break main;
                    } else
                        FullQueue(queue);
                    break;

                default:
                    System.out.print("Invalid Queue Number. Pls enter 1 ,2 or 3 : ");
            }
        }

        Continue = Continue("add");
        if (Continue.equalsIgnoreCase("yes"))
            AddCustomer();
        else if (Continue.equalsIgnoreCase("no"))
            System.out.println();

    }

     //Method to add a customer, qx is the index of the last element in the queue and is updated after each addition
    public static int Add(int QNumber, String[] queue, int qx) {
        queue[qx] = CustomerName;
        qx++;
        System.out.println("The customer " + CustomerName + " is successfully added to queue number " + QNumber);
        return qx;
    }

    //Displayed when queue has 6 elements and is called for to add 7th element.
    public static void FullQueue(int QNumber) {
        System.out.println("The queue number " + QNumber + " is full.");
        System.out.print("Please choose another queue: ");
    }

    //Displays the current stock of fuel.
    public static void ViewStock() {
        System.out.println("The current stock remaining  is " + Stock + " litres.");
    }

    //Method to add more fuel stock to the current stock.
    public static void AddStock() {
        System.out.print("Please enter the litres of fuel added to the current stock: ");
        while (!input.hasNextInt()) {
            System.out.print("Invalid input.Please re-enter");
            input.next();
        }
        int add = input.nextInt();
        int old = Stock;
        Stock += add;
        System.out.println(add + " liters of fuel was added the the stock of " + old + " litres.");
        System.out.println("The new stock is " + Stock + " litres.");
    }

    //Displays the customers in each queue in alphabetical order
    public static void SortCustomers() {
        String sort;
        System.out.print("Enter the queue number(1,2 or 3) or all to view sorted customers: ");

        while (true) {
            sort = input.next();
            if (sort.equalsIgnoreCase("all")) {
                SortQueue(queue1, 1);
                SortQueue(queue2, 2);
                SortQueue(queue3, 3);
                break;
            } else if (sort.equalsIgnoreCase("1")) {
                SortQueue(queue1, 1);
                break;
            } else if (sort.equalsIgnoreCase("2")) {
                SortQueue(queue2, 2);
                break;
            } else if (sort.equalsIgnoreCase("3")) {
                SortQueue(queue3, 3);
                break;
            } else {
                System.out.println("Invalid input!!");
                System.out.print("PLease Enter 1,2,3 or all: ");
            }
        }
    }

    //Method to sort the customers in a queue
    public static void SortQueue(String[] queue, int QNumber) {
        String[] SortedQueue = queue.clone();
        for (int i = 0; i < SortedQueue.length; i++) {
            for (int j = 0; j < SortedQueue.length - 1; j++) {
                if (SortedQueue[j] != null && SortedQueue[j + 1] != null) {
                    if (SortedQueue[j].compareTo(SortedQueue[j + 1]) > 0) {
                        String temp = SortedQueue[j];
                        SortedQueue[j] = SortedQueue[j + 1];
                        SortedQueue[j + 1] = temp;
                    }
                }
            }
        }
        System.out.print("Sorted Queue " + QNumber + " : ");
        for (String customer : SortedQueue)
            if (customer != null)
                System.out.print(customer + ", ");
        System.out.println();
    }

    //Removes a customer from a specified location in  a specific queue
    public static void RemoveCustomer() {
        System.out.print("Enter Queue Number of Customer to be removed (1, 2 or 3): ");

        queue:
        while (true) {
            IntegerValidation();
            queue = input.nextInt();
            while (true) {
                if (queue > 0 && queue < 4) {
                    System.out.print("Enter the customer location in the queue (1-6): ");
                    IntegerValidation();
                    int location = input.nextInt();
                    if (location > 0 && location < 7) {
                        index = location - 1;
                        if (queue == 1) {
                            q1 = RemoveIndex(queue1, index, q1);
                        } else if (queue == 2) {
                            q2 = RemoveIndex(queue2, index, q2);
                        } else if (queue == 3) {
                            q3 = RemoveIndex(queue3, index, q3);
                        }
                        break queue;
                    } else
                        System.out.print("Invalid location. ");
                } else
                    System.out.print("Invalid Queue Number. Pls enter 1 ,2 or 3: ");
            }
        }
        Continue = Continue("remove");
        if (Continue.equalsIgnoreCase("yes"))
            RemoveCustomer();
        else if (Continue.equalsIgnoreCase("no"))
            System.out.println();
    }

    //Method to remove any customer from a queue
    public static int RemoveIndex(String[] queue, int index, int qn) {
        if (index < qn) {
            for (int i = 0, k = 0; i < queue.length - 1; i++) {
                if (i != index) {
                    queue[k] = queue[i];
                    k++;
                } else {
                    System.out.println("Customer " + queue[i] + " was successfully removed from the queue.");
                }
            }

            queue[5] = null;
            qn--;
        } else {
            System.out.println("There is no customer at location " + (index + 1));
        }
        return qn;
    }

    //Removed the first customer in the queue and reduces the fuel stock by 10 liters (as the customer was served)
    public static void RemoveServedCustomer() {
        System.out.print("Enter Queue Number of Served Customer(1,2 or 3): ");
        main:
        while (true) {
            IntegerValidation();
            queue = input.nextInt();
            switch (queue) {
                case 1:
                    q1 = ServedCustomer(queue1, q1);
                    break main;

                case 2:
                    q2 = ServedCustomer(queue2, q2);
                    break main;

                case 3:
                    q3 = ServedCustomer(queue3, q3);
                    break main;

                default:
                    System.out.print("Invalid Queue Number. Pls enter 1 ,2 or 3: ");
            }
        }
        Continue = Continue("serve and remove");
        if (Continue.equalsIgnoreCase("yes"))
            RemoveServedCustomer();
        else if (Continue.equalsIgnoreCase("no"))
            System.out.println();
    }

    //Method to remove the 1st customer and reduce stock
    public static int ServedCustomer(String[] queue, int qn) {
        if (queue[0] != null){
            String served= queue[0];
            for (int i = 1; i < queue.length; i++) {
                queue[i - 1] = queue[i];
            }
            queue[5] = null;
            qn--;
            System.out.println("The customer " + served + " was served with fuel(10 litres) and successfully removed from the queue");
            Stock -= 10;
            if (Stock <= 500)
              System.out.println("Warning!!! The current fuel stock has reached 500 liters");
        }
        else
            System.out.println("There are no customers in the queue");
        return qn;
    }

    public static void IntegerValidation() {
        while (!input.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            input.next();
        }
    }

    //To check for continuity in the current function
    public static String Continue(String Function) {
        String cont;
        System.out.print("Do you want to " +Function +" another customer? (yes or no): ");
        cont = input.next();
        while (!cont.equalsIgnoreCase("yes") && !cont.equalsIgnoreCase("no")) {
            System.out.print("Invalid input! Please Enter yes or no: ");
            cont = input.next(); // this is important!
        }
        return cont;

    }

    //Stores all the queue customer data and current fuel stock data to files
    public static void StoreDataToFile() throws IOException {
            StoreQueueData();
            System.out.println("The Fuel Queue data was successfully stored.");
    }

    //to store Queue data in files
    public static void StoreQueueData() throws IOException {
        FileWriter myWriter= new FileWriter("FuelQueue.txt");
        myWriter.write("Queue 1: \n");
        myWriter.write(PrintArray(queue1));
        myWriter.write("Queue 2: \n");
        myWriter.write(PrintArray(queue2));
        myWriter.write("Queue 3: \n");
        myWriter.write(PrintArray(queue3));
        myWriter.write("The current full stock is: \n");
        myWriter.write(String.valueOf(Stock));
        myWriter.close();
    }

    //To print the elements in the queue array to a file
    public static String PrintArray(String [] queue){
        ProgramData="";
        for (String customer : queue){
            if (customer != null)
                ProgramData += customer +"\n";
            else
                ProgramData+="\n";
        }
        return ProgramData ;
    }

    //Loads all the queue and stock data stored into the program for use
    public static void LoadDataFromFile()  {
        try {
        LoadArray();
        System.out.println("The stored Fuel Queue Data was successfully loaded to the program ");
        } catch (IOException e) {
            System.out.println("No Data was stored");
        }
    }

    //Loads customers of the queue (stored) into an array
    public static void LoadArray() throws IOException{
        File inputFile = new File ("FuelQueue.txt");
        Scanner file = new Scanner(inputFile);
        String Data;
        int count=0;

            while (file.hasNext()) {
                Data = file.nextLine();
                if (Data == "") {
                    count++;
                } else {
                    if (count > 0 && count < 7) {
                        queue1[q1] = Data;
                        q1++;
                        count++;
                    } else if (count > 7 && count < 14) {

                        queue2[q2] = Data;
                        q2++;
                        count++;
                    } else if (count > 14 && count < 21) {
                        queue3[q3] = Data;
                        q3++;
                        count++;
                    } else if (count == 0 || count == 7 || count == 14 || count == 21)
                        count++;
                    else if (count==22)
                        Stock= Integer.parseInt(Data);


                }
            }
    }

}





