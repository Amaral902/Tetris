package tetris;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    // Variáveis para controle do grid do jogo
    private int GridTamanho;      // Número de linhas no grid
    private int GridColunas;      // Número de colunas no grid
    private int GridCelula;       // Tamanho de cada célula do grid em pixels
    private Blocos bloco;         // Bloco atual em movimento
    private Color[][] backgorund; // Matriz que guarda os blocos fixos no fundo
    private static final int GRID_HEIGHT = 20; // Altura padrão do grid em células

    // Construtor da área do jogo
    public GameArea(int colunas) {
        // Define posição e tamanho do painel
        this.setBounds(10, 30, 200, 400);
        this.setBackground(Color.gray);
        
        // Inicializa variáveis do grid
        GridColunas = colunas;
        GridCelula = this.getBounds().width / GridColunas;  // Calcula tamanho da célula
        GridTamanho = this.getBounds().height / GridColunas; // Calcula número de linhas

        // Inicializa matriz de fundo
        backgorund = new Color[GridTamanho][GridColunas];
        spawnbloco(); // Cria o primeiro bloco
    }

    // Cria um novo bloco no jogo
    public void spawnbloco() {
        // Cria um bloco em forma de L (exemplo fixo)
        bloco = new Blocos(new int[][]{
                {1, 0, 0},
                {1, 0, 0},
                {1, 1, 0}
        }, Color.green, GridCelula);
    }

    // Retorna a largura do painel em pixels
    public int getPixelWidth() {
        return this.getBounds().width;
    }

    // Retorna a altura do painel em pixels
    private int getPixelHeight() {
        return 0;
    }

    // Move o bloco para baixo se possível
    public boolean moveDown() {
        if (olhabaixo() == false) {
            moverparabackground(); // Fixa o bloco no fundo
            spawnbloco();         // Cria um novo bloco
            return true;          // Continua o jogo
        }
        
        bloco.baixo();           // Move o bloco atual para baixo
        repaint();               // Redesenha o painel
        return true;
    }
    public void moveLeft() {
        bloco.esquerda();
        repaint();
    }
    public void moveRight() {
        bloco.direita();
        repaint();
    }
    public void rotate() {
        bloco.rotacionar();
        repaint();
    }
    public void drop() {
        while (olhabaixo()) {
            moveDown();
        }
    }

    // Verifica se o bloco pode mover para baixo
    public boolean olhabaixo() {
        // Verifica se atingiu o fundo da tela
        if (bloco.pegay() + (bloco.altura() * GridCelula) >= getBounds().height) {
            return false;
        }
        
        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;
        int gridY = bloco.pegay() / GridCelula;
        
        // Se o bloco ainda está acima do grid, permite movimento
        if (gridY < 0) {
            return true;
        }
        
        // Verifica colisão com outros blocos
        for (int linha = 0; linha < bloco.altura(); linha++) {
            for (int coluna = 0; coluna < bloco.largura(); coluna++) {
                if (forma[linha][coluna] == 1) {
                    int nextY = gridY + linha + 1;
                    if (nextY >= 0 && nextY < GridTamanho && 
                        gridX + coluna >= 0 && gridX + coluna < GridColunas &&
                        backgorund[nextY][gridX + coluna] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Desenha o bloco atual na tela
    private void criarbloco(Graphics g) {
        for (int linha = 0; linha < bloco.altura(); linha++) {
            for (int coluna = 0; coluna < bloco.largura(); coluna++) {
                if (bloco.pegaforma()[linha][coluna] == 1) {
                    // Calcula posição em pixels
                    int x = (bloco.pegax() + coluna * GridCelula);
                    int y = (bloco.pegay() + linha * GridCelula);
                    
                    gridQuadrado(g, x, y, bloco.pegacor());
                }
            }
        }
    }

    // Desenha os blocos fixos do fundo
    private void setBackground(Graphics g) {
        Color cor;
        for (int linha = 0; linha < GridTamanho; linha++) {
            for (int coluna = 0; coluna < GridColunas; coluna++) {
                cor = backgorund[linha][coluna];
                if (cor != null) {
                    int x = (coluna * GridCelula);
                    int y = (linha * GridCelula);
                    g.setColor(cor);
                    g.fillRect(coluna * GridCelula, linha * GridCelula, GridCelula, GridCelula);
                    gridQuadrado(g, x, y, cor);
                }
            }
        }
    }

    // Desenha um quadrado do grid com borda
    private void gridQuadrado(Graphics g, int x, int y, Color cor) {
        g.setColor(cor);
        g.fillRect(x, y, GridCelula, GridCelula);
        g.setColor(Color.black);
        g.drawRect(x, y, GridCelula, GridCelula);
    }

    // Move o bloco atual para o fundo (fixa ele)
    private void moverparabackground() {
        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;  // Converte pixel para posição do grid
        int gridY = bloco.pegay() / GridCelula;  // Converte pixel para posição do grid
        int largura = bloco.largura();
        int altura = bloco.altura();
        Color cor = bloco.pegacor();

        for (int linha = 0; linha < altura; linha++) {
            for (int coluna = 0; coluna < largura; coluna++) {
                if (forma[linha][coluna] == 1) {
                    // Verifica se a posição está dentro dos limites da matriz
                    if (gridY + linha >= 0 && gridY + linha < GridTamanho &&
                        gridX + coluna >= 0 && gridX + coluna < GridColunas) {
                        backgorund[gridY + linha][gridX + coluna] = cor;
                    }
                }
            }
        }
    }


    @Override
    // Metodo chamado automaticamente para desenhar o componente
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        criarbloco(g);     // Desenha o bloco atual
        setBackground(g);  // Desenha os blocos do fundo
    }
}