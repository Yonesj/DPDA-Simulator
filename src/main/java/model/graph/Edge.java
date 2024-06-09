package model.graph;

public interface Edge<E> {
    E getInputSymbol();
    E getPopSymbol();
    E getPushSymbol();
}