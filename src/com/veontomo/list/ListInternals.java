package com.veontomo.list;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains info about various properties of List interface and some classes that implements it.
 */
public class ListInternals {
    /**
     * Get the {@code list}'s capacity
     * @param list
     * @return
     */
    private static int capacity(final List<?> list) {
        Field field = null;
        try {
            field = ArrayList.class.getDeclaredField("elementData");
            field.setAccessible(true);
            return ((Object[]) field.get(list)).length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main() {
        final List<Integer> list = new ArrayList<Integer>();
        var c = capacity(list);
        System.out.println("Start capacity: " + c + ", size: " + list.size());
        for (var i = 0; i < 10000; i++) {
            list.add(1);
            if (c != capacity(list)) {
                ((ArrayList<Integer>) list).trimToSize();
                c = capacity(list);
                System.out.println("New capacity: " + c + ", size: " + list.size());
            }

        }
    }
}
