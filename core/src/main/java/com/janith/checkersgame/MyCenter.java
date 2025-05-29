package com.janith.checkersgame;

@FunctionalInterface
public interface MyCenter<T,U,V,R>{
    R applay(T t, U u,V v);
}
//@FunctionalInterface
//interface TriFunction<T, U, V, R> {
//    R apply(T t, U u, V v);
//}
