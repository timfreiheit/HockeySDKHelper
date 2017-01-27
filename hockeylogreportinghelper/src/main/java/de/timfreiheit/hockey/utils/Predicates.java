package de.timfreiheit.hockey.utils;

public class Predicates {

    public static <T> Predicate<T> TRUE() {
        return new Predicate<T>() {
            @Override
            public boolean apply(T t) {
                return true;
            }
        };
    }

}
