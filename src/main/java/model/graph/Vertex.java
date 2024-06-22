package model.graph;

public interface Vertex<V> {
    V getElement();
    Vertex<V> clone();
}