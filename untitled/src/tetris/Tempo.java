package tetris;

import javax.swing.*;
import java.awt.*;

public class Tempo extends Thread {
    private GameArea gameArea;     // Referência para a área do jogo
    private JFrame parentFrame;    // Referência para a janela principal

    // Construtor recebe referências necessárias
    public Tempo(GameArea gameArea, JFrame parentFrame) {
        this.gameArea = gameArea;
        this.parentFrame = parentFrame;
    }

    // Inicia a thread do jogo
    public void start() {
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(100);  // Pausa entre movimentos

                        // Verifica se o bloco pode continuar descendo
                        if (!gameArea.moveDown()) {
                            // Mostra mensagem de game over
                            JOptionPane.showMessageDialog(parentFrame,
                                    "Game Over!",
                                    "Fim de Jogo",
                                    JOptionPane.INFORMATION_MESSAGE);
                            
                            parentFrame.dispose();  // Fecha a janela do jogo
                            
                            // Volta para o menu principal
                            EventQueue.invokeLater(() -> {
                                Principal menu = new Principal();
                                menu.setVisible(true);
                            });
                            break;  // Encerra o loop do jogo
                        }

                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
        }.start();
    }
}