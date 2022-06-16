

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ArrArrayTest {
    interface Fn {
        void apply();
    }

    static String captureStdout(Fn fn) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(buffer));
        fn.apply();
        System.setOut(stdout);
        return buffer.toString().replaceAll(System.lineSeparator(), "\n");
    }

    void runOperation(ArrArray arrArray, char op, String opStr) {
        switch (op) {
            case '+':
                arrArray.insert(Integer.parseInt(opStr));
                break;
            case '?':
                arrArray.find(Integer.parseInt(opStr));
                break;
            case '-':
                arrArray.delete(Integer.parseInt(opStr));
                break;
            case 'p':
                arrArray.printOut();
        }
    }

    void runOperations(ArrArray arrArray, String ops) {
        StringBuilder opStr = new StringBuilder();
        char op = 'x';
        for (int i = 0; i < ops.length(); i++) {
            char c = ops.charAt(i);
            if (Character.isDigit(c)) opStr.append(c);
            else {
                runOperation(arrArray, op, opStr.toString());
                opStr.setLength(0);
                op = c;
            }
        }
        runOperation(arrArray, op, opStr.toString());
    }

    void test(String ops, String expectedStdout) {
        ArrArray arrArray = new ArrArray();
        String capturedStdout = captureStdout(() -> runOperations(arrArray, ops));
        assertEquals(expectedStdout.trim(), capturedStdout.trim());
    }

    @DisplayName("Public test 1")
    @Test
    void testUcilnica1() {
        test("p+7+5+9+3+15+11p",
             "empty\n" +
             "A_0: ...\n" +
             "A_1: 11/1, 15/1\n" +
             "A_2: 3/1, 5/1, 7/1, 9/1");
    }

    @DisplayName("Public test 2")
    @Test
    void testUcilnica2() {
        test("+7+5+9+3+15+11" +
             "+17-15+11-5" +
             "p" +
             "?7?5?42",
             "true\n" +
             "true\n" +
             "A_0: 17/1\n" +
             "A_1: 11/2, x\n" +
             "A_2: 3/1, x, 7/1, 9/1\n" +
             "true\n" +
             "false\n" +
             "false");
    }

    @DisplayName("Public test 3")
    @Test
    void testUcilnica3() {
        test("+7+5+9+3+15+11" +
             "+17-15+11-5-11+22" +
             "+33+12+9" +
             "p" +
             "?11?9?42",
             "true\n" +
             "true\n" +
             "true\n" +
             "A_0: ...\n" +
             "A_1: ...\n" +
             "A_2: ...\n" +
             "A_3: 3/1, 7/1, 9/2, 11/1, 12/1, 17/1, 22/1, 33/1\n" +
             "true\n" +
             "true\n" +
             "false");
    }

    @DisplayName("Newly constructed ArrArrays shows up as empty")
    @Test
    void testBasicEmpty() {
        test("p", "empty");
    }

    @DisplayName("8 values inserted are sorted correctly")
    @ParameterizedTest
    @ValueSource(strings = {
        "+1+2+3+4+5+6+7+8p",
        "+8+7+6+5+4+3+2+1p",
        "+5+1+7+2+3+6+4+8p",
        "+6+1+2+8+7+5+3+4p",
    })
    void testBasicSorting(String ops) {
        test(ops, "A_0: ...\n" +
                  "A_1: ...\n" +
                  "A_2: ...\n" +
                  "A_3: 1/1, 2/1, 3/1, 4/1, 5/1, 6/1, 7/1, 8/1");
    }

    @DisplayName("Basic find")
    @Test
    void testBasicFind() {
        test("+6+3+1+8+9+5p" +
             "?3?6?4",
             "A_0: ...\n" +
             "A_1: 5/1, 9/1\n" +
             "A_2: 1/1, 3/1, 6/1, 8/1\n" +
             "true\n" +
             "true\n" +
             "false");
    }

    @DisplayName("Basic lazy delete")
    @Test
    void testBasicDelete() {
        test("+1+2+3+4-2-9-4p",
             "true\n" +
             "false\n" +
             "true\n" +
             "A_0: ...\n" +
             "A_1: ...\n" +
             "A_2: 1/1, x, 3/1, x");
    }

    @DisplayName("Tables with all deleted values show up as \"...\"")
    @Test
    void testDeletedOutput1() {
        test("+1+2+3+4+5+6+7-7-6-5p",
             "true\n" +
             "true\n" +
             "true\n" +
             "A_0: ...\n" +
             "A_1: ...\n" +
             "A_2: 1/1, 2/1, 3/1, 4/1");
    }

    @DisplayName("ArrArray with all deleted values shows up as empty")
    @Test
    void testDeletedOutput2() {
        test("+1+2+3+4-1-2-3-4p",
             "true\n" +
             "true\n" +
             "true\n" +
             "true\n" +
             "empty");
    }

    @DisplayName("Deleted values aren't found")
    @Test
    void testDeletedFind() {
        test("+1+2+3+4+5" +
             "-1?3?1-4?4",
             "true\n" +
             "true\n" +
             "false\n" +
             "true\n" +
             "false");
    }

    @DisplayName("Insertions into deleted spaces are handled correctly")
    @Test
    void testDeleteReinsert() {
        test("+2+5+3+6+1+11" +
             "-2-11-6p" +
             "+10+0+4p" +
             "-0+20+2p",
             "true\n" +
             "true\n" +
             "true\n" +
             "A_0: ...\n" +
             "A_1: 1/1, x\n" +
             "A_2: x, 3/1, 5/1, x\n" +
             "A_0: 10/1\n" +
             "A_1: 0/1, 1/1\n" +
             "A_2: 3/1, 4/1, 5/1, x\n" +
             "true\n" +
             "A_0: 10/1\n" +
             "A_1: 1/1, 20/1\n" +
             "A_2: 2/1, 3/1, 4/1, 5/1");
    }

    @DisplayName("Insert scans all tables")
    @Test
    void testIncrementCountBeforeInsert() {
        test("+10+11+12+13+10+1-1+10p",
             "true\n" +
             "A_0: ...\n" +
             "A_1: ...\n" +
             "A_2: 10/3, 11/1, 12/1, 13/1");
    }

    @DisplayName("Fully deleted tables are treated as empty when inserting")
    @Test
    void testInsertIntoDeletedTable() {
        test("+1+2+3+4+5+6+7-5-6p" +
             "+8p",
             "true\n" +
             "true\n" +
             "A_0: 7/1\n" +
             "A_1: ...\n" +
             "A_2: 1/1, 2/1, 3/1, 4/1\n" +
             "A_0: ...\n" +
             "A_1: 7/1, 8/1\n" +
             "A_2: 1/1, 2/1, 3/1, 4/1");
    }

    @DisplayName("Deleted values are still sorted")
    @Test
    void testDeletedSorting() {
        test("+10+20+30+40+50+60+70-10-20-30p" +
             "+25p+35p",
             "true\n" +
             "true\n" +
             "true\n" +
             "A_0: 70/1\n" +
             "A_1: 50/1, 60/1\n" +
             "A_2: x, x, x, 40/1\n" +
             "A_0: 70/1\n" +
             "A_1: 50/1, 60/1\n" +
             "A_2: x, 25/1, x, 40/1\n" +
             "A_0: 70/1\n" +
             "A_1: 50/1, 60/1\n" +
             "A_2: 25/1, x, 35/1, 40/1\n");
    }

    @DisplayName("Deleted tables at the end show up as empty")
    @Test
    void testDeletedEnd() {
        test("+1+2+3+4+5+6+7-1-2-3-4p",
             "true\n" +
             "true\n" +
             "true\n" +
             "true\n" +
             "A_0: 7/1\n" +
             "A_1: 5/1, 6/1\n" +
             "A_2: ...");
    }

    @DisplayName("Values can be inserted multiple times")
    @Test
    void testMultiInsert() {
        test("+1+2+3+1+1+3+2p",
             "A_0: 3/2\n" +
             "A_1: 1/3, 2/2");
    }

    @DisplayName("Values inserted multiple times merge correctly")
    @Test
    void testMultiInsertMerge() {
        test("+1+2+3+1+1+3+2+6+1+7+6p",
             "A_0: 7/1\n" +
             "A_1: ...\n" +
             "A_2: 1/4, 2/2, 3/2, 6/2");
    }

    @DisplayName("Values inserted multiple times are deleted correctly")
    @Test
    void testMultiInsertDelete() {
        test("+1+2+3+1+1+3+2+6+1+7+6" +
             "?8?2" +
             "-8-4-2-6-7-7-2p" +
             "?2?1?7",
             "false\n" +
             "true\n" +
             "false\n" +
             "false\n" +
             "true\n" +
             "true\n" +
             "true\n" +
             "false\n" +
             "true\n" +
             "A_0: ...\n" +
             "A_1: ...\n" +
             "A_2: 1/4, x, 3/2, 6/1\n" +
             "false\n" +
             "true\n" +
             "false");
    }
}
