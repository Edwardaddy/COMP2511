package unsw.collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.fruit.Apple;
import unsw.fruit.Fruit;
import unsw.fruit.Orange;
import java.util.*;

class ArrayListSetTest {

    @Test
    void testBasics() {
        Set<String> set = new ArrayListSet<>();
        set.add("Hello");
        set.add("World");
        assertTrue(set.contains("Hello"));
        assertTrue(set.contains("World"));

        set.remove("Hello");
        assertFalse(set.contains("Hello"));
        assertTrue(set.contains("World"));
    }

    
    @Test
    void testSubsetOf() {
        Set<Fruit> fruit = new ArrayListSet<Fruit>();
        fruit.add(new Apple("Gala"));
        fruit.add(new Apple("Fuji"));
        fruit.add(new Orange("Navel"));

        Set<Apple> apples = new ArrayListSet<>();
        apples.add(new Apple("Gala"));
        apples.add(new Apple("Fuji"));

        assertTrue(apples.subsetOf(fruit));
        assertFalse(fruit.subsetOf(apples));

        fruit.remove(new Apple("Fuji"));

        assertFalse(apples.subsetOf(fruit));
    }

    @Test
    void testSubset2() {
        Set<Fruit> fruit = new ArrayListSet<Fruit>();
        fruit.add(new Apple("Gala"));
        fruit.add(new Apple("Fuji"));
        fruit.add(new Orange("Navel"));

        Set<Object> emptySet = new ArrayListSet<Object>();
        assertTrue(emptySet.subsetOf(fruit));
        assertFalse(fruit.subsetOf(emptySet));
    }


    @Test
    void testSubset3() {
        Set<Fruit> fruit = new ArrayListSet<Fruit>();
        fruit.add(new Apple("Gala"));
        fruit.add(new Apple("Fuji"));
        fruit.add(new Orange("Navel"));

        Set<Apple> apples = new ArrayListSet<>();
        apples.add(new Apple("Gala"));
        apples.add(new Apple("Fuji"));

        assertTrue(apples.subsetOf(fruit));
    }

    @Test
    void testIterator() {
        List<String> strings = new ArrayList<>(
                Arrays.asList("I'm","in", "a", "JUnit", "test", "in", "COMP2511"));
        Set<String> set = new ArrayListSet<>();
        for (String s : strings)
            set.add(s);

        for (String s : set)
            strings.remove(s);

        
        assertEquals(strings.size(), 1);
        assertEquals(strings.get(0), "in");
    }

    @Test
    void testUnion() {
        Set<Fruit> fruit = new ArrayListSet<Fruit>();
        fruit.add(new Apple("Gala"));
        fruit.add(new Apple("Fuji"));
        fruit.add(new Orange("Navel"));

        Set<Orange> oranges = new ArrayListSet<>();
        oranges.add(new Orange("yo"));
        oranges.add(new Orange("hik"));
        oranges.add(new Orange("voo"));

        Set<Fruit> combined = fruit.union(oranges);
        assertEquals(combined.size(), 6);

        for (Fruit f : fruit) {
            assertTrue(combined.contains(f));
        }

        for (Fruit f : oranges) {
            assertTrue(combined.contains(f));
        }

        oranges.add(new Orange("yo"));
        combined = fruit.union(oranges);
        assertEquals(combined.size(), 6);
    }

    @Test
    void testUnion2() {
        Set<Fruit> fruit = new ArrayListSet<Fruit>();
        fruit.add(new Apple("Gala"));
        fruit.add(new Apple("Fuji"));
        fruit.add(new Orange("Navel"));

        Set<Orange> oranges = new ArrayListSet<>();
        oranges.add(new Orange("yo"));
        oranges.add(new Orange("ji"));
        oranges.add(new Orange("fyu"));

        Set<Fruit> combined = fruit.union(oranges);
        assertEquals(combined.size(), 6);
    }

    @Test
    void testIntersection() {
        Set<Fruit> fruit = new ArrayListSet<Fruit>();
        fruit.add(new Apple("Gala"));
        fruit.add(new Apple("Fuji"));
        fruit.add(new Orange("Navel"));

        Set<Apple> apples = new ArrayListSet<>();
        apples.add(new Apple("Gala"));
        apples.add(new Apple("Fuji"));
        apples.add(new Apple("Granny Smith"));

        Set<Fruit> combined = fruit.intersection(apples);
        assertEquals(combined.size(), 2);

        for (Fruit f : combined) {
            assertTrue(fruit.contains(f) && apples.contains(f));
        }

        apples.remove(new Apple("Gala"));
        apples.remove(new Apple("Fuji"));
        combined = fruit.intersection(apples);
        assertEquals(combined.size(), 0);
    }

    @Test
    void testIntersection2() {
        Set<Fruit> fruit = new ArrayListSet<Fruit>();
        fruit.add(new Apple("Gala"));
        fruit.add(new Apple("Fuji"));
        fruit.add(new Orange("Navel"));

        Set<Apple> apples = new ArrayListSet<>();
        apples.add(new Apple("Gala"));
        apples.add(new Apple("Fuji"));
        apples.add(new Apple("uo"));

        Set<Fruit> combined = fruit.intersection(apples);
        assertEquals(combined.size(), 2);
    }

    @Test
    void testEquality() {
        Set<Fruit> fruit = new ArrayListSet<Fruit>();
        fruit.add(new Apple("Gala"));
        fruit.add(new Apple("Fuji"));

        Set<Apple> apples = new ArrayListSet<>();
        apples.add(new Apple("Gala"));
        apples.add(new Apple("Fuji"));

        assertTrue(fruit.equals(apples));
        assertTrue(apples.equals(fruit));

        Set<Apple> apples2 = new ArrayListSet<>();
        apples2.add(new Apple("Gala"));
        apples2.add(new Apple("Fuji"));

        assertTrue(apples.equals(apples2));
        assertTrue(fruit.equals(apples2));

        Set<Apple> qq = new ArrayListSet<>();
        Set<Orange> empty = new ArrayListSet<>();
        assertTrue(qq.equals(empty));
    }

}
