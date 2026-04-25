import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        ArvoreBinariaBolada bst = new ArvoreBinariaBolada();
        Scanner scanner = new Scanner(System.in);

        loadPlayersFromCSV(bst, "players.csv");

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Inserir jogador");
            System.out.println("2. Buscar jogador");
            System.out.println("3. Remover jogador");
            System.out.println("4. Visualizar árvore");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Nickname: ");
                    String nickname = scanner.nextLine();
                    System.out.print("Ranking: ");
                    int ranking = scanner.nextInt();
                    bst.inserir(new Player(nickname, ranking));
                    break;
                case 2:
                    System.out.print("Nickname: ");
                    String buscaNickname = scanner.nextLine();
                    boolean found = bst.busca(buscaNickname);
                    System.out.println(found ? "Jogador encontrado!" : "Jogador não encontrado.");
                    break;
                case 3:
                    System.out.print("Nickname: ");
                    String removeNickname = scanner.nextLine();
                    Player removedPlayer = bst.remove(removeNickname);
                    System.out.println(removedPlayer != null ? "Jogador removido!" : "Jogador não encontrado.");
                    break;
                case 4:
                    JavaFXLauncher.setBst(bst);
                    Application.launch(JavaFXLauncher.class, new String[0]);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (choice != 5);
    }

    private static void loadPlayersFromCSV(ArvoreBinariaBolada bst, String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String nickname = data[0].trim();
                    int ranking = Integer.parseInt(data[1].trim());
                    bst.inserir(new Player(nickname, ranking));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }
}