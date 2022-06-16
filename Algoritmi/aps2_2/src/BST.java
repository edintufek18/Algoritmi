public class BST {
     public int premik = 0;
     public boolean param = false;
      public  boolean isLeft = false ;

    class Node {
        int key;
        Node left, right;

        int count;

        public Node(int item) {
            this.key = item;
            left = right = null;
            this.count = 1;
        }
    }
    Node root;
    BST() { root = null; }

    BST(int value) { root = new Node(value); }

    Node insertRec(Node root, int key)
    {


        if (root == null) {
            root = new Node(key);
            return root;
        }
        if(root.key == key){
            premik++;
            (root.count)++;
            return root;
        }

        else if (key < root.key){
            premik++;
            root.left = insertRec(root.left, key);
        }
        else if (key > root.key) {
            premik++;
            root.right = insertRec(root.right, key);
        }


        return root;
    }
    void insert(int key){ root = insertRec(root, key); }



    boolean containsNodeRecursive(Node current, int value) {
        if (current == null)
            return false;
        premik++;
        if (value == current.key)
            return true;

        return value < current.key ? containsNodeRecursive(current.left, value) : containsNodeRecursive(current.right, value);
    }


    boolean find(int key){ return
        containsNodeRecursive(root,key);
        }

    int [] minValue(Node root)
    {
        int minv = root.key;
        int counter = root.count;
        while (root.left != null)
        {
            minv = root.left.key;
            counter = root.left.count;
            root = root.left;
        }
        return new int []{minv,counter};
    }
    int [] maxValue(Node root)
    {
        int maxv = root.key;
        int counter = root.count;
        while (root.right != null)
        {
            maxv = root.right.key;
            counter = root.right.count;
            root = root.right;
        }
        return new int []{maxv,counter};
    }
    public void addPremik(){
        if(param) premik++;
    }

    Node deleteRec(Node root, int key) {
        if (root == null)
            return root;

        if (key < root.key) {
            addPremik();
            root.left = deleteRec(root.left, key);
        }
        else if (key > root.key) {
            addPremik();
            root.right = deleteRec(root.right, key);
        }
        else {
            addPremik();
            if(root.count > 1 && param){

                (root.count)--;
                return root;
            }
            if (root.left == null) {

                return root.right;
            }
            else if (root.right == null) {

                return root.left;
            }

            param = false;
            isLeft = !isLeft;
            if(isLeft){

                int [] denim = maxValue(root.left);
                root.key = denim[0];
                root.count = denim[1];
                root.left = deleteRec(root.left, root.key);
            }
            else {
               // isLeft = true;
                int [] denim = minValue(root.right);
                root.key = denim[0];
                root.count = denim[1];
                root.right = deleteRec(root.right, root.key);
            }


        }

        return root;
    }

    void delete(int key){
        param = true;

        root = deleteRec(root, key);

    }

    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.key);
            inorderRec(root.right);
        }
    }
    void printInorder(){inorderRec(root);};

    void preorderRec(Node root) {
        if (root != null) {
            System.out.println(root.key);
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }
    void printPreorder(){preorderRec(root);};
    void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.println(root.key);
        }
    }

    void printPostorder(){postorderRec(root);};
    void printNodeComparisons(){
        System.out.println(premik);
    }



}
