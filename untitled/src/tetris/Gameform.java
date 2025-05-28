package tetris;
import javax.swing.*;
import java.awt.*;

public class Gameform extends JFrame {
    private GameArea gameArea; // Declara gameArea como campo da classe
    
    public Gameform() {
        initComponents();
        // Define o layout como null para permitir posicionamento livre
        this.setLayout(null);

        // Cria e armazena a referência do GameArea
        gameArea = new GameArea(10); // Remove o GameArea da declaração local
        this.add(gameArea);

        // Define o tamanho da janela principal
        this.setSize(450, 500);

        // Centraliza a janela
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Define o título da janela
        this.setTitle("Tetris");
        start();
    }

    public void start(){
        new Tempo(gameArea).start();
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gameform().setVisible(true);
            }
        });
    }

    private void initComponents() {
    }
}