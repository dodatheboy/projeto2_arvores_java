public class ArvoreBinariaBolada {
    private Node root;

    public ArvoreBinariaBolada() {
        this.root = null;
    }

    public void inserir(Player player) {
        root = inserir(root, player);
    }

    private Node inserir(Node current, Player player) {
        if (current == null) {
            return new Node(player);
        }

        if (player.getRanking() < current.player.getRanking()) {
            current.esquerda = inserir(current.esquerda, player);
        } else if (player.getRanking() > current.player.getRanking()) {
            current.direita = inserir(current.direita, player);
        }

        return current;
    }

    public boolean busca(String name) {
        return busca(root, name) != null;
    }

    private Node busca(Node current, String name) {
        if (current == null) {
            return null;
        }

        if (current.player.getName().equals(name)) {
            return current;
        }

        Node esquerdaResult = busca(current.esquerda, name);
        if (esquerdaResult != null) {
            return esquerdaResult;
        }

        return busca(current.direita, name);
    }

    public Player remove(String name) {
        Node nodeToRemove = busca(root, name);
        if (nodeToRemove == null) {
            return null;
        }
        root = remove(root, name);
        return nodeToRemove.player;
    }

    private Node remove(Node current, String name) {
        if (current == null) {
            return null;
        }

        if (current.player.getName().equals(name)) {
            if (current.esquerda == null && current.direita == null) {
                return null;
            }
            if (current.direita == null) {
                return current.esquerda;
            }
            if (current.esquerda == null) {
                return current.direita;
            }

            Player smallestPlayer = findSmallest(current.direita);
            current.player = smallestPlayer;
            current.direita = remove(current.direita, smallestPlayer.getName());
            return current;
        }

        current.esquerda = remove(current.esquerda, name);
        current.direita = remove(current.direita, name);
        return current;
    }

    private Player findSmallest(Node root) {
        return root.esquerda == null ? root.player : findSmallest(root.esquerda);
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.esquerda);
            System.out.println(node.player.getName() + " - " + node.player.getRanking());
            inOrder(node.direita);
        }
    }

    public Node getRoot() {
        return root;
    }
}