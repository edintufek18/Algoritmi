import java.util.Scanner;
@SuppressWarnings("unchecked")
class CollectionException extends Exception {
    public CollectionException(String msg) {
        super(msg);
    }
}
interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

  //  boolean isEmpty();
    int size();
    String toString();


}
interface Stack<Integer> extends Collection {
    int top() throws CollectionException;
    void push(int x) throws CollectionException;
    int pop() throws CollectionException;
    void resizable();
}

class ArrayX implements Stack<Integer>{
    private static final int DEFAULT_CAPACITY = 4;
    private static int capacity = DEFAULT_CAPACITY;
    private int[] a;
    private int front,back,size;
    public ArrayX(){
        a =  new int[capacity];
    }
    private int next (int i){
        return (i+1) % capacity;

    }
    private int prev(int i){
        return (capacity + i -1) % capacity;

    }
    private int index(int i){
        return(front + i) % capacity;
    }




//    @Override
//    public boolean isEmpty() {
//        return (size == 0);
//    }

    @Override
    public int size() {
       return size;
    }


    @Override
    public int top() throws CollectionException {
        //  if(isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
        return a[prev(back)];

    }

    @Override
    public void push(int x) throws CollectionException {
        resizable();
        a[back] = x;
        back = next(back);
        size++;

    }

    @Override
    public int pop() throws CollectionException {
        back = prev(back);
        int b = a[back];
        a[back] = Integer.parseInt(null);
        size--;
        return b;

    }

    @Override
    public void resizable() {
        if(size == capacity){
            capacity*=2;
            int [] temp = new int [capacity];
            for (int i = 0; i < a.length; i++) {
                temp[i]=a[i];
            }

            a = temp;
            back = size;

        }

    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        if(size > 0) sb.append(a[front]);
        for (int i = 0; i < size - 1 ; i++) {
            sb.append("," + a[next(front + i)]);
        }
        sb.append("]");
        return sb.toString();
    }
    public int get(int i) throws CollectionException {
        //    if(isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
        if(i<0 || i>= size) throw new CollectionException("Ne obstaja taksen index");
        return a[index(i)];
    }

}







@SuppressWarnings("unchecked")
public class Naloga2 {
    public static ArrayX edin = new ArrayX();
    public static int [] tempo;
    public static int brojac = 0;
    public static int compareCnt = 0;
    public static int swapCnt = 0;
    public static boolean traceActivated =false;

    public static boolean up = true;
    static void swap(int [] list,int a,int b){
        int temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }
    public static void set(int[] a, int i, int j) {
        swapCnt++;
        a[i] = a[j];
    }
    public static boolean bigger(int i, int j) {
        compareCnt++;
        return i > j;
    }
    public static boolean smaller(int i, int j) {
        compareCnt++;
        return i < j;
    }
    public static boolean biggerOrEqual(int i, int j) {
        compareCnt++;
        return i >= j;
    }
    public static boolean smallerOrEqual(int i, int j) {
        compareCnt++;
        return i <= j;
    }
    public static int[] insert(int[] list){
        if (up) {
            for (int i = 1; i < list.length; i++) {
                int k = list[i];
                swapCnt++;
                int j = i;
                while (j > 0 && bigger(list[j-1],k)) {
                    set(list,j,j-1);
                    j = j - 1;

                }
                list[j] = k;
                swapCnt++;
                trace(list,i+1);
            }
            return list;
        }
        else{
            for (int i = 1; i < list.length; i++) {
                int k = list[i];
                swapCnt++;
                int j = i;
                while (j > 0 && smaller(list[j - 1],k)){
                    set(list,j,j-1);
                    j = j - 1;

                }
                list[j] = k;
                swapCnt++;
                trace(list,i+1);
            }
            return list;
        }
    }
    public static int[] select(int [] list){
            if(up) {
                for (int i = 0; i < list.length - 1; i++) {
                    int m = i;
                    for (int j = i + 1; j < list.length; j++) {
                        compareCnt += 1;
                        if (list[j] < list[m]) {
                            m = j;
                        }

                    }
                    swap(list,i,m);
                    swapCnt += 3;
                    trace(list,i+1);
                }
                return list;
            }
            else {
                for (int i = 0; i < list.length - 1; i++) {
                    int m = i;
                    for (int j = i + 1; j < list.length; j++) {
                        compareCnt += 1;
                        if (list[j] > list[m]) {
                            m = j;
                        }

                    }
                    swap(list,i,m);
                    swapCnt += 3;
                    trace(list,i+1);
                }
                return list;
            }
    }
    public static int[] bubble(int [] list,int n){
        if(up) {
            int lastSwap = 0;
            for (int i = 1; i < list.length; i++) {
                boolean is_sorted = true;
                int currentSwap = list.length-1;
                for (int j = list.length-1;j >lastSwap; j--) {
                    compareCnt++;
                    if (list[j] < list[j - 1]) {
                        swap(list,j,j-1);
                        swapCnt+=3;
                        is_sorted = false;
                        currentSwap = j;
                    }
                }
                trace(list,currentSwap);
                if (is_sorted || currentSwap == list.length-1){


                    return list;
                }

                lastSwap = currentSwap;

            }
            return list;
        }
        else{
            int lastSwap = 0;
            for (int i = 1; i < list.length; i++) {
                boolean is_sorted = true;
                int currentSwap = list.length-1;
                for (int j = list.length-1;j >lastSwap; j--) {
                    compareCnt++;
                    if (list[j] > list[j - 1]) {
                        swap(list,j,j-1);
                        swapCnt+=3;
                        is_sorted = false;
                        currentSwap = j;
                    }
                }
                trace(list,currentSwap);
                if (is_sorted || currentSwap == list.length-1){


                    return list;
                }

                lastSwap = currentSwap;

            }
            return list;
    }
    }
    public static void trace(int [] list,int broj){
        if(traceActivated) {
            int size = list.length;
            for (int i = 0; i < size; i++) {
                if (broj != 0 && broj == i) System.out.printf("| %d ", list[i]);
                else System.out.printf("%d ", list[i]);
            }
            if (broj == size) System.out.print("|");
            System.out.println();
        }
    }
    static void siftDown(int [] list,int p,int size){
        if(up) {
            int c = 2 * p + 1;
            //  int last = list.length-1;
            while (c < size) {

                if (c + 1 < size && bigger(list[c + 1],list[c])) c++;
                if (biggerOrEqual(list[p],list[c])) break;
                swap(list, p, c);
                swapCnt += 3;
                p = c;
                c = 2 * p + 1;
                swapCnt++;
            }
        }
        else{
            int c = 2 * p + 1;
            //  int last = list.length-1;
            while (c < size) {

                if (c + 1 < size && smaller(list[c + 1],list[c])) c++;
                if (smallerOrEqual(list[p],list[c])) break;
                swap(list, p, c);
                swapCnt += 3;
                p = c;
                c = 2 * p + 1;
                swapCnt++;
            }
        }
    }
    public static int[] heap(int [] list){

        int last,n,i;
        n = list.length;last = list.length - 1;
        for ( i =n/2-1; i >= 0 ; i--) {
            siftDown(list,i,n);
        }

        while (last >= 1){
            trace(list,last+1);
            swap(list,0,last);
            siftDown(list,0,last);
            last--;

        }
        trace(list,1);
        return list;

    }

   static int [] mergeList( int [] left,int [] right){
        if(up) {
            int i = 0;
            int j = 0;
            int t = 0;
            int k = left.length;
            int l = right.length;
            int[] temp = new int[k + l];

            while (i < k && j < l) {
                if (left[i] <= right[j]) temp[t] = left[i++];
                else temp[t] = right[j++];
                t++;
                swapCnt += 2;
                compareCnt++;
            }
            while (i < k) {
                temp[t++] = left[i++];
                swapCnt += 2;
            }
            while (j < l) {
                temp[t++] = right[j++];
                swapCnt += 2;
            }
            trace(temp, 0);
            return temp;
        }
        else {
            int i = 0;
            int j = 0;
            int t = 0;
            int k = left.length;
            int l = right.length;
            int[] temp = new int[k + l];

            while (i < k && j < l) {
                if (left[i] >= right[j]) temp[t] = left[i++];
                else temp[t] = right[j++];
                t++;
                swapCnt += 2;
                compareCnt++;
            }
            while (i < k) {
                temp[t++] = left[i++];
                swapCnt += 2;
            }
            while (j < l) {
                temp[t++] = right[j++];
                swapCnt += 2;
            }
            trace(temp, 0);
            return temp;
        }

    }
    public static int[] getSlice(int[] array, int startIndex, int endIndex)
    {
// Get the slice of the Array
        int[] slicedArray = new int[endIndex - startIndex];
//copying array elements from the original array to the newly created sliced array
        for (int i = 0; i < slicedArray.length; i++)
        {
            slicedArray[i] = array[startIndex + i];
        }
//returns the slice of an array
        return slicedArray;
    }
    public static int[] merge(int[] list){
       int middle;
        if(list.length <= 1) return list;

        if(list.length-1 % 2 == 0)  middle = (list.length-1)/2;
        else middle = (list.length-1)/2 + 1 ;
        trace(list,middle);
        int [] left = merge(getSlice(list,0,middle));

        int [] right = merge(getSlice(list,middle, list.length));


         return mergeList(left,right);

    }
    public static void quick(ArrayX list){}
    public static void radix(ArrayX list){}
    public static void bucket(ArrayX list){}



    public static void main(String[] args) {
            try {
               traceActivated =false;

                Scanner sc = new Scanner(System.in);

                while (sc.hasNextLine()){
                    String [] nxt2 = sc.nextLine().split(" +");
                    String [] nxt = sc.nextLine().split(" +");
                    tempo = new int[nxt.length];
                    for (;brojac < nxt.length;brojac++){
                        tempo[brojac] = Integer.parseInt(nxt[brojac]);
                        edin.push(Integer.parseInt(nxt[brojac]));
            }

                        if(nxt2[0].equals("trace"))traceActivated=true;

                    if(nxt2[2].equals("up"))up=true;
                    else up=false;
                        switch (nxt2[1]){
                            case "select":

                                if(traceActivated) {
                                    trace(tempo,0);
                                    tempo = select(tempo);

                                }
                                else {
                                    compareCnt=0;swapCnt=0;
                                    tempo = select(tempo);
                                    System.out.printf("%d %d | ",swapCnt,compareCnt);
                                    compareCnt=0;swapCnt=0;
                                    tempo = select(tempo);
                                    System.out.printf("%d %d | ",swapCnt,compareCnt);
                                    up=!up;
                                    compareCnt=0;swapCnt=0;
                                    tempo = select(tempo);
                                    System.out.printf("%d %d",swapCnt,compareCnt);

                                }
                                break;
                            case "insert":
                                if(traceActivated) {
                                    trace(tempo,0);
                                    tempo = insert(tempo);

                                }
                                else {
                                    compareCnt=0;swapCnt=0;
                                    tempo = insert(tempo);
                                    System.out.printf("%d %d | ",swapCnt,compareCnt);
                                    compareCnt=0;swapCnt=0;
                                    tempo = insert(tempo);
                                    System.out.printf("%d %d | ",swapCnt,compareCnt);
                                    up=!up;
                                    compareCnt=0;swapCnt=0;
                                    tempo = insert(tempo);
                                    System.out.printf("%d %d",swapCnt,compareCnt);

                                }
                                break;
                            case "bubble":
                                if(traceActivated) {
                                    trace(tempo,0);
                                    tempo = bubble(tempo, tempo.length);
                                }
                                else {
                                    compareCnt=0;swapCnt=0;
                                    tempo = bubble(tempo, tempo.length);
                                    System.out.printf("%d %d | ",swapCnt,compareCnt);
                                    compareCnt=0;swapCnt=0;
                                    tempo = bubble(tempo, tempo.length);
                                    System.out.printf("%d %d | ",swapCnt,compareCnt);
                                    up=!up;
                                    compareCnt=0;swapCnt=0;
                                    tempo = bubble(tempo, tempo.length);
                                    System.out.printf("%d %d",swapCnt,compareCnt);

                                }
                                break;
                            case "heap":

                                if(traceActivated) {
                                    trace(tempo,0);
                                    tempo = heap(tempo);

                                }
                                else {
                                    compareCnt=0;swapCnt=0;
                                    tempo = heap(tempo);
                                    System.out.printf("%d %d | ",swapCnt,compareCnt);
                                    compareCnt=0;swapCnt=0;
                                    tempo = heap(tempo);
                                    System.out.printf("%d %d | ",swapCnt,compareCnt);
                                    up=!up;
                                    compareCnt=0;swapCnt=0;
                                    tempo = heap(tempo);
                                    System.out.printf("%d %d",swapCnt,compareCnt);

                                }
                                break;
                                case "merge":
                                    if(traceActivated) {
                                        trace(tempo,0);
                                        tempo = merge(tempo);

                                    }
                                    else {
                                        compareCnt=0;swapCnt=0;
                                        tempo = merge(tempo);
                                        System.out.printf("%d %d | ",swapCnt,compareCnt);
                                        compareCnt=0;swapCnt=0;
                                        tempo = merge(tempo);
                                        System.out.printf("%d %d | ",swapCnt,compareCnt);
                                        up=!up;
                                        compareCnt=0;swapCnt=0;
                                        tempo = merge(tempo);
                                        System.out.printf("%d %d",swapCnt,compareCnt);

                                    }

                                break;
                            case "quick":


                              //  tempo = heap(tempo);
                                break;
                            case "radix":


                               // tempo = heap(tempo);
                                break;
                            case "bucket":


                              //  tempo = heap(tempo);
                                break;
                            default:
                                System.out.print("");
                        }


                    edin = new ArrayX();
                    brojac = 0;compareCnt=0;swapCnt=0;up=true;



                }
//                for (int i = 0; i < tempo.length; i++) {
//                    System.out.print(tempo[i] + " ");
//                }
                sc.close();


            }
            catch (Exception e){
                System.out.println(e);
            }

    }
}
