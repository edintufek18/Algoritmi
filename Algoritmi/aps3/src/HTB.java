public class HTB {
    int p; // multiplicant
    int m; // length
    int c1; // constant 1
    int c2; // constant 2
    int[] table;
    int collisions = 0;

    public HTB(int p, int m, int c1, int c2) {
        this.p = p;
        this.m = m;
        this.c1 = c1;
        this.c2 = c2;
        this.table = new int[m];
    }

    int multiple_hash(int k) {
        return (k * p) % m;
    }
    void resize() {
        int[] temp = new int[table.length];
        System.arraycopy(table, 0, temp, 0, table.length);
        m = 2 * m + 1;
        table = new int[m];
        for (int i = 0; i < temp.length; i++) {
            insert(temp[i]);
        }

    }
    public int multiple_hash2(int key, int i){
        return (multiple_hash(key) + c1 * i + c2 * (int)(Math.pow(i, 2)))% m;
    }
    void insert(int key) {
        if (find(key)) return;

        if (table[multiple_hash(key)] == 0)
            table[multiple_hash(key)] = key;
        else {
            for (int i = 1; i < table.length +1 ; i++) {


                collisions++;
                if (table[multiple_hash2(key,i)] == 0) {
                    table[multiple_hash2(key,i)] = key;

                    return;
                }
            }

                resize();
                insert(key);

        }
    }
    boolean find(int key) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == key) return true;

        }
        return false;
    }
    void delete(int key) {

        if (find(key)) table[multiple_hash(key)] = 0;
    }
    void printKeys() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != 0) System.out.println(i + ": " + table[i]);
        }
    }
    void printCollisions() {

        System.out.println(collisions);
    }
}