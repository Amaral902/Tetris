package tetris;
import java.util.Random;
import tetris.tetronimos.*;
import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa a área de jogo do Tetris.
 * Gerencia toda a lógica do jogo, renderização e interações do jogador.
 */
public class GameArea extends JPanel {
    // Variáveis de estado do jogo
    private int score = 0;               // Pontuação atual do jogador
    private int level = 1;               // Nível atual (afeta a velocidade)
    private int linhasCompletasTotal = 0; // Total de linhas completadas
    private Gameform gameform;           // Referência à janela principal

    // Controle do estado
    private boolean isGameOver = false;   // Flag de fim de jogo

    // Configurações do grid
    private int GridLinha;               // Número de linhas do grid
    private int GridColunas;             // Número de colunas do grid
    private int GridCelula;              // Tamanho de cada célula em pixels
    private Blocos bloco;                // Peça atual em movimento
    private Color[][] backgorund;        // Matriz das peças fixadas
    private static final int GRID_HEIGHT = 20; // Altura padrão

    // Array com todos os tipos de peças
    private Blocos[] blocos = new Blocos[]{
            new I(GridCelula), new J(GridCelula), new T(GridCelula),
            new L(GridCelula), new cubo(GridCelula), new S(GridCelula),
            new Z(GridCelula)
    };

    /**
     * Construtor que inicializa a área de jogo.
     * @param colunas Número de colunas do grid
     */
    public GameArea(int colunas) {
        this.setBounds(10, 30, 200, 400);
        this.setBackground(Color.gray);

        GridColunas = colunas;
        GridCelula = this.getBounds().width / GridColunas;
        GridLinha = this.getBounds().height / GridCelula;

        backgorund = new Color[GridLinha][GridColunas];
        spawnbloco();
    }

    // ====================== GETTERS ======================
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public boolean isGameOver() { return isGameOver; }

    // ================== GERENCIAMENTO DE PEÇAS ==================

    /**
     * Cria uma nova peça aleatória no topo do grid.
     */
    public void spawnbloco() {
        Random random = new Random();
        int indice = random.nextInt(blocos.length);

        /* Factory improvisada - cria nova instância baseada no tipo selecionado
         * (Percorre implicitamente o array blocos para selecionar o tipo) */
        if (blocos[indice] instanceof I) {
            bloco = new I(GridCelula);
        } else if (blocos[indice] instanceof J) {
            bloco = new J(GridCelula);
        } else if (blocos[indice] instanceof T) {
            bloco = new T(GridCelula);
        } else if (blocos[indice] instanceof L) {
            bloco = new L(GridCelula);
        } else if (blocos[indice] instanceof cubo) {
            bloco = new cubo(GridCelula);
        } else if (blocos[indice] instanceof S) {
            bloco = new S(GridCelula);
        } else if (blocos[indice] instanceof Z) {
            bloco = new Z(GridCelula);
        }
    }

    // ================== MOVIMENTAÇÃO ==================

    /**
     * Move a peça para baixo (retorna false se game over)
     */
    public boolean moveDown() {
        if (!olhabaixo()) {
            moverparabackground();

            if (gameover()) {
                isGameOver = true;
                if (gameform != null) {
                    JOptionPane.showMessageDialog(gameform,
                            "Game Over!\nPontuação Final: " + score,
                            "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
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
        repaint();
        return true;
    }

    /**
     * Move a peça para a esquerda (com verificação de colisão)
     */
    public void moveLeft() {
        if (isGameOver || !olhadesquerda()) return;
        bloco.esquerda();
        repaint();
    }

    /**
     * Move a peça para a direita (com verificação de colisão)
     */
    public void moveRight() {
        if (isGameOver || !olhadireita()) return;
        bloco.direita();
        repaint();
    }

    /**
     * Rotaciona a peça (com ajuste de posição se necessário)
     */
    public void rotate() {
        if (isGameOver) return;

        int formaAtual = bloco.getFormaAtual();
        bloco.rotacionar();

        // Ajustes de posição
        if (bloco.pegaBordaEsquerda() < 0) bloco.setGridX(0);
        if (bloco.pegaBordaDireita() >= GridColunas)
            bloco.setGridX(GridColunas - bloco.largura());
        if (bloco.pegaBordaInferior() >= GridLinha)
            bloco.setGridY(GridLinha - bloco.altura());

        if (temColisao()) {
            bloco.setFormaAtual(formaAtual);
        }
        repaint();
    }

    /**
     * Faz a peça cair rapidamente até o fundo
     */
    public void drop() {
        if (isGameOver) return;
        while (olhabaixo()) {
            moveDown();
        }
    }

    // ================== VERIFICAÇÕES DE COLISÃO ==================

    /**
     * Verifica se pode mover para baixo
     */
    private boolean olhabaixo() {
        if (bloco.pegay() + (bloco.altura() * GridCelula) >= getBounds().height) {
            return false;
        }

        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;
        int gridY = bloco.pegay() / GridCelula;

        /* Loop de verificação de colisão:
         * - Percorre coluna por coluna
         * - Em cada coluna, verifica de baixo para cima
         */
        for (int coluna = 0; coluna < bloco.largura(); coluna++) {
            for (int linha = bloco.altura() - 1; linha >= 0; linha--) {
                if (forma[linha][coluna] == 1) {
                    int nextY = gridY + linha + 1;
                    int nextX = gridX + coluna;

                    if (nextY >= GridLinha ||
                            (nextX >= 0 && nextX < GridColunas &&
                                    nextY >= 0 && backgorund[nextY][nextX] != null)) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    /**
     * Verifica se pode mover para a direita
     */
    public boolean olhadireita() {
        if (bloco.pegadireita() >= getBounds().width) return false;

        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;
        int gridY = bloco.pegay() / GridCelula;

        /* Loop de verificação:
         * - Percorre da direita para esquerda
         * - Verifica cada bloco da peça
         */
        for (int coluna = bloco.largura() - 1; coluna >= 0; coluna--) {
            for (int linha = 0; linha < bloco.altura(); linha++) {
                if (forma[linha][coluna] == 1) {
                    int nextY = gridY + linha;
                    int nextX = gridX + coluna + 1;

                    if (nextX >= GridColunas ||
                            (nextY >= 0 && nextY < GridLinha &&
                                    backgorund[nextY][nextX] != null)) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    /**
     * Verifica se pode mover para a esquerda
     */
    public boolean olhadesquerda() {
        if (bloco.pegaesquerda() <= 0) return false;

        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;
        int gridY = bloco.pegay() / GridCelula;

        /* Loop de verificação:
         * - Percorre da esquerda para direita
         * - Verifica cada bloco da peça
         */
        for (int coluna = 0; coluna < bloco.largura(); coluna++) {
            for (int linha = 0; linha < bloco.altura(); linha++) {
                if (forma[linha][coluna] == 1) {
                    int nextY = gridY + linha;
                    int nextX = gridX + coluna - 1;

                    if (nextX < 0 ||
                            (nextY >= 0 && nextY < GridLinha &&
                                    backgorund[nextY][nextX] != null)) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    // ================== LÓGICA DO JOGO ==================

    /**
     * Fixa a peça atual no fundo
     */
    private void moverparabackground() {
        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;
        int gridY = bloco.pegay() / GridCelula;
        Color cor = bloco.pegacor();

        /* Loop para transferir a peça para o fundo:
         * - Percorre toda a matriz da peça
         * - Marca as posições ocupadas no background
         */
        for (int linha = 0; linha < bloco.altura(); linha++) {
            for (int coluna = 0; coluna < bloco.largura(); coluna++) {
                if (forma[linha][coluna] == 1 &&
                        gridY + linha >= 0 && gridX + coluna >= 0 &&
                        gridY + linha < GridLinha && gridX + coluna < GridColunas) {
                    backgorund[gridY + linha][gridX + coluna] = cor;
                }
            }
        }
    }

    /**
     * Verifica condições de game over
     */
    public boolean gameover() {
        /* Loop de verificação:
         * - Checa apenas as 2 primeiras linhas (área de spawn)
         */
        for (int coluna = 0; coluna < GridColunas; coluna++) {
            if (backgorund[0][coluna] != null || backgorund[1][coluna] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove linhas completas e atualiza pontuação
     */
    public void limpalinhas() {
        boolean algumaLinhaCompleta = false;
        int linhasCompletas = 0;

        /* Loop principal - percorre de BAIXO PARA CIMA */
        for (int linha = GridLinha - 1; linha >= 0; linha--) {
            boolean linhafeita = true;

            /* Verificação de linha completa */
            for (int coluna = 0; coluna < GridColunas; coluna++) {
                if (backgorund[linha][coluna] == null) {
                    linhafeita = false;
                    break;
                }
            }

            if (linhafeita) {
                linhasCompletas++;

                /* Limpeza da linha */
                for (int coluna = 0; coluna < GridColunas; coluna++) {
                    backgorund[linha][coluna] = null;
                }

                /* Queda das peças superiores */
                for (int l = linha; l > 0; l--) {
                    for (int coluna = 0; coluna < GridColunas; coluna++) {
                        backgorund[l][coluna] = backgorund[l - 1][coluna];
                    }
                }

                /* Limpeza da linha do topo */
                for (int coluna = 0; coluna < GridColunas; coluna++) {
                    backgorund[0][coluna] = null;
                }

                linha++; // Reavalia a mesma posição
                algumaLinhaCompleta = true;
            }
        }

        if (linhasCompletas > 0) {
            linhasCompletasTotal += linhasCompletas;

            // Atualização da pontuação
            switch (linhasCompletas) {
                case 1: score += 100 * level; break;
                case 2: score += 300 * level; break;
                case 3: score += 500 * level; break;
                case 4: score += 800 * level; break;
            }

            level = (linhasCompletasTotal / 3) + 1;

            if (gameform != null) {
                gameform.updateScore(score);
                gameform.updateLevel(level);
            }
        }
    }

    // ================== RENDERIZAÇÃO ==================

    /**
     * Metodo principal de desenho
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha o grid
        g.setColor(Color.DARK_GRAY);

        /* Loop para linhas horizontais */
        for (int y = 0; y <= GridLinha; y++) {
            g.drawLine(0, y * GridCelula, getWidth(), y * GridCelula);
        }

        /* Loop para linhas verticais */
        for (int x = 0; x <= GridColunas; x++) {
            g.drawLine(x * GridCelula, 0, x * GridCelula, getHeight());
        }

        // Desenha os elementos do jogo
        criarbloco(g);
        desenharBackground(g);
    }

    /**
     * Desenha a peça atual
     */
    private void criarbloco(Graphics g) {
        /* Loop para renderizar cada parte da peça */
        for (int linha = 0; linha < bloco.altura(); linha++) {
            for (int coluna = 0; coluna < bloco.largura(); coluna++) {
                if (bloco.pegaforma()[linha][coluna] == 1) {
                    int x = (bloco.pegax() + coluna * GridCelula);
                    int y = (bloco.pegay() + linha * GridCelula);
                    gridQuadrado(g, x, y, bloco.pegacor());
                }
            }
        }
    }

    /**
     * Desenha as peças já fixadas
     */
    private void desenharBackground(Graphics g) {
        /* Loop para renderizar todo o fundo */
        for (int linha = 0; linha < GridLinha; linha++) {
            for (int coluna = 0; coluna < GridColunas; coluna++) {
                if (backgorund[linha][coluna] != null) {
                    int x = (coluna * GridCelula);
                    int y = (linha * GridCelula);
                    gridQuadrado(g, x, y, backgorund[linha][coluna]);
                }
            }
        }
    }

    /**
     * Desenha um quadrado individual com borda
     */
    private void gridQuadrado(Graphics g, int x, int y, Color cor) {
        g.setColor(cor);
        g.fillRect(x, y, GridCelula, GridCelula);
        g.setColor(Color.black);
        g.drawRect(x, y, GridCelula, GridCelula);
    }

    // ================== AUXILIARES ==================

    public void setGameform(Gameform gameform) {
        this.gameform = gameform;
    }

    private boolean temColisao() {
        int[][] forma = bloco.pegaforma();
        int gridX = bloco.pegax() / GridCelula;
        int gridY = bloco.pegay() / GridCelula;

        for (int linha = 0; linha < bloco.altura(); linha++) {
            for (int coluna = 0; coluna < bloco.largura(); coluna++) {
                if (forma[linha][coluna] == 1) {
                    int nextY = gridY + linha;
                    int nextX = gridX + coluna;

                    if (nextY >= 0 && nextX >= 0 &&
                            nextY < GridLinha && nextX < GridColunas &&
                            backgorund[nextY][nextX] != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}