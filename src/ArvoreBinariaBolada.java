public class ArvoreBinariaBolada {

    private Node root;

    public ArvoreBinariaBolada() {
        this.root = null;
    }

    // Insere um jogador na árvore (chamada pública)
    public void inserir(Player Player) {
        root = inserir(root, Player);
    }

    // Insere um jogador na árvore (recursivo)
    private Node inserir(Node current, Player Player) {
        if (current == null) {
            return new Node(Player);
        }

        if (Player.getRanking() < current.Player.getRanking()) {
            current.esquerda = inserir(current.esquerda, Player);
        }
        else if (Player.getRanking() > current.Player.getRanking()) {
            current.direita = inserir(current.direita, Player);
        }

        return current;
    }

    // Busca um jogador pelo nome (chamada pública)
    public boolean busca(String nome) {
        return busca(root, nome) != null;
    }

    // Busca um jogador pelo nome (recursivo)
    private Node busca(Node current, String nome) {
        if (current == null) {
            return null;
        }

        if (current.Player.getName().equals(nome)) {
            return current;
        }

        // Busca recursivamente nos filhos esquerdo e direito
        Node esquerdaResult = busca(current.esquerda, nome);
        if (esquerdaResult != null) {
            return esquerdaResult;
        }

        return busca(current.direita, nome);
    }

    // remover um jogador pelo nome (chamada pública)
    public Player remover(String nome) {
        Node nodeToremover = busca(root, nome);
        if (nodeToremover == null) {
            return null; // Jogador não encontrado
        }
        Player removerdPlayer = nodeToremover.Player;
        root = remover(root, nome);
        return removerdPlayer;
    }

    // remover um jogador pelo nome (recursivo)
    private Node remover(Node current, String nome) {
        if (current == null) {
            return null;
        }

        if (nome.compareTo(current.Player.getName()) < 0) {
            current.esquerda = remover(current.esquerda, nome);
        } else if (nome.compareTo(current.Player.getName()) > 0) {
            current.direita = remover(current.direita, nome);
        } else {
            // Caso 1: Nó sem filhos
            if (current.esquerda == null && current.direita == null) {
                return null;
            }
            // Caso 2: Nó com um filho
            if (current.esquerda == null) {
                return current.direita;
            } else if (current.direita == null) {
                return current.esquerda;
            }
            // Caso 3: Nó com dois filhos
            Node menordireita = procurarmenor(current.direita);
            current.Player = menordireita.Player;
            current.direita = remover(current.direita, menordireita.Player.getName());
        }

        return current;
    }

    // Encontra o nó com o menor ranking (para remoção)
    private Node procurarmenor(Node node) {
        if (node.esquerda == null) {
            return node;
        }
        return procurarmenor(node.esquerda);
    }

    // Percurso em ordem (opcional)
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.esquerda);
            System.out.println("nome: " + node.Player.getName() + ", Ranking: " + node.Player.getRanking());
            inOrder(node.direita);
        }
    }
}