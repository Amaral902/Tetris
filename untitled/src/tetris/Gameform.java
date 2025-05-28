package tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


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
        initControls();
        start();
    }
    private void initControls() {
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "direita");
        im.put(KeyStroke.getKeyStroke("LEFT"), "esquerda");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "baixo");

        am.put("direita", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveRight();
            }
        });

        am.put("esquerda", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveLeft();
            }
        });

        am.put("up", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.rotate();
            }
        });

        am.put("baixo", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.drop();
            }
        });
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