import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {
    private ArvoreBinariaBolada bst = new ArvoreBinariaBolada();
    private SwingNode swingNode;
    private JPanel treePanel;

    @Override
    public void start(Stage primaryStage) {
        // Configuração do JavaFX
        BorderPane root = new BorderPane();
        swingNode = new SwingNode();
        createSwingContent();
        root.setCenter(swingNode);

        // Menu de opções
        VBox menu = new VBox(10);
        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Nickname");
        TextField rankingField = new TextField();
        rankingField.setPromptText("Ranking");
        Button insertButton = new Button("Inserir");
        Button searchButton = new Button("Buscar");
        Button removeButton = new Button("Remover");
        Button loadCSVButton = new Button("Carregar CSV");

        insertButton.setOnAction(e -> {
            try {
                String nickname = nicknameField.getText();
                int ranking = Integer.parseInt(rankingField.getText());
                bst.inserir(new Player(nickname, ranking));
                updateTreeView();
            } catch (NumberFormatException ex) {
                showAlert("Erro", "Ranking deve ser um número inteiro.");
            }
        });

        searchButton.setOnAction(e -> {
            String nickname = nicknameField.getText();
            boolean found = bst.busca(nickname);
            showAlert("Resultado", found ? "Jogador encontrado!" : "Jogador não encontrado.");
        });

        removeButton.setOnAction(e -> {
            String nickname = nicknameField.getText();
            Player removed = bst.remover(nickname);
            if (removed != null) {
                showAlert("Sucesso", "Jogador removido: " + removed);
            } else {
                showAlert("Erro", "Jogador não encontrado.");
            }
            updateTreeView();
        });

        loadCSVButton.setOnAction(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length == 2) {
                            bst.inserir(new Player(data[0].trim(), Integer.parseInt(data[1].trim())));
                        }
                    }
                    updateTreeView();
                } catch (IOException | NumberFormatException ex) {
                    showAlert("Erro", "Erro ao ler o arquivo CSV.");
                }
            }
        });

        menu.getChildren().addAll(
                new Label("Nickname:"), nicknameField,
                new Label("Ranking:"), rankingField,
                insertButton, searchButton, removeButton, loadCSVButton
        );

        root.setLeft(menu);

        // Configuração da janela
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Sistema de Ranking de Jogadores");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para criar o conteúdo Swing
    private void createSwingContent() {
        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, bst.getRoot(), getWidth() / 2, 30, getWidth() / 4);
            }
        };
        swingNode.setContent(treePanel);
    }

    // Método para desenhar a árvore
    private void drawTree(Graphics g, Node node, int x, int y, int offset) {
        if (node == null) {
            return;
        }

        // Desenha o nó atual
        g.setColor(Color.WHITE);
        g.fillOval(x - 20, y - 20, 40, 40);
        g.setColor(Color.BLACK);
        g.drawOval(x - 20, y - 20, 40, 40);
        g.drawString(node.getPlayer().getName(), x - 15, y + 5);

        // Desenha as linhas para os filhos
        if (node.getEsquerda() != null) {
            g.drawLine(x, y + 10, x - offset, y + 60);
            drawTree(g, node.getEsquerda(), x - offset, y + 60, offset / 2);
        }
        if (node.getDireita() != null) {
            g.drawLine(x, y + 10, x + offset, y + 60);
            drawTree(g, node.getDireita(), x + offset, y + 60, offset / 2);
        }
    }

    // Método para atualizar a visualização da árvore
    private void updateTreeView() {
        treePanel.repaint();
    }

    // Método para mostrar alertas
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}