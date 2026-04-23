// Classe que implementa a Árvore Binária de Busca (ABB)
public class ArvoreBinariaBolada {
    private Node root; // Raiz da árvore

    // Construtor
    public ArvoreBinariaBolada() {
        this.root = null;
    }

    // Método público para inserir um jogador
    public void inserir(Player jogador) {
        root = inserir(root, jogador);
    }

    // Método privado recursivo para inserir um jogador
    private Node inserir(Node current, Player jogador) {
        if (current == null) {
            return new Node(jogador);
        }

        if (jogador.getRanking() < current.getPlayer().getRanking()) {
            current.setEsquerda(inserir(current.getEsquerda(), jogador));
        }
        else if (jogador.getRanking() > current.getPlayer().getRanking()) {
            current.setDireita(inserir(current.getDireita(), jogador));
        }

        return current;
    }

    // Método público para buscar um jogador pelo nickname
    public boolean busca(String nome) {
        return busca(root, nome) != null;
    }

    // Método privado recursivo para buscar um jogador pelo nickname
    private Node busca(Node current, String nome) {
        if (current == null) {
            return null;
        }

        if (current.getPlayer().getName().equals(nome)) {
            return current;
        }

        Node esquerda = busca(current.getEsquerda(), nome);
        if (esquerda != null) {
            return esquerda;
        }

        return busca(current.getDireita(), nome);
    }

    // Método público para remover um jogador pelo nickname
    public Player remover(String nome) {
        Node nodeToRemove = busca(root, nome);
        if (nodeToRemove == null) {
            return null; // Jogador não encontrado
        }
        root = remover(root, nome);
        return nodeToRemove.getPlayer();
    }

    // Método privado recursivo para remover um jogador pelo nickname
    private Node remover(Node current, String nome) {
        if (current == null) {
            return null;
        }

        if (current.getPlayer().getName().equals(nome)) {
            // Caso 1: Nó sem filhos
            if (current.getEsquerda() == null && current.getDireita() == null) {
                return null;
            }
            // Caso 2: Nó com um filho
            if (current.getEsquerda() == null) {
                return current.getDireita();
            }
            if (current.getDireita() == null) {
                return current.getEsquerda();
            }
            // Caso 3: Nó com dois filhos
            Player menorJogador = encontrarMenor(current.getDireita());
            current.setPlayer(menorJogador);
            current.setDireita(remover(current.getDireita(), menorJogador.getName()));
        }
        else {
            current.setEsquerda(remover(current.getEsquerda(), nome));
            current.setDireita(remover(current.getDireita(), nome));
        }

        return current;
    }

    // Método para encontrar o menor jogador em uma subárvore
    private Player encontrarMenor(Node node) {
        return node.getEsquerda() == null ? node.getPlayer() : encontrarMenor(node.getEsquerda());
    }

    // Método para percorrer a árvore em ordem (opcional)
    public void emOrdem() {
        emOrdem(root);
    }

    private void emOrdem(Node node) {
        if (node != null) {
            emOrdem(node.getEsquerda());
            System.out.println(node.getPlayer());
            emOrdem(node.getDireita());
        }
    }

    // Getter para a raiz
    public Node getRoot() {
        return root;
    }
}