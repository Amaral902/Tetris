package tetris;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    private int score = 0;
    private int level = 1;
    private int linhasCompletasTotal = 0;
    private Gameform gameform;  // Referência ao Gameform
    
    // Flag para controlar o estado do jogo
    private boolean isGameOver = false;
    
    // Variáveis para controle do grid do jogo
    private int GridLinha;        // Número de linhas no grid
    private int GridColunas;      // Número de colunas no grid
    private int GridCelula;       // Tamanho de cada célula do grid em pixels
    private Blocos bloco;         // Bloco atual em movimento
    private Color[][] backgorund; // Matriz que guarda os blocos fixos no fundo
    private static final int GRID_HEIGHT = 20; // Altura padrão do grid em células

    // Construtor: inicializa a área do jogo com número específico de colunas
    public GameArea(int colunas) {
        // Configuração visual do painel
        this.setBounds(10, 30, 200, 400);
        this.setBackground(Color.gray);
        
        // Inicialização do grid
        GridColunas = colunas;
        GridCelula = this.getBounds().width / GridColunas;
        GridLinha = this.getBounds().height / GridColunas;

        // Inicializa matriz de fundo e cria primeiro bloco
        backgorund = new Color[GridLinha][GridColunas];
        spawnbloco();
    }

    // Adicione estes métodos getter
    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    // Cria um novo bloco no jogo
    public void spawnbloco() {
        // Cria um bloco em forma de L (exemplo fixo)
        bloco = new Blocos(new int[][]{
                {1, 0},
                {1, 0},
                {1, 1},

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
    if (!olhabaixo()) {
        moverparabackground();
        
        if (gameover()) {
            isGameOver = true;
            if (gameform != null) {
                // Em vez de chamar gameOver, vamos mostrar uma mensagem simples
                JOptionPane.showMessageDialog(gameform, 
                    "Game Over!\nPontuação Final: " + score, 
                    "Fim de Jogo", 
                    JOptionPane.INFORMATION_MESSAGE);
                // Fecha a janela atual e abre o menu principal
                gameform.dispose();
                new Principal().setVisible(true);
            }
            return false;
        }
        
        spawnbloco();
        limpalinhas();
        return true;
    }
    
    bloco.baixo();
    limpalinhas();
    repaint();
    return true;
}
    // Métodos de movimento (todos verificam se o jogo acabou antes de executar)
    public void moveLeft() {
        if (isGameOver || !olhadesquerda()) return;
        bloco.esquerda();
        repaint();
    }

    public void moveRight() {
        if (isGameOver || !olhadireita()) return;
        bloco.direita();
        repaint();
    }

    public void rotate() {
        if (isGameOver) return;
        bloco.rotacionar();
        repaint();
    }

    public void drop() {
        if (isGameOver) return;
        while (olhabaixo()) {
            moveDown();
        }
    }

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

        for (int coluna = 0; coluna < bloco.largura(); coluna++) {
            for (int linha = bloco.altura() - 1; linha >= 0; linha--) {
                if (forma[linha][coluna] == 1) {
                    int nextY = gridY + linha + 1;
                    int nextX = gridX + coluna;
                    
                    // Verifica se a próxima posição está dentro dos limites
                    if (nextY >= GridLinha) {
                        return false;
                    }
                    
                    if (nextX >= 0 && nextX < GridColunas && nextY >= 0) {
                        if (backgorund[nextY][nextX] != null) {
                            return false;
                        }
                    }
                    break; // Só precisamos verificar o bloco mais baixo em cada coluna
                }
            }
        }
        return true;
    }
    public boolean olhadireita() {
        // Verifica se o bloco atingiu o limite direito da tela
        if (bloco.pegadireita() + (bloco.largura() * GridCelula) >= getBounds().width) {
            return false;
        }

        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;
        int gridY = bloco.pegay() / GridCelula;

        // Verifica cada coluna do bloco, da direita para a esquerda
        for (int coluna = bloco.largura() - 1; coluna >= 0; coluna--) {
            for (int linha = 0; linha < bloco.altura(); linha++) {
                if (forma[linha][coluna] == 1) {
                    int nextY = gridY + linha;
                    int nextX = gridX + coluna + 1;

                    // Verifica se está dentro dos limites horizontais
                    if (nextX >= GridColunas) {
                        return false;
                    }

                    // Verifica se há colisão com blocos existentes
                    if (nextY >= 0 && nextY < GridLinha && nextX >= 0 && nextX < GridColunas) {
                        if (backgorund[nextY][nextX] != null) {
                            return false;
                        }
                    }
                    break; // Passa para próxima coluna após encontrar o primeiro bloco
                }
            }
        }
        return true;
    }

    public boolean olhadesquerda() {
        // Verifica se o bloco atingiu o limite esquerdo da tela
        if (bloco.pegaesquerda() <= 0) {
            return false;
        }

        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;
        int gridY = bloco.pegay() / GridCelula;

        // Verifica cada coluna do bloco, da esquerda para a direita
        for (int coluna = 0; coluna < bloco.largura(); coluna++) {
            for (int linha = 0; linha < bloco.altura(); linha++) {
                if (forma[linha][coluna] == 1) {
                    int nextY = gridY + linha;
                    int nextX = gridX + coluna - 1;

                    // Verifica se está dentro dos limites horizontais
                    if (nextX < 0) {
                        return false;
                    }

                    // Verifica se há colisão com blocos existentes
                    if (nextY >= 0 && nextY < GridLinha && nextX >= 0 && nextX < GridColunas) {
                        if (backgorund[nextY][nextX] != null) {
                            return false;
                        }
                    }
                    break; // Passa para próxima coluna após encontrar o primeiro bloco
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
        for (int linha = 0; linha < GridLinha; linha++) {
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
    // Modifique o metodo limpalinhas para retornar void
    public void limpalinhas() {
        boolean linhafeita;
        boolean algumaLinhaCompleta = false;
        int linhasCompletas = 0;
        
        for (int linha = GridLinha - 1; linha >= 0; linha--) {
            linhafeita = true;
            for (int coluna = 0; coluna < GridColunas; coluna++) {
                if (backgorund[linha][coluna] == null) {
                    linhafeita = false;
                    break;
                }
            }

            if (linhafeita) {
                linhasCompletas++;
                limpar(linha);
                cair(linha);
                algumaLinhaCompleta = true;
                linha++;
            }
        }
        
        if (linhasCompletas > 0) {
            linhasCompletasTotal += linhasCompletas;
            
            // Atualiza a pontuação
            switch (linhasCompletas) {
                case 1:
                    score += 100 * level;
                    break;
                case 2:
                    score += 300 * level;
                    break;
                case 3:
                    score += 500 * level;
                    break;
                case 4:
                    score += 800 * level;
                    break;
            }
            
            // Atualiza o nível (a cada 3 linhas)
            level = (linhasCompletasTotal / 3) + 1;
            
            // Notifica o Gameform sobre as mudanças
            if (gameform != null) {
                gameform.updateScore(score);
                gameform.updateLevel(level);
            }
        }
        
        if (algumaLinhaCompleta) {
            repaint();
        }
    }
// Adicione um metodo para definir o Gameform
public void setGameform(Gameform gameform) {
    this.gameform = gameform;
}
    private void limpar(int linha){
        for (int coluna = 0; coluna < GridColunas; coluna++)
        {
            backgorund[linha][coluna] = null;
        }
    }
    private void cair(int linhaCompleta) {
        for (int linha = linhaCompleta; linha > 0; linha--) {
            for (int coluna = 0; coluna < GridColunas; coluna++) {
                backgorund[linha][coluna] = backgorund[linha - 1][coluna];
            }
        }
        // Limpa a linha do topo
        for (int coluna = 0; coluna < GridColunas; coluna++) {
            backgorund[0][coluna] = null;
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
                    if (gridY + linha >= 0 && gridY + linha < GridLinha &&
                        gridX + coluna >= 0 && gridX + coluna < GridColunas) {
                        backgorund[gridY + linha][gridX + coluna] = cor;
                    }
                }
            }
        }
    }

    // Verifica se o jogo acabou (blocos chegaram ao topo)
    public boolean gameover() {
        // Verifica se há blocos na área de spawn (primeiras linhas)
        for (int coluna = 0; coluna < GridColunas; coluna++) {
            if (backgorund[0][coluna] != null || backgorund[1][coluna] != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    // Metodo chamado automaticamente para desenhar o componente
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        criarbloco(g);     // Desenha o bloco atual
        setBackground(g);  // Desenha os blocos do fundo
    }
    // Getter para o estado do jogo
    public boolean isGameOver() {
        return isGameOver;
    }
}