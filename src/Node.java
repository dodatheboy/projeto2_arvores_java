class Node<T> {
    private Player player;
    Node<T> esquerda;
    Node<T> direita;

    public Node(Player player) {
        this.player = player;
        this.esquerda = null;
        this.direita = null;
    }
    // Getters e Setters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Node getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Node esquerda) {
        this.esquerda = esquerda;
    }

    public Node getDireita() {
        return direita;
    }

    public void setDireita(Node direita) {
        this.direita = direita;
    }
}