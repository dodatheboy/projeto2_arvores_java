import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXLauncher extends Application {
    private static ArvoreBinariaBolada bst;

    public static void setBst(ArvoreBinariaBolada bst) {
        JavaFXLauncher.bst = bst;
    }

    @Override
    public void start(Stage primaryStage) {
        TreeVisualizer visualizer = new TreeVisualizer(bst);
        visualizer.start(primaryStage);
    }
}