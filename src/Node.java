class Node {
    public Player player;
    public Node esquerda;
    public Node direita;

    public Node(Player player) {
        this.player = player;
        this.esquerda = null;
        this.direita = null;
    }
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