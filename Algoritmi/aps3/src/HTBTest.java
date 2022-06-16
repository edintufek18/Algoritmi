

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class HTBTest {
    interface Fn {
        void apply();
    }

    String captureStdout(Fn fn) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(buffer));
        fn.apply();
        System.setOut(stdout);
        return buffer.toString().replace(System.lineSeparator(), "\n");
    }

    static final Pattern INIT_ARGS = Pattern.compile("(\\d+),\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)");

    HTB makeHTB(String initArgs) {
        Matcher matcher = INIT_ARGS.matcher(initArgs.trim());
        if (!matcher.matches()) throw new IllegalArgumentException();
        return new HTB(
            Integer.parseInt(matcher.group(1)),
            Integer.parseInt(matcher.group(2)),
            Integer.parseInt(matcher.group(3)),
            Integer.parseInt(matcher.group(4))
        );
    }

    void runOperation(HTB htb, char op, String opStr) {
        switch (op) {
            case '+':
                htb.insert(Integer.parseInt(opStr));
                break;
            case '?':
                System.out.println(htb.find(Integer.parseInt(opStr)));
                break;
            case '-':
                htb.delete(Integer.parseInt(opStr));
                break;
            case 'p':
                htb.printKeys();
                break;
            case 'c':
                htb.printCollisions();
                break;
            case 'o':
                System.out.println(htb);
                break;
            case '.':
                System.out.println("--");
                break;
        }
    }

    void runOperations(HTB htb, String ops) {
        for (String op : ops.split("(?=\\D)")) {
            runOperation(htb, op.charAt(0), op.substring(1));
        }
    }

    String captureStdout(HTB htb, String ops) {
        return captureStdout(() -> runOperations(htb, ops));
    }

    void test(HTB htb, String ops, String expectedStdout) {
        String capturedStdout = captureStdout(htb, ops);
        assertEquals(expectedStdout.trim(), capturedStdout.trim());
    }

    void test(String initArgs, String ops, String expectedStdout) {
        test(makeHTB(initArgs), ops, expectedStdout);
    }

    /*
    Delete is implemented using lazy deletion: the element is marked as deleted,
    and then skipped during find operations. During insert operations, it's treated
    the same as a null element.

    p, m, c1, c2 are given in the constructor.
    m is the size of the underlying array, and can change.
    h(k) = (k * p) % m
    h(k, i) = (h(k) + c1 * i + c2 * i * i) % m
    When finding an empty slot, insert scans all values of h(k, i) with 0 <= i < m.
    If it finds no empty slots, it resizes the underlying array and retries the insert.
    The collision counter is only updated in the insert() method.
     */

    @Test
    @DisplayName("Public test 1")
    void testUcilnica1() {
        test("7, 13, 5, 11",

             "+19+11+23?19." +
             "-19-29?19." +
             "+17+2+33p",

             "true\n" +
             "--\n" +
             "false\n" +
             "--\n" +
             "1: 2\n" +
             "2: 17\n" +
             "5: 23\n" +
             "10: 33\n" +
             "12: 11");
    }

    @Test
    @DisplayName("Public test 2")
    void testUcilnica2() {
        test("7, 13, 5, 11",

             "+19+11+23+19+29+17+2+33+99+129p.c",

             "1: 2\n" +
             "2: 17\n" +
             "3: 19\n" +
             "4: 99\n" +
             "5: 23\n" +
             "6: 129\n" +
             "8: 29\n" +
             "10: 33\n" +
             "12: 11\n" +
             "--\n" +
             "0");
    }

    @Test
    @DisplayName("Public test 3")
    void testUcilnica3() {
        test("7, 3, 5, 7",

             "+19+11+23+19+29+17+2+33+99+129p.c",

             "0: 129\n" +
             "2: 11\n" +
             "3: 99\n" +
             "6: 33\n" +
             "7: 17\n" +
             "8: 29\n" +
             "11: 23\n" +
             "13: 19\n" +
             "14: 2\n" +
             "--\n" +
             "34");
    }

    @Test
    @DisplayName("Hidden test 1")
    void testUcilnicaHidden1() {
        test("7, 3, 5, 7",

             "+19+11+23+19+29+17+2+33+99+129-11+11p.c",

             "0: 129\n" +
             "2: 11\n" +
             "3: 99\n" +
             "6: 33\n" +
             "7: 17\n" +
             "8: 29\n" +
             "11: 23\n" +
             "13: 19\n" +
             "14: 2\n" +
             "--\n" +
             "34");
    }

    @Test
    @DisplayName("Hidden test 2")
    void testUcilnicaHidden2() {
        test("7, 3, 5, 7",

             "+19+11+23+19+29+17+2+33+99+129-11+11-17+17-2+2p.c",

             "0: 129\n" +
             "2: 11\n" +
             "3: 99\n" +
             "6: 33\n" +
             "7: 17\n" +
             "8: 29\n" +
             "11: 23\n" +
             "13: 19\n" +
             "14: 2\n" +
             "--\n" +
             "36");
    }

    @Test
    @DisplayName("Hidden test 3")
    void testUcilnicaHidden3() {
        test("11, 13, 5, 7",

             "+19+11+23+19+29+17+2+33+99+129-11+17-17+2-2+11p." +
             "?129-129?129+119+211+123+159+129+217+12+233+299+429p.c",

             "1: 19\n" +
             "2: 129\n" +
             "4: 11\n" +
             "6: 23\n" +
             "7: 29\n" +
             "10: 99\n" +
             "12: 33\n" +
             "--\n" +
             "true\n" +
             "false\n" +
             "0: 233\n" +
             "3: 123\n" +
             "6: 429\n" +
             "7: 299\n" +
             "9: 99\n" +
             "10: 23\n" +
             "11: 217\n" +
             "12: 33\n" +
             "13: 11\n" +
             "15: 129\n" +
             "20: 19\n" +
             "21: 159\n" +
             "22: 29\n" +
             "24: 12\n" +
             "25: 119\n" +
             "26: 211\n" +
             "--\n" +
             "38");
    }
}
