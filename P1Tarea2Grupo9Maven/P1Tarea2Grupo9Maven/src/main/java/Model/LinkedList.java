package Model;

public class LinkedList {

    private Node head;

    public LinkedList() {
        this.head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int getSize() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    // Insert at the beginning of the list
    public void insertAtBeginning(Movie movie) {
        Node newNode = new Node(movie);
        newNode.setNext(this.head);
        this.head = newNode;
    }

    // Insert at the end of the list
    public void insertAtEnd(Movie movie) {
        Node newNode = new Node(movie);
        if (isEmpty()) {
            this.head = newNode;
        } else {
            Node current = this.head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    // Delete an element by its title
    public boolean deleteByTitle(String title) {
        if (isEmpty()) {
            return false;
        }

        if (head.getData().getTitle().equalsIgnoreCase(title)) {
            head = head.getNext();
            return true;
        }

        Node previous = head;
        Node current = head.getNext();
        while (current != null && !current.getData().getTitle().equalsIgnoreCase(title)) {
            previous = current;
            current = current.getNext();
        }

        if (current != null) {
            previous.setNext(current.getNext());
            return true;
        }
        return false;
    }

    // Find a node by title
    public Movie findByTitle(String title) {
        Node current = head;
        while (current != null) {
            if (current.getData().getTitle().equalsIgnoreCase(title)) {
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }

    // Get all movies to display them
    public Movie[] getAllMovies() {
        if (isEmpty()) {
            return new Movie[0];
        }
        int totalSize = getSize();
        Movie[] movies = new Movie[totalSize];
        Node current = head;
        int i = 0;
        while (current != null) {
            movies[i] = current.getData();
            current = current.getNext();
            i++;
        }
        return movies;
    }
}
