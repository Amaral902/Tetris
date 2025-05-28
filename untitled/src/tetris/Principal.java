package tetris;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {
    public Principal() {
        criarMenuInicial();
    }

    private void criarMenuInicial() {
        setTitle("Tetris - Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel do menu
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        
        // Botão Novo Jogo
        JButton btnNovoJogo = new JButton("Novo Jogo");
        btnNovoJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adiciona ação ao botão
        btnNovoJogo.addActionListener(e -> iniciarNovoJogo());

        painelMenu.add(Box.createVerticalGlue());
        painelMenu.add(btnNovoJogo);
        painelMenu.add(Box.createVerticalGlue());

        add(painelMenu);
    }

    private void iniciarNovoJogo() {
        // Esconde a janela do menu
        setVisible(false);
        
        // Cria e mostra a janela do jogo
        EventQueue.invokeLater(() -> {
            Gameform gameForm = new Gameform();
            gameForm.setVisible(true);
        });
        
        // Fecha a janela do menu
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Principal menu = new Principal();
            menu.setVisible(true);
        });
    }
}