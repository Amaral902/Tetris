package tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;



   ; public class Gameform extends JFrame {
        private GameArea gameArea;
        private JLabel scoreLabel;
        private JLabel levelLabel;
        private JPanel controlsPanel; // Novo painel para controles

        public Gameform() {
            initComponents();
            this.setLayout(null);

            gameArea = new GameArea(10);
            gameArea.setGameform(this); // Adicione esta linha
            this.add(gameArea);

            // Criar e adicionar o label de pontuação
            scoreLabel = new JLabel("Score: 0");
            scoreLabel.setBounds(300, 30, 100, 30);
            scoreLabel.setForeground(Color.BLACK);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
            this.add(scoreLabel);

            levelLabel = new JLabel("level: 1");
            levelLabel.setBounds(300, 50, 100, 30);
            levelLabel.setForeground(Color.BLACK);
            levelLabel.setFont(new Font("Arial", Font.BOLD, 15));
            this.add(levelLabel);

            // Adicionar painel de controles
            addControlsPanel();

            this.setSize(450, 500);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Tetris");
            initControls();

            Tempo tempo = new Tempo(gameArea, this);
            tempo.start();
        }


    void addControlsPanel() {
        controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setBounds(300, 100, 120, 200);
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Controles"));

        // Adicionar labels de controle
        JLabel[] controls = {
            new JLabel("← : Esquerda"),
            new JLabel("→ : Direita"),
            new JLabel("↑ : Rotacionar"),
            new JLabel("↓ : Descer"),
        };

        for (JLabel control : controls) {
            control.setAlignmentX(Component.LEFT_ALIGNMENT);
            control.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            controlsPanel.add(control);
        }

        this.add(controlsPanel);
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
    public void updateScore(int pontos) {
        scoreLabel.setText("Score: " + pontos);
    }
    public void updateLevel(int level) {
        levelLabel.setText("Level: " + level);
    }

    private void initComponents() {
    }
}