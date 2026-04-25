import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class TreeVisualizer extends Application {
    private static ArvoreBinariaBolada bst;

    private static final int NODE_RADIUS = 25;
    private static final int LEVEL_HEIGHT = 90;
    private static final int MIN_LEAF_SPACING = 70;

    private Map<Node, Double> nodeX = new HashMap<>();
    private int[] leafCounter = {0};

    public TreeVisualizer() {
        this.bst = new ArvoreBinariaBolada();
    }

    public TreeVisualizer(ArvoreBinariaBolada bst) {
        this.bst = bst;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Visualizador de Árvore de Jogadores");

        if (bst.getRoot() == null) {
            System.out.println("Árvore vazia!");
            return;
        }

        leafCounter[0] = 0;
        assignXPositions(bst.getRoot());

        double minX = nodeX.values().stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double maxX = nodeX.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
        int treeHeight = calculateHeight(bst.getRoot());

        int marginX = 60;
        int marginY = 60;
        int canvasWidth  = (int)(maxX - minX) + marginX * 2 + NODE_RADIUS * 2;
        int canvasHeight = treeHeight * LEVEL_HEIGHT + marginY * 2;

        double offsetX = marginX + NODE_RADIUS - minX;
        double offsetY = marginY + NODE_RADIUS;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        drawTree(canvas, offsetX, offsetY);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(new Pane(canvas));
        scrollPane.setPannable(true);          // arrastar com o mouse
        scrollPane.setFitToHeight(false);
        scrollPane.setFitToWidth(false);
        scrollPane.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(scrollPane, 1400, 850);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void assignXPositions(Node node) {
        if (node == null) return;

        assignXPositions(node.esquerda);

        if (node.esquerda == null && node.direita == null) {
            nodeX.put(node, (double) leafCounter[0] * MIN_LEAF_SPACING);
            leafCounter[0]++;
        }

        assignXPositions(node.direita);

        if (node.esquerda != null || node.direita != null) {
            double left  = node.esquerda != null ? nodeX.get(node.esquerda)  : nodeX.get(node.direita);
            double right = node.direita  != null ? nodeX.get(node.direita)   : nodeX.get(node.esquerda);
            nodeX.put(node, (left + right) / 2.0);
        }
    }

    private int calculateHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateHeight(node.esquerda), calculateHeight(node.direita));
    }

    private void drawTree(Canvas canvas, double offsetX, double offsetY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawNodeRecursive(gc, bst.getRoot(), 0, offsetX, offsetY);
    }

    private void drawNodeRecursive(GraphicsContext gc, Node node, int depth, double offsetX, double offsetY) {
        if (node == null) return;

        double cx = nodeX.get(node) + offsetX;
        double cy = depth * LEVEL_HEIGHT + offsetY;

        if (node.esquerda != null) {
            double childX = nodeX.get(node.esquerda) + offsetX;
            double childY = (depth + 1) * LEVEL_HEIGHT + offsetY;
            gc.setStroke(Color.DARKGRAY);
            gc.setLineWidth(1.5);
            gc.strokeLine(cx, cy + NODE_RADIUS, childX, childY - NODE_RADIUS);
            drawNodeRecursive(gc, node.esquerda, depth + 1, offsetX, offsetY);
        }

        if (node.direita != null) {
            double childX = nodeX.get(node.direita) + offsetX;
            double childY = (depth + 1) * LEVEL_HEIGHT + offsetY;
            gc.setStroke(Color.DARKGRAY);
            gc.setLineWidth(1.5);
            gc.strokeLine(cx, cy + NODE_RADIUS, childX, childY - NODE_RADIUS);
            drawNodeRecursive(gc, node.direita, depth + 1, offsetX, offsetY);
        }

        gc.setFill(Color.STEELBLUE);
        gc.fillOval(cx - NODE_RADIUS, cy - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.setStroke(Color.DARKBLUE);
        gc.setLineWidth(2);
        gc.strokeOval(cx - NODE_RADIUS, cy - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial Bold", 13));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(String.valueOf(node.player.getRanking()), cx, cy + 5);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 11));
        gc.fillText(node.player.getName(), cx, cy + NODE_RADIUS + 14);
    }

    public static void main(String[] args) {
        ArvoreBinariaBolada bst = new ArvoreBinariaBolada();
        bst.inserir(new Player("Jogador1", 100));
        bst.inserir(new Player("Jogador2", 50));
        bst.inserir(new Player("Jogador3", 150));

        TreeVisualizer visualizer = new TreeVisualizer(bst);

        javafx.application.Platform.runLater(() -> {
            Stage stage = new Stage();
            try {
                visualizer.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}