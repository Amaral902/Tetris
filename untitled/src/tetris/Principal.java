package tetris;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {
    // Construtor da tela inicial
    public Principal() {
        criarMenuInicial();
    }

    // Cria o menu inicial do jogo
    private void criarMenuInicial() {
        // Configura a janela
        setTitle("Tetris - Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cria o painel do menu
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        
        // Cria o botão de novo jogo
        JButton btnNovoJogo = new JButton("Novo Jogo");
        btnNovoJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Quando clicar no botão, inicia o jogo
        btnNovoJogo.addActionListener(e -> iniciarNovoJogo());

        // Adiciona espaçamento e o botão
        painelMenu.add(Box.createVerticalGlue());
        painelMenu.add(btnNovoJogo);
        painelMenu.add(Box.createVerticalGlue());

        add(painelMenu);
    }

    // Inicia um novo jogo
    private void iniciarNovoJogo() {
        // Esconde o menu
        setVisible(false);
        
        // Cria e mostra a janela do jogo
        EventQueue.invokeLater(() -> {
            Gameform gameForm = new Gameform();
            gameForm.setVisible(true);
        });
        
        // Fecha o menu
        dispose();
    }

    // Método principal que inicia o programa
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Principal menu = new Principal();
            menu.setVisible(true);
        });
    }
}