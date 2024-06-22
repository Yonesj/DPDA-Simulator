package model.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AdjacencyMapGraph<V, E> implements Graph<V, E> {
    private class InnerVertex<V> implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> pos;
        private Map<Edge<E>, Vertex<V>> outgoing, incoming;

        /** Constructs a new InnerVertex instance storing the given element.*/
        public InnerVertex(V elem, boolean graphIsDirected) {
            element = elem;
            outgoing = new HashMap<>();
            if (graphIsDirected)
                incoming = new HashMap<>();
            else
                incoming = outgoing; // if undirected, alias outgoing map
        }

        private InnerVertex(InnerVertex<V> other){
            this.element = other.element;
            this.pos = other.pos;
            this.incoming = other.incoming;
            this.outgoing = other.outgoing;
        }

        /** Returns the element associated with the vertex.*/
        public V getElement() {
            return element;
        }

        /** Stores the position of this vertex within the graph's vertex list.*/
        public void setPosition(Position<Vertex<V>> p) {
            pos = p;
        }

        /** Returns the position of this vertex within the graph's vertex list.*/
        public Position<Vertex<V>> getPosition() {
            return pos;
        }

        /** Returns reference to the underlying map of outgoing edges.*/
        public Map<Edge<E>, Vertex<V>> getOutgoing() {
            return outgoing;
        }

        /** Returns reference to the underlying map of incoming edges.*/
        public Map<Edge<E>, Vertex<V>> getIncoming() {
            return incoming;
        }

        public Vertex<V> clone(){
            return new InnerVertex<>(this);
        }
    } //------------ end of InnerVertex class ------------

    /** An edge between two vertices.*/
    class InnerEdge<E> implements Edge<E> {
        private E inputSymbol;
        private E popSymbol;
        private E pushSymbol;
        private Position<Edge<E>> pos;
        private Vertex<V>[] endpoints;

        /** Constructs InnerEdge instance from u to v, storing the given element.*/
        public InnerEdge(Vertex<V> u, Vertex<V> v, E inputSymbol, E popSymbol, E pushSymbol) {
            this.inputSymbol = inputSymbol;
            this.popSymbol = popSymbol;
            this.pushSymbol = pushSymbol;
            this.endpoints = (Vertex<V>[]) new Vertex[]{u, v}; // array of length 2
        }

        @Override
        public E getInputSymbol() {
            return inputSymbol;
        }

        @Override
        public E getPopSymbol() {
            return popSymbol;
        }

        @Override
        public E getPushSymbol() {
            return pushSymbol;
        }

        /** Returns reference to the endpoint array.*/
        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }

        /** Stores the position of this edge within the graph's vertex list.*/
        public void setPosition(Position<Edge<E>> p) {
            pos = p;
        }

        /** Returns the position of this edge within the graph's vertex list.*/
        public Position<Edge<E>> getPosition() {
            return pos;
        }
    }

    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private PositionalList<Edge<E>> edges = new LinkedPositionalList<>();

    /** Constructs an empty graph (either undirected or directed).*/
    public AdjacencyMapGraph(boolean directed) {
        isDirected = directed;
    }

    /** Returns the number of vertices of the graph*/
    public int numVertices() {
        return vertices.size();
    }

    /** Returns the vertices of the graph as an iterable collection*/
    public Iterable<Vertex<V>> vertices() {
        return new Iterable<Vertex<V>>() {
            @Override
            public Iterator<Vertex<V>> iterator() {
                return vertices.iterator();
            }
        };
    }

    /** Returns the number of edges of the graph*/
    public int numEdges() {
        return edges.size();
    }

    /** Returns the edges of the graph as an iterable collection*/
    public Iterable<Edge<E>> edges() {
        return new Iterable<Edge<E>>() {
            @Override
            public Iterator<Edge<E>> iterator() {
                return edges.iterator();
            }
        };
    }

    /** Returns the number of edges for which vertex v is the origin.*/
    public int outDegree(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    /** Returns an iterable collection of edges for which vertex v is the origin.*/
    public Set<Edge<E>> outgoingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().keySet(); // edges are the keys in the adjacency map
    }

    /** Returns the number of edges for which vertex v is the destination.*/
    public int inDegree(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }

    /** Returns an iterable collection of edges for which vertex v is the destination.*/
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().keySet(); // edges are the keys in the adjacency map
    }

    /** Returns the edge from u to v, or null if they are not adjacent.*/
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) {
        InnerVertex<V> origin = validate(u);
        for (Map.Entry<Edge<E>, Vertex<V>> entry : origin.getOutgoing().entrySet()) {
            if (entry.getValue().equals(v)) {
                return entry.getKey();
            }
        }
        return null; // no edge from u to v
    }

    /** Returns the vertices of edge e as an array of length two.*/
    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    public Object[] getTransitionInfo(Vertex<V> origin, E inputSymbol, E popSymbol) {
        InnerVertex<V> originVert = validate(origin);
        Object[] info = new Object[2];

        if (outDegree(originVert) == 0) {
            return info;
        }

        for (Edge<E> edge : outgoingEdges(originVert)){
            if (edge.getInputSymbol().equals(inputSymbol) && edge.getPopSymbol().equals(popSymbol)) {
                info[0] = edge.getPushSymbol();
                info[1] = opposite(origin, edge);
                break;
            }
        }

        return info;
    }

    /** Returns the vertex that is opposite vertex v on edge e.*/
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if (endpoints[0].getElement().equals(v.getElement()))
            return endpoints[1];
        else if (endpoints[1].getElement().equals(v))
            return endpoints[0];
        else
            throw new IllegalArgumentException("v is not incident to this edge");
    }

    /** Inserts and returns a new vertex with the given element.*/
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> v = new InnerVertex<>(element, isDirected);
        v.setPosition(vertices.addLast(v));
        return v;
    }

    /** Inserts and returns a new edge between u and v, storing given element.*/
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E inputSymbol, E popSymbol, E pushSymbol) throws IllegalArgumentException {
        InnerEdge<E> e = new InnerEdge<>(u, v, inputSymbol, popSymbol, pushSymbol);
        e.setPosition(edges.addLast(e));
        InnerVertex<V> origin = validate(u);
        InnerVertex<V> dest = validate(v);

        origin.getOutgoing().put(e, v);
        dest.getIncoming().put(e, u);

        return e;
    }

    /** Removes a vertex and all its incident edges from the graph.*/
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        // remove all incident edges from the graph
        for (Edge<E> e : vert.getOutgoing().keySet()) {
            removeEdge(e);
        }
        for (Edge<E> e : vert.getIncoming().keySet()) {
            removeEdge(e);
        }
        // remove this vertex from the list of vertices
        vertices.remove(vert.getPosition());
    }

    /** Removes an edge from the graph.*/
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        InnerVertex<V> origin = validate(endpoints[0]);
        InnerVertex<V> dest = validate(endpoints[1]);

        origin.getOutgoing().remove(e);
        dest.getIncoming().remove(e);

        edges.remove(edge.getPosition());
    }

    /** Validates and returns the inner vertex instance.*/
    private InnerVertex<V> validate(Vertex<V> v) {
        if (!(v instanceof InnerVertex))
            throw new IllegalArgumentException("Invalid vertex");
        return (InnerVertex<V>) v;
    }

    /** Validates and returns the inner edge instance. */
    private InnerEdge<E> validate(Edge<E> e) {
        if (!(e instanceof InnerEdge))
            throw new IllegalArgumentException("Invalid edge");
        return (InnerEdge<E>) e;
    }
}
