package vn.rideshare;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@DisplayName("Demo test")
@SpringBootTest
class RideShareBackendApplicationTests {

    private int add(int a, int b) {
        return a + b;
    }

    private double[] doubleArray() {
        double[] arr = {1.0, 2.0, 3.0};
        return arr;
    }

    @Test
    void standardAssertions() {
        assertEquals(2, add(1, 1));
        assertNotEquals(3, add(2, 2));
        assertTrue(4 < add(3, 3), () -> "assert true test");
        assertFalse(3 < add(1, 1), () -> "assert false test");
        double[] arr = {1.0, 2.0, 3.0};
        assertArrayEquals(arr, doubleArray());

        String one = "one";
        String stillOne = "one";
        assertSame(one, stillOne);

        String two = new String("two");
        String notSameTwo = "two";
//        assertSame(two, notSameTwo);

        assertNotSame(two, notSameTwo);
        assertNull(null, "assert null");
        assertNotNull(new String(), () -> "assert not null");
    }

    @Test
    void standardAssertions2() {
        assertThrows(Exception.class, () -> {throw new RuntimeException();}, () -> "assert run time exception");
        assertDoesNotThrow(() -> {
            System.out.println("No exception");
        }, () -> "assert does not throw");

//        assertThrowsExactly()
    }

    @Nested
//    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Underscore_name {
        @Test
        void if_it_is_zero() {

        }

        //        @DisplayName("My test name")
        @ParameterizedTest(name = "For example, year {0} is not supported")
        @ValueSource(ints = {-1, -4})
        void if_it_is_negative(int year) {

        }
    }

    @Nested
    @IndicativeSentencesGeneration(separator = "->")
    class Arrow_name {
        @Test
        void if_it_is_divisible_by_4_but_not_by_100() {
        }

        @ParameterizedTest(name = "Year {0} is a leap year")
        @ValueSource(ints = {2016, 2020, 2024})
        void if_it_is_one_of_the_following_year(int year) {

        }

    }

    @Mock
    static LinkedList list;
    @Mock
    static LinkedList mockList;
    @Mock
    static List singleMock;
    @Mock
    static List firstMock;
    @Mock
    static List secondMock;

    @BeforeAll
    static void initAll() {
    }

    //    @DisplayName("Before alll")
    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("tearDown");
    }

    //    @DisplayName("TEST 1")
    @Test
    @Order(0)
    void test1() {
        list.add(1);
        list.add(2);
        verify(list).add(1);
        verify(list).add(2);
        verify(list).add(3);
        list.clear();
    }

    @Test
    @Order(1)
    @DisplayName("╯°□°）╯")
    void test2() {
        when(list.get(anyInt())).thenReturn("mock value");
        System.out.println(list.get(0));
        verify(list).get(anyInt());
        verify(list).get(0);
        when(list.get(3)).thenReturn("mock First value");
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        list.add(3);
        verify(list).get(3);
        verify(list).get(2);
        verify(list).add(3);
        when(list.contains(anyInt())).thenReturn(true);
        System.out.println(list.contains(4));
        list.clear();
    }

    @Test
    @Order(2)
    @DisplayName("╯°□°）╯")
    void test3() {
        verifyNoInteractions(list);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        verify(list, atLeastOnce()).add(1);
        verify(list, times(2)).add(2);
        verify(list, never()).add(0);
//        verify(list, atMost(1)).add(3);
        mockList.add(0);
        verify(mockList, only()).add(0);
        verifyNoMoreInteractions(mockList);
    }

    @Test
    @Order(3)
//    @DisplayName(🥦)
    void test4() {
        doThrow(new RuntimeException("mock exception")).when(list).get(0);
        doThrow(new RuntimeException("mock exception")).when(list).clear();
        when(list.get(1)).thenReturn("mock value");
        System.out.println(list.get(2));
        System.out.println(list.get(1));
//        System.out.println(list.get(0));
//        list.clear();
        mockList.add(0);
//        verify(mockList, only()).add(0);
        verifyNoMoreInteractions(mockList);
//        System.out.println(mockList);
    }

    @Test
    @Order(4)
    void test5() {
        singleMock.add("one");
        singleMock.add("two");
        singleMock.get(2);
        InOrder inOrder = inOrder(singleMock);
        inOrder.verify(singleMock).add("one");
        inOrder.verify(singleMock).add("two");
        inOrder.verify(singleMock).get(2);
    }

    @Test
    @Order(5)
    void test6() {
        verifyNoInteractions(firstMock, secondMock);
        firstMock.add("first");
        secondMock.add("second");
        InOrder inOrder = inOrder(secondMock, firstMock);

        inOrder.verify(firstMock).add("first");
        inOrder.verify(secondMock).add("second");
        inOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(firstMock);
        verifyNoMoreInteractions(secondMock);
    }

    @Test
    @Order(6)
    void test7() {
        when(list.get(anyInt()))
                .thenReturn(false, 0, "three");
//                .thenThrow(new RuntimeException("first throw exception"));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));

        when(list.get(anyInt()))
                .thenThrow(new RuntimeException("override exception"))
                .thenThrow(new RuntimeException("not call exception"));
        list.get(5);
        list.get(6);
    }

    @Test
    @Order(7)
    void test8() {
        when(mockList.get(anyInt())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocationOnMock) {
                Object args[] = invocationOnMock.getArguments();
                Object mock = invocationOnMock.getMock();
                return "args " + Arrays.toString(args);
            }
        });

        System.out.println(mockList.get(000));
    }

    @Test
    @Order(8)
    void test9() {
        doThrow(new RuntimeException("void method"))
                .when(list).clear();
        doReturn(true)
                .when(list).add(anyInt());
        doCallRealMethod().when(list).add(1);
//        doNothing().when(list).clear();
        System.out.println(list.add(6));
        System.out.println(list.add(7));
        System.out.println(list.get(0));
        list.clear();
    }

    @Test
    @Order(9)
    void test10() {
        List linkedList = new LinkedList();
        List spy = spy(linkedList);
        when(spy.size()).thenReturn(100);
        spy.add("one");
        spy.add("two");
        System.out.println(spy.get(1));
        System.out.println(spy.size());
        verify(spy).add("one");
        verify(spy).size();
        verify(spy).add("two");
//        verify(spy).clear();
//        System.out.println(linkedList.size());
        verifyNoMoreInteractions(spy);
    }

    @Test
    @Order(10)
    void test11() {
        List linkedList = new LinkedList();
        List spy = spy(linkedList);

//        when(spy.get(0)).thenReturn(false);
        doReturn(false).when(spy).get(anyInt());
        System.out.println(spy.get(0));
        verify(spy).get(anyInt());
    }

    @Test
    @Order(11)
    void test12() {
        List mock = mock(List.class);
        when(mock.size()).thenReturn(100);
        System.out.println(mock.size());
        verify(mock).size();
        reset(mock);
        verify(mock).size();
        System.out.println(mock.size());
    }
}
