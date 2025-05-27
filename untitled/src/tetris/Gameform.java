package tetris;
import javax.swing.*;
public class Gameform extends javax.swing.JFrame {
    public Gameform() {
        initComponents();

        // Define o layout como null para permitir posicionamento livre
        this.setLayout(null);

        // Adiciona a área do jogo
        this.add(new GameArea());

        // Define o tamanho da janela principal
        this.setSize(450, 500);

        // Centraliza a janela
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gameform().setVisible(true);
            }
        });
    }

    private void initComponents() {
        // Additional initialization code, e.g., preparing GUI components, can be added here.
    }
}