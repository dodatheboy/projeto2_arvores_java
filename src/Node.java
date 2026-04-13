class Node<T> {
    T Player;
    Node<T> esquerda;
    Node<T> direita;

    public Node(T Player) {
        this.Player = Player;
        this.esquerda = null;
        this.direita = null;
    }
}