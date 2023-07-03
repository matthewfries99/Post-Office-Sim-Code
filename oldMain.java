//Matthew Fries, Assignment #4
//This program simulates a post office line with a worker servicing the customer at the front of the line.
//The program simulates the random chance of a new customer arriving and being added to the queue, the random
//chance of the customer at the end of the line becoming disgruntled and leaving, and also the random chance
//of the customer at the front of the line being asked to step aside to fill out information. When a customer
//is asked to step aside, the next customer in line can step up and begin being serviced. The user is prompted to
//enter the total simulation time (the number of iterations the for loop runs), the total service time (how long
//it takes a customer to be serviced), and also the probabilities that each of the three random events listed above
//will occur. After executing the code, the program prints out the customers that were serviced, the customers that
//were disgruntled and left, and also the customers still waiting to be serviced after the simulation ends.

import java.util.*;
class Customer{
    private int customerNum;
    private int processTime;
    private Boolean disgruntled;

    public Customer(){
        customerNum = 0;
        processTime = 0;
        disgruntled = false;
    }
    public Customer(int customer, int time, Boolean flag){
        customerNum = customer;
        processTime = time;
        disgruntled = flag;
    }
    public int getCustomerNum(){
        return customerNum;
    }
    public int getProcessTime(){
        return processTime;
    }
    public Boolean getDisgruntled(){
        return disgruntled;
    }
    public void setCustomerNum(int num){
        customerNum = num;
    }
    public void setProcessTime(int time){
        processTime = time;
    }
    public void setDisgruntled(Boolean flag){
        disgruntled = flag;
    }
}
class Node{
    private Customer customer;
    private Node next;
    private Node prev;

    public Node(){
        customer = new Customer();
        next = null;
        prev = null;
    }
    public Node(Customer c, Node n, Node p){
        customer = c;
        next = n;
        prev = p;
    }
    public Node(Customer d, Node n){
        customer = d;
        next = n;
    }
    public Node getNext(){
        return next;
    }
    public Customer getCustomer(){
        return customer;
    }
    public Node getPrev(){
        return prev;
    }
    public void setCustomer(Customer c){
        customer = c;
    }
    public void setNext(Node n){
        next = n;
    }
    public void setPrev(Node p){
        prev = p;
    }
}

class Deque{
    private Node front;
    private Node rear;
    private int size;

    public Deque(){
        rear = null;
        front = null;
        size = 0;
    }


    public Boolean isEmpty(){
        if(size == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void clear(){
        rear = null;
        front = null;
        size = 0;
    }

    public int size(){
        return size;
    }
    public Node getFront(){
        return front;
    }
    public Node getRear(){
        return rear;
    }

    public void addFront(Customer x){
        Node t = new Node(x, null, null);
        if(front == null){
            front = rear = t;
        }
        else{
            t.setNext(front);
            front.setPrev(t);
            front = t;
        }
        size++;
    }
    public void addRear(Customer x){
        Node t = new Node(x, null, null);
        if(front == null){
            front = rear = t;
        }
        else{
            t.setPrev(rear);
            rear.setNext(t);
            rear = t;
        }
        size++;
    }
    public Customer removeFront(){
        if(front == null){
            return null;
        }
        else{
            Customer t = front.getCustomer();
            front = front.getNext();
            size--;
            return t;
        }
    }
    public Customer removeRear(){
        if(front == null) {
            return null;
        }
        Customer x = rear.getCustomer();
        rear = rear.getPrev();
        size--;
        if(front.getNext() == null){
            front = null;
        }
        else{
            rear.setNext(null);
        }
        return x;

    }
    public void print(){
        Node t = front;
        while(t != null){
            System.out.println(t.getCustomer());
            t = t.getNext();
        }
    }
}

class Queue{
    private Node front;
    private Node rear;
    private int size;

    public Queue(){
        front = null;
        rear = null;
        size = 0;
    }

    public void enqueue(Customer d){
        Node t = new Node(d, null);
        if(front == null){
            front = t;
            rear = t;
        }
        else{
            rear.setNext(t);
            rear = t;
        }
        size++;
    }

    public Customer dequeue(){
        if(front == null){
            return null;
        }
        Customer frontCus = front.getCustomer();
        front = front.getNext();
        if(front == null){
            rear = null;
        }
        size--;
        return frontCus;
    }

    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Customer front(){
        if(size == 0){
            return null;
        }
        else{
            return front.getCustomer();
        }
    }

    public int size(){
        return size;
    }

    public void print(){
        if(size == 0){
            System.out.println("Empty Queue");
        }
        else{
            Node t = front;
            while(t != null){
                System.out.println(t.getCustomer());
                t = t.getNext();
            }
        }
    }
    public void clear(){
        front = null;
        rear = null;
        size = 0;
    }
    public Node getFront(){
        return front;
    }
    public Node getRear(){
        return rear;
    }
    public void setFront(Node t){
        front = t;
    }
    public void setRear(Node t){
        rear = t;
    }
}

public class oldMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        //Take in and validate probabilities used in simulation
        System.out.println("Enter arrival probability (0-100): ");
        int tempArrive = scan.nextInt();
        int arriveProb = validateProb(tempArrive, scan);
        System.out.println("Enter disgruntled probability (0-100): ");
        int tempDisgruntle = scan.nextInt();
        int disgruntleProb = validateProb(tempDisgruntle, scan);
        System.out.println("Enter step-aside probability (0-100): ");
        int tempAside = scan.nextInt();
        int asideProb = validateProb(tempAside, scan);
        //Take in and validate times used in simulation
        System.out.println("Enter simulation time (any positive integer): ");
        int tempSimTime = scan.nextInt();
        while(tempSimTime<0){
            System.out.println("Invalid simulation time. Please enter a positive integer.");
            tempSimTime = scan.nextInt();
        }
        int simTime = tempSimTime;
        System.out.println("Enter service time (3-10): ");
        int tempServiceTime = scan.nextInt();
        while(tempServiceTime < 3 || tempServiceTime > 10){
            System.out.println("Invalid service time. Please enter a number 3-10.");
            tempServiceTime = scan.nextInt();
        }
        int serviceTime = tempServiceTime;
        //Create dequeue with 3 customers
        Deque customerLine = new Deque();
        customerLine.addFront(new Customer(0, 0, false));
        customerLine.addFront(new Customer(0, 0, false));
        customerLine.addFront(new Customer(0, 0, false));
        //Create queues for processed and disgruntled customers
        Queue processedQueue = new Queue();
        Queue disgruntledQueue = new Queue();
        //Create simulation loop variables
        int asideCtr = 0;
        int serviceCtr = 0;
        int tempServiceCtr = 0;
        int tempServiceCtr2 = 0;
        int asideTime = 3;
        int whileCtr;
        boolean onSide = false;
        Node tempNode;
        Customer currentCustomer = null;
        Customer asideCustomer = null;
        //Simulation loop
        for(int i=0; i<simTime; i++){
            whileCtr = 0;
            tempNode = customerLine.getFront();
            while(whileCtr < customerLine.size() && !customerLine.isEmpty()){
                tempNode.getCustomer().setProcessTime(tempNode.getCustomer().getProcessTime()+1);
                tempNode = tempNode.getNext();
                whileCtr++;
            }
            if(currentCustomer == null && !customerLine.isEmpty()){
                currentCustomer = customerLine.getFront().getCustomer();
            }
            serviceCtr++;
            if(serviceCtr == serviceTime){
                if(tempServiceCtr2 != 0){
                    serviceCtr = tempServiceCtr2;
                    tempServiceCtr2 = 0;
                }
                else{
                    serviceCtr = 0;
                }
                if(currentCustomer != null){
                    processedQueue.enqueue(currentCustomer);
                    customerLine.removeFront();
                    currentCustomer = null;
                }
                if(!customerLine.isEmpty()){
                    currentCustomer = customerLine.getFront().getCustomer();
                }
            }
            if(rand.nextInt(101) < arriveProb){
                if(customerLine.isEmpty()){
                    serviceCtr = 0;
                }
                customerLine.addRear(new Customer(i, 0, false));

            }
            if(rand.nextInt(101) < disgruntleProb && !customerLine.isEmpty()){
                Customer disgruntledCustomer = customerLine.getRear().getCustomer();
                disgruntledCustomer.setDisgruntled(true);
                disgruntledQueue.enqueue(disgruntledCustomer);
                customerLine.removeRear();
            }
            if(asideCtr == 0){
                if(rand.nextInt(101) < asideProb){
                    onSide = true;
                    asideCustomer = currentCustomer;
                    customerLine.removeFront();
                    if(!customerLine.isEmpty()){
                        currentCustomer = customerLine.getFront().getCustomer();
                        tempServiceCtr = serviceCtr;
                        serviceCtr = 0;
                    }
                }
            }
            if(onSide){
                asideCtr++;
                if(asideCtr == asideTime){
                    if(asideCustomer != null){
                        customerLine.addFront(asideCustomer);
                    }
                    currentCustomer = asideCustomer;
                    tempServiceCtr2 = serviceCtr;
                    serviceCtr = tempServiceCtr;
                    asideCtr = 0;
                    onSide = false;
                }
            }
        }
        //Print out data from simulation
        System.out.println("Number of serviced customers: "+processedQueue.size());
        System.out.println("Customer numbers and processing times for serviced customers: ");
        printCustomersInQueue(processedQueue);
        System.out.println();
        System.out.println("Number of disgruntled customers: "+disgruntledQueue.size());
        System.out.println("Customer numbers and processing times for disgruntled customers: ");
        printCustomersInQueue(disgruntledQueue);
        System.out.println();
        System.out.println("Number of customers still waiting to be serviced: "+customerLine.size());
        System.out.println("Customer numbers and processing times for customers waiting to be serviced: ");
        printCustomersInDeque(customerLine);
    }


    public static int validateProb(int prob, Scanner scan){
        while(prob < 0 || prob > 100){
            System.out.println("Invalid probability. Please enter a number 0-100.");
            prob = scan.nextInt();
        }
        return prob;
    }

    public static void printCustomersInQueue(Queue queue){
        Node temp = queue.getFront();
        while(temp != null){
            System.out.println("Customer Number: " +temp.getCustomer().getCustomerNum());
            System.out.println("Process Time: " +temp.getCustomer().getProcessTime());
            temp = temp.getNext();
        }
    }

    public static void printCustomersInDeque(Deque deque){
        Node temp = deque.getFront();
        while(temp != null){
            System.out.println("Customer Number: " +temp.getCustomer().getCustomerNum());
            System.out.println("Process Time: " +temp.getCustomer().getProcessTime());
            temp = temp.getNext();
        }

    }
}

