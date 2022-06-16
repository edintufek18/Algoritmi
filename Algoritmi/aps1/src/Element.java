public class Element {

    int num;
    int count;

    Element(int num){
        this.num = num;
        this.count  = 1;

    }
    Element(Element pops){
        this.num = pops.num;
        this.count  = pops.count;

    }


}

