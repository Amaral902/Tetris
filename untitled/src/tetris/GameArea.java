package tetris;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    private int GridTamanho;
    private int GridColunas;
    private int GridCelula;
    //instancia de array com valores predefinidos
    private int[][] bloco = {{1,0},{1,0},{1,1}};
    public GameArea(int colunas) {
        this.setBounds(10, 30, 200, 400);
        this.setBackground(Color.gray);
        GridColunas = colunas;
        GridCelula = this.getBounds().width / GridColunas;
        GridTamanho = this.getBounds().height / GridColunas;
    }
    private void colocarbloco(Graphics g){
        for ( int linha=0; linha<bloco.length; linha++){
            for (int coluna=0; coluna<bloco[0].length; coluna++){
                if (bloco[linha][coluna]==1){
                    g.setColor(Color.green);
                    g.fillRect(coluna*GridCelula, linha*GridCelula, GridCelula, GridCelula);
                    g.setColor(Color.black);
                    g.drawRect(coluna*GridCelula, linha*GridCelula, GridCelula, GridCelula);
                }

            }
        }

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        colocarbloco(g);

    }
}