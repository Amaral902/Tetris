package tetris;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    private int GridTamanho;
    private int GridColunas;
    private int GridCelula;
    private Blocos bloco;
    // ... outros campos ...
    private static final int GRID_HEIGHT = 20; // altura do grid em células

    public GameArea(int colunas) {
        this.setBounds(10, 30, 200, 400);
        this.setBackground(Color.gray);
        GridColunas = colunas;
        GridCelula = this.getBounds().width / GridColunas;
        GridTamanho = this.getBounds().height / GridColunas;
        spawnbloco();
    }

    public void spawnbloco() {
        bloco = new Blocos(new int[][]{
                {1, 0},
                {1, 0},
                {1, 1}
        }, Color.green, GridCelula);
    }

    public int getPixelWidth() {
        return this.getBounds().width; // pega largura em pixels
    }
    private int getPixelHeight() {
        return 0;
    }

    public void moveDown() {
        if (olhabaixo()) {
            bloco.baixo();
            repaint();
        }
    }

    private boolean olhabaixo() {
        if (bloco.pegay() + (bloco.altura() * GridCelula) >= getBounds().height) {
            spawnbloco(); // cria novo bloco quando atingir o fundo
            return false;
        }
        return true;
    }



    private void criarbloco(Graphics g){
        for ( int linha=0; linha<bloco.altura(); linha++){
            for (int coluna=0; coluna<bloco.largura(); coluna++){
                if (bloco.pegaforma()[linha][coluna]==1){
                    int x=(bloco.pegax() + coluna*GridCelula);
                    int y=(bloco.pegay() + linha*GridCelula);
                    g.setColor(bloco.pegacor());
                    g.fillRect(x, y, GridCelula, GridCelula);
                    g.setColor(Color.black);
                    g.drawRect(x, y, GridCelula, GridCelula);
                }

            }
        }

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        criarbloco(g);


    }}