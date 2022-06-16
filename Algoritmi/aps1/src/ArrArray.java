import java.lang.annotation.ElementType;

public class ArrArray {
    int caps = 1;
    boolean isThereDeleted = false;
    Element[][] mainArr;

    ArrArray() {
        mainArr = new Element[caps][];
    }

    public void resize() {
        caps++;
        Element[][] tempArray = new Element[caps][];
        System.arraycopy(mainArr,0,tempArray,0,mainArr.length);
        mainArr = tempArray;
    }

    public  Element[] insertSort(Element[] list){

            for (int i = 1; i < list.length; i++) {
                Element k = new Element(list[i]);

                int j = i;
                while (j > 0 && list[j-1].num > k.num ){
                    list[j] = list[j-1];
                    j = j - 1;

                }
                list[j] = k;

            }
            return list;
        }




    public Element[] mergeArr(Element[] left, Element[] right) {
        Element[] temp = new Element[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            if (right[j].num > left[i].num) temp[k++] = left[i++];
            else temp[k++] = right[j++];
        }
        while (i < left.length) {
            temp[k++] = left[i++];
        }
        while (j < right.length) {
            temp[k++] = right[j++];
        }
        return temp;
    }




    int binarySearch(Element arr[], int l, int r, int x) {
        if (r >= l) {
            int mid = l + (r - l) / 2;

            if (arr[mid].num == x)
                return mid;

            if (arr[mid].num > x)
                return binarySearch(arr, l, mid - 1, x);

            return binarySearch(arr, mid + 1, r, x);
        }
        return -1;
    }





    public int [] searchIndex(int e){
        for (int i = 0; i < mainArr.length; i++) {
            if(mainArr[i] == null) continue;
            int tmep = binarySearch(mainArr[i],0,mainArr[i].length-1,e);

            if(tmep != -1) return new int[]{i,tmep};

        }
        return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
    }



    public boolean isEmpty(){
        for (int i = 0; i < mainArr.length; i++) {
            if(mainArr[i] == null) continue;
            for (int j = 0; j < mainArr[i].length; j++) {
                if(mainArr[i][j].count == 0) return true;
            }
        }
        return false;
    }

    public boolean isEmptyRow(int i){
        for (int j = 0; j < mainArr[i].length; j++) {
            if(mainArr[i][j].count > 0) return false;
        }
        return true;
    }



        public void insert ( int e){
            int [] table = searchIndex(e);
            if(table[0] != Integer.MIN_VALUE){
                mainArr[table[0]][table[1]].count++;
                return;
            }

            Element[] A = new Element[]{new Element(e)};
            int i = 0;
            while (A != null) {
                if(mainArr.length == i){
                    resize();

                }
                if (mainArr[i] == null) {
                    mainArr[i] = A;
                    A = null;
                }
                else if(isThereDeleted){
                    for (int j = 0; j < mainArr[i].length; j++) {
                        if(mainArr[i][j].count == 0){
                             mainArr[i][j] = new Element(e);
                             A = null;
                             insertSort(mainArr[i]);
                             isThereDeleted = isEmpty();
                             break;
                        }
                    }
                    i++;
                }
                else {
                    A = mergeArr(mainArr[i], A);
                    mainArr[i] = null;
                    i++;

                }
            }

        }



        public void find ( int e){
            int [] table = searchIndex(e);
            if(table[0] != Integer.MIN_VALUE && mainArr[table[0]][table[1]].count != 0 )
                System.out.println("true");

            else System.out.println("false");
        }




        public void  delete ( int e){
            int [] table = searchIndex(e);
            if(table[0] != Integer.MIN_VALUE && mainArr[table[0]][table[1]].count != 0 ) {
                mainArr[table[0]][table[1]].count--;
                if(isEmptyRow(table[0])) mainArr[table[0]] = null;
                isThereDeleted = isEmpty();
                System.out.println("true");
                return;
            }
            System.out.println("false");
        }



    public boolean isThereElement(){
        for (int i = 0; i < mainArr.length; i++) {
            if(mainArr[i] == null) continue;
            for (int j = 0; j < mainArr[i].length; j++) {
                if(mainArr[i][j].count > 0) return true;
            }
        }
        return false;
    }



        public void printOut () {
            if(!isThereElement()) {
                System.out.println("empty");
                return;
            }
            for (int i = 0; i <mainArr.length ; i++) {
                System.out.print("A_" + i + ": ");
                if (mainArr[i] == null) System.out.println("...");
                else{
                    for (int j = 0; j < mainArr[i].length; j++) {
                        if(mainArr[i][j].count == 0 && j == mainArr[i].length-1) System.out.print("x");
                        else if(mainArr[i][j].count > 0 && j == mainArr[i].length-1) System.out.print(mainArr[i][j].num + "/" + mainArr[i][j].count);
                        else if(mainArr[i][j].count > 0) System.out.print(mainArr[i][j].num + "/" + mainArr[i][j].count + ", ");
                        else if(mainArr[i][j].count == 0) System.out.print("x, ");





                    }
                    System.out.println();
                }
            }

        }




    }
