package tetris;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    public GameArea() {
        this.setBounds(10, 200, 100, 100);
        this.setBackground(Color.gray);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, 50, 50);

    }
}