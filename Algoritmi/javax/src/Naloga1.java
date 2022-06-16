

import java.util.Scanner;
// ### INTERFACES AND CLASSES ###
@SuppressWarnings("unchecked")
class CollectionException extends Exception {
    public CollectionException(String msg) {
        super(msg);
    }
}


interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

   // boolean isEmpty();
    boolean isFull();
    int size();
    String toString();
}


interface Stack<T> extends Collection {
    T top() throws CollectionException;
    void push(T x) throws CollectionException;
    T pop() throws CollectionException;
}


interface Deque<T> extends Collection {
    T front() throws CollectionException;
    T back() throws CollectionException;
    void enqueue(T x) throws CollectionException;
    void enqueueFront(T x) throws CollectionException;
    T dequeue() throws CollectionException;
    T dequeueBack() throws CollectionException;
}


interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";

    T get(int i) throws CollectionException;

    void add(T x) throws CollectionException;
}
@SuppressWarnings("unchecked")
class ArrayDeque<T> implements Deque<T>, Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;
    private T[] a;
    private int front,back,size;
    public ArrayDeque(){
        a =(T[]) new Object [DEFAULT_CAPACITY];


    }
    private int next (int i){
        return (i+1) % DEFAULT_CAPACITY;

    }
    private int prev(int i){
        return (DEFAULT_CAPACITY + i -1) % DEFAULT_CAPACITY;

    }
    private int index(int i){
        return(front + i) % DEFAULT_CAPACITY;
    }
//    @Override
//    public boolean isEmpty(){
//        return (size == 0);
//    }

    @Override
    public boolean isFull() {
        return (size == DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        if(size > 0) sb.append(a[front].toString());
        for (int i = 0; i < size - 1 ; i++) {
            sb.append("," + a[next(front +i)].toString());
        }
        sb.append("]");
        return sb.toString();
    }
    public T top() throws CollectionException{
      //  if(isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
        return a[prev(back)];

    }

    @Override
    public void push(T x) throws CollectionException {
        if(isFull()) throw new CollectionException(ERR_MSG_FULL);
        a[back] = x;
        back = next(back);
        size++;

    }

    @Override
    public T pop() throws CollectionException {
    //    if(isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
        back = prev(back);
        T b = a[back];
        a[back] = null;
        size--;
        return  b;
    }


    @Override
    public T front() throws CollectionException {

   //     if(isEmpty())  throw new CollectionException(ERR_MSG_EMPTY);
        return a[0];


    }

    @Override
    public T back() throws CollectionException {
        return top();
    }

    @Override
    public void enqueue(T x) throws CollectionException {
        push(x);
    }

    @Override
    public void enqueueFront(T x) throws CollectionException {
        if(isFull()) throw new CollectionException(ERR_MSG_FULL);
        front = prev(front);
        a[front] = x;
        size++;


    }


    @Override
    public T dequeue() throws CollectionException {
    //    if(isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
        T popped = a[0];
        front = next(front);
        size--;
        return popped;
    }

    @Override
    public T dequeueBack() throws CollectionException {
        return pop();
    }


    @Override
    public void add(T x) throws CollectionException {
        push(x);
    }

    @Override
    public T get(int i) throws CollectionException {
    //    if(isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
        if(i<0 || i>= size) throw new CollectionException(ERR_MSG_INDEX);
        return a[index(i)];
    }
}




// ### FUNCTIONS ###


public class Naloga1 {

    @SuppressWarnings(value = "unchecked")

    public static ArrayDeque<ArrayDeque<String>> theSequence(){
        try {
            ArrayDeque<ArrayDeque<String>> sequence = new ArrayDeque<>();
            for (int i = 0; i < 42; i++) {
                sequence.add(new ArrayDeque<String>());

            }
            return sequence;
        } catch (Exception e) {

            System.out.println(e);

        }
        return null;
    }
    public static ArrayDeque<ArrayDeque<String>> theSequence = theSequence();

    public static  void echo() throws CollectionException{
        if(theSequence.get(0).size() == 0) System.out.println("");
       else{ System.out.println(theSequence.get(0).top());}
    }
    public static void pop() throws CollectionException{
        theSequence.get(0).pop();
    }
    public static void dup() throws CollectionException{

        theSequence.get(0).push(theSequence.get(0).top());
    }
    public static void dup2() throws CollectionException {

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();
            theSequence.get(0).push(two);
            theSequence.get(0).push(one);
            theSequence.get(0).push(two);
            theSequence.get(0).push(one);
        }

    public static void swap() throws CollectionException{

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();
            theSequence.get(0).push(one);
            theSequence.get(0).push(two);

        }


    public static void isChar() throws CollectionException{

            int ascii = Integer.parseInt(theSequence.get(0).pop());
            char nov  = (char) ascii;
            theSequence.get(0).push(Character.toString(nov));

        }

    public static void  even() throws CollectionException{

            if(Math.abs(Integer.parseInt(theSequence.get(0).top()))% 2 ==0) {
                theSequence.get(0).pop();
            theSequence.get(0).push("1");}
            else{
                theSequence.get(0).pop();theSequence.get(0).push("0");
            }

        }

    public static void  odd() throws CollectionException{

            if(Math.abs(Integer.parseInt(theSequence.get(0).top()))% 2 ==1) {
                theSequence.get(0).pop();
                theSequence.get(0).push("1");}
            else{
                theSequence.get(0).pop();theSequence.get(0).push("0");
            }

        }

    public static void fact() throws CollectionException{

            long broj = Integer.parseInt(theSequence.get(0).pop());
            long produkt = 1;
            for (int i =1; i <= broj; i++) {
                produkt *= i;
            }
            theSequence.get(0).push(Long.toString(produkt));
        }


    public static void len() throws CollectionException{

            int counter = 0;
            String object = theSequence.get(0).pop();

                for (int i = 0; i < object.length(); i++) {
                    counter++;

                }
            theSequence.get(0).push(Integer.toString(counter));
        }

    public static void notEqual() throws  CollectionException {

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();

            if(Integer.parseInt(one) != Integer.parseInt(two)) theSequence.get(0).push("1");
            else{theSequence.get(0).push("0");}
        }

    public static void small() throws  Exception {

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();

            if(Integer.parseInt(one) > Integer.parseInt(two)) theSequence.get(0).push("1");
            else{theSequence.get(0).push("0");}
        }

    public static void smallOrEqual() throws  CollectionException {

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();

            if(Integer.parseInt(one) >= Integer.parseInt(two)) theSequence.get(0).push("1");
            else{theSequence.get(0).push("0");}
        }

    public static void equal() throws  CollectionException {

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();

            if(Integer.parseInt(one) == Integer.parseInt(two)) theSequence.get(0).push("1");
            else{theSequence.get(0).push("0");}
        }

    public static void big() throws  CollectionException {

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();

            if(Integer.parseInt(one) < Integer.parseInt(two)) theSequence.get(0).push("1");
            else{theSequence.get(0).push("0");}
        }

    public static void bigOrEqual() throws  CollectionException {

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();

            if(Integer.parseInt(one) <= Integer.parseInt(two)) theSequence.get(0).push("1");
            else{theSequence.get(0).push("0");}
        }

    public static void plus() throws CollectionException{

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();
            long element = Long.parseLong(two) + Long.parseLong(one);
            theSequence.get(0).push(Long.toString(element));
        }

    public static void minus() throws CollectionException{

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();
        long element = Long.parseLong(two) - Long.parseLong(one);
        theSequence.get(0).push(Long.toString(element));
        }

    public static void times() throws CollectionException{

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();
        long element = Long.parseLong(two) * Long.parseLong(one);
        theSequence.get(0).push(Long.toString(element));
        }

    public static void divide() throws CollectionException {

        String one = theSequence.get(0).pop();
        String two = theSequence.get(0).pop();
        if (Integer.parseInt(one) == 0) {
            for (int i = 0; i < 1; i++) {
                System.out.print("");
            }

        }
        else {
            long element = Long.parseLong(two) / Long.parseLong(one);
            theSequence.get(0).push(Long.toString(element));
        }
    }
    public static void modulus() throws CollectionException{

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();
        long element = Long.parseLong(two) % Long.parseLong(one);

            theSequence.get(0).push(Long.toString(element));

        }

    public static void join() throws CollectionException{

            String one = theSequence.get(0).pop();
            String two = theSequence.get(0).pop();

            theSequence.get(0).push(two + one);
        }


    public static void rnd() throws CollectionException {

        String one = theSequence.get(0).pop();
        String two = theSequence.get(0).pop();
        int maximum = Math.max(Integer.parseInt(one),Integer.parseInt(two) );
        int minimum = Math.min(Integer.parseInt(one),Integer.parseInt(two) );

        int range = (maximum - minimum) + 1;
        double random = minimum + (Math.random() * range);
        int nov = (int) Math.round(Math.floor(random));
        theSequence.get(0).push(Integer.toString(nov));
        }


    public static void print() throws CollectionException{

            int broj = Integer.parseInt(theSequence.get(0).pop());
            for (int i = 0; i < theSequence.get(broj).size(); i++) {
                System.out.print(theSequence.get(broj).get(i)+" ");
            }
            System.out.println("");
        }

    public static void clear() throws CollectionException{

            int broj = Integer.parseInt(theSequence.get(0).pop());
            while(theSequence.get(broj).size() != 0){
                theSequence.get(broj).pop();

            }
        }

    public static void reverse() throws CollectionException{

            ArrayDeque<String> temp = new ArrayDeque<>();
            int broj = Integer.parseInt(theSequence.get(0).pop());
            while(theSequence.get(broj).size() != 0){
                temp.push(theSequence.get(broj).pop());


            }
            for (int i = 0; i < temp.size() ; i++) {
                theSequence.get(broj).push(temp.get(i));
            }
        }

    public static void run() throws CollectionException{


    }

    public static boolean isTrue = false;
    public static void then() throws CollectionException{

            int broj = Integer.parseInt(theSequence.get(0).pop());
            if(broj != 0) isTrue = true;

        }

    public static void _else() throws CollectionException{

            isTrue = !isTrue;

        }
    // 3 4 fun echo pop echo pop
    public static String sledeci = "";
    public static int brojac = 0;
    public static void fun(String [] niza ) throws CollectionException{

        int broj1 =Integer.parseInt( theSequence.get(0).pop()); //stack num
        int broj2 =Integer.parseInt( theSequence.get(0).pop()); //reps

        for (int i = 0; i < broj2; i++) {
            brojac++;
            theSequence.get(broj1).push(niza[brojac]);
        }





    }

    public static void move() throws CollectionException{
        int broj1 =Integer.parseInt( theSequence.get(0).pop()); //stack num
        int broj2 =Integer.parseInt( theSequence.get(0).pop()); //reps

        for (int i = 0; i < broj2; i++) {
            theSequence.get(broj1).push(theSequence.get(0).pop());
        }
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {



        try {



            // run bez da se smeni proveri isto kako vo main. switchot da go klajs vo promenliva za da dostapi vo
            //napraj posebna metoda so postoecki switch

            // vo loop e isto ko run i ke go povikas run();
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
              String []  nxt = sc.nextLine().split(" +");
                for ( ; brojac < nxt.length; brojac++) {

                        if (nxt[brojac].contains("?")) {
                            nxt[brojac] = nxt[brojac].replace("?", "");
                            if (!isTrue) continue;


                        }

                        switch (nxt[brojac]) {
                            case "echo":
                                echo();
                                break;
                            case "pop":
                                pop();
                                break;
                            case "dup":
                                dup();
                                break;
                            case "dup2":
                                dup2();
                                break;
                            case "swap":
                                swap();
                                break;
                            case "char":
                                isChar();
                                break;
                            case "even":
                                even();
                                break;
                            case "odd":
                                odd();
                                break;
                            case "!":
                                fact();
                                break;
                            case "len":
                                len();
                                break;
                            case "<>":
                                notEqual();
                                break;
                            case "<":
                                small();
                                break;
                            case "<=":
                                smallOrEqual();
                                break;
                            case "==":
                                equal();
                                break;
                            case ">":
                                big();
                                break;
                            case ">=":
                                bigOrEqual();
                                break;
                            case "+":
                                plus();
                                break;
                            case "-":
                                minus();
                                break;
                            case "*":
                                times();
                                break;
                            case "/":
                                divide();
                                break;
                            case "%":
                                modulus();
                                break;
                            case ".":
                                join();
                                break;
                            case "rnd":
                                rnd();
                                break;
                            case "then":
                                then();
                                break;
                            case "else":
                                _else();
                                break;

                            case "print":
                                print();
                                break;
                            case "clear":
                                clear();
                                break;


//                case "loop":
//                    loop();
//                    break;
                            case "fun":
                                fun(nxt);
                                break;
                            case "move":
                                move();
                                break;
                            case "reverse":
                                reverse();
                                break;
                            default:
                                theSequence.get(0).push(nxt[brojac]);
                        }
                    }
                for (int i = 0; i < theSequence.size(); i++) {
                    while(theSequence.get(i).size() != 0){
                        theSequence.get(i).pop();
                    }
                }
                isTrue = false;
                brojac = 0;
                }



            sc.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.print("");
    }
}