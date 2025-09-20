package mst.model;

import java.util.*;

/**
 * A graph.
 *
 * @param <T> the type of data held in nodes of the graph
 */
public class Graph<T> {
    private final List<Node<T>> nodes = new ArrayList<>();
    private final List<Edge<T>> edges = new ArrayList<>();
    private long modCount = 0L;

    /**
     * Constructs an empty graph.
     */
    public Graph() {
    }

    /**
     * Gets the edges of this graph.
     *
     * @return the edges of this graph
     */
    public List<Edge<T>> getEdges() {
        return edges;
    }

    private void addEdge(Edge<T> edge) {
        modCount++;
        edges.add(edge);
    }

    /**
     * Adds an edge to this graph.
     *
     * @param node1  the node at one end of the edge
     * @param node2  the node at the other end of the edge
     * @param weight the weight of the edge
     */
    public void addEdge(Node<T> node1, Node<T> node2, int weight) {
        addEdge(new Edge<>(node1, node2, weight));
    }

    /**
     * Gets an iterator that provides edges according to Kruskal's Algorithm.
     * The iterator provides edges of increasing weight that would join distinct
     * subtrees into a single minimum spanning tree.
     *
     * @return the iterator
     */
    public Iterator<Edge<T>> getKruskalIterator() {
        return new KruskalIterator(getEdges());
    }

    private class KruskalIterator implements Iterator<Edge<T>> {
        private final PriorityQueue<Edge<T>> priorityQueue;
        private Edge<T> nextEdge;
        private final long expectedModCount;

        KruskalIterator(List<Edge<T>> edges) {
            this.expectedModCount = Graph.this.modCount;
            this.priorityQueue = new PriorityQueue<>(new ArrayList<>(edges));
            for (Node<T> node : Graph.this.getNodes()) {
                node.parent = node;
                node.rank = 0;
            }
            findNextEdge();
        }

        private void findNextEdge() {
            while (!priorityQueue.isEmpty()) {
                Edge<T> edge = priorityQueue.poll();
                Node<T> node1 = edge.getNode1().find();
                Node<T> node2 = edge.getNode2().find();
                if (node1 != node2) {
                    nextEdge = edge;
                    node1.union(node2);
                    return;
                }
            }
            nextEdge = null;
        }

        @Override
        public boolean hasNext() {
            if (expectedModCount != Graph.this.modCount) {
                throw new ConcurrentModificationException();
            }
            return nextEdge != null;
        }

        @Override
        public Edge<T> next() {
            if (expectedModCount != Graph.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (nextEdge == null) {
                throw new NoSuchElementException();
            }
            Edge<T> currentEdge = nextEdge;
            findNextEdge();
            return currentEdge;
        }
    }

    /**
     * Creates a new node with the specified data value.
     *
     * @param data the data value
     */
    public void addNode(T data) {
        modCount++;
        nodes.add(new Node<>(data));
    }

    /**
     * Gets the nodes in this graph in the order they were added.
     *
     * @return the nodes in this graph
     */
    public List<Node<T>> getNodes() {
        return nodes;
    }

    /**
     * A graph node with an immutable data value and support for disjoint
     * set operations.
     *
     * @param <T> the type of the data value
     */
    public static class Node<T> {
        private final T data;
        private Node<T> parent;
        private int rank = 0;

        /**
         * Creates a node with the specified data value.
         *
         * @param data the data value
         */
        public Node(T data) {
            this.data = data;
            this.parent = this;
        }

        /**
         * Gets the data value associated with this node.
         *
         * @return the data value
         */
        public T getData() {
            return data;
        }

        /**
         * Finds the representative of the set to which this node belongs.
         *
         * @return the representative
         */
        public Node<T> find() {
            if (parent == this) {
                return this;
            }
            // For now, don't use path compression.
            return parent.find();
        }

        /**
         * Merges the two sets to which this node and the other node belong.
         *
         * @param other the other node
         */
        public void union(Node<T> other) {
            Node<T> thisRep = this.find();
            Node<T> otherRep = other.find();

            // If they're already in the same set, stop.
            if (thisRep == otherRep) {
                return;
            }

            // Choose the node with the larger rank as the parent of the other.
            if (thisRep.rank > otherRep.rank) {
                otherRep.parent = thisRep;
            } else if (thisRep.rank < otherRep.rank) {
                thisRep.parent = otherRep.parent;
            } else {
                // Arbitrarily choose a node to be the parent of the other.
                otherRep.parent = thisRep;
                thisRep.rank++;
            }
        }

        @Override
        public String toString() {
            if (getData() instanceof Station station) {
                if (this == this.find()) {
                    return station.getName();
                } else {
                    return String.format("%s (%s) ", station.getName(), find());
                }
            }
            return super.toString();
        }
    }

    /**
     * A weighted undirected edge in a graph.
     *
     * @param <T> the type of data stored in the edge
     */
    public static class Edge<T> implements Comparable<Edge<T>> {
        private final Node<T> node1;
        private final Node<T> node2;
        private final int weight;

        /**
         * Creates a new undirected edge with the specified weight between
         * two nodes.
         *
         * @param node1  the first node
         * @param node2  the other node
         * @param weight the weight
         */
        public Edge(Node<T> node1, Node<T> node2, int weight) {
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
        }

        /**
         * Gets the first node used when constructing this edge.
         *
         * @return the first node
         */
        public Node<T> getNode1() {
            return node1;
        }

        /**
         * Gets the second node used when constructing this edge.
         *
         * @return the second node
         */
        public Node<T> getNode2() {
            return node2;
        }

        /**
         * Gets the weight associated with this edge. Although the term
         * "weight" is used, this is not necessarily a number.
         *
         * @return the weight
         */
        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Edge<T> other) {
            return Integer.signum(this.weight - other.weight);
        }
    }
}
