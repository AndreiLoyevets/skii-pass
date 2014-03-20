package com.epam.andrii_loievets.skipass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MyArrayListTest {

    @Test
    public void ensureCapacity_Add2ElementsTocapacity1_Capacity2() {
        MyArrayList<Integer> list = new MyArrayList<>(1);

        list.add(1);
        list.add(2);

        assertEquals("", 4, list.getCapacity());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void add_IndexOutOfBounds_ThrowsException() {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(20, 1);
    }

    @Test
    public void add_AddToBeginning_FirstElementBecomesSecond() {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(1);
        list.add(0, 2);

        assertEquals("First element not shifted when adding to the beginning",
                new Integer(1), list.get(1));
    }

    @Test
    public void add_AddToEnd_LastElementBecomesBeforeLast() {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        list.add(3, 4);

        assertEquals("Last element changed after appending the element",
                new Integer(3), list.get(2));
    }

    @Test
    public void indexOf_ElementNotInList_ReturnsMinus1() {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(1);

        assertEquals("Non-existing element found in the list",
                -1, list.indexOf(2));
    }

    @Test
    public void contains_ElementNotInList_ReturnsFalse() {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(1);

        assertFalse("Non-existing element found in the list", list.contains(2));
    }

    @Test
    public void contains_ElementInList_ReturnsTrue() {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        assertTrue("2 not found in the list", list.contains(2));
    }

    @Test
    public void get_Add1Turnstile_ReturnsTurnstile() {
        MyArrayList<Turnstile> list = new MyArrayList<>();

        list.add(new Turnstile());

        assertNotNull("First turnstile not found", list.get(0));
    }
}
