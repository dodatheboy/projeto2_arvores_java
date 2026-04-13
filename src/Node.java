class Node<T> {
    T input;
    Node<T> esquerda;
    Node<T> direita;

    public Node(T input) {
        this.input = input;
        this.esquerda = null;
        this.direita = null;
    }
}