package Model;

public class Node {

    private Movie data;
    private Node next;

    // Constructor
    public Node(Movie data) {
        this.data = data;
        this.next = null;
    }

    // Getters and Setters
    public Movie getData() {
        return data;
    }

    public void setData(Movie data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
