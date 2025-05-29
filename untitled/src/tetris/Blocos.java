package tetris;

import java.awt.Color;

public class Blocos {
    private int[][] forma;
    private Color cor;
    private int x;
    private int y;
    private int TamanhoBloco;
    private int[][] formas[];  // Array para armazenar rotações
    private int formaatual;

    // Construtor que inicializa um novo bloco
    public Blocos(int[][] forma, Color cor, int TamanhoBloco) {
        this.cor = cor;
        this.TamanhoBloco = TamanhoBloco;
        
        // Primeiro inicializa as formas
        initShapes(forma);
        
        // Depois configura a forma inicial
        formaatual = 0;
        this.forma = formas[formaatual];
        
        // Inicializa o bloco no topo da tela
        x = 3 * TamanhoBloco;  // Posição inicial X (3 células da esquerda)
        y = -4 * TamanhoBloco; // Posição inicial Y (4 células acima da área visível)
    }

    private void initShapes(int[][] formaInicial) {
    // Inicializa array com 4 rotações possíveis
    formas = new int[4][][];  // Array para 4 rotações diferentes
    
    // A primeira forma é a inicial
    formas[0] = formaInicial;
    
    // Calcula as outras rotações
    for(int i = 1; i < 4; i++) {
        formas[i] = rotacionarMatriz(formas[i-1]);
    }
}

    // Metodo auxiliar para rotacionar uma matriz 90 graus
    private int[][] rotacionarMatriz(int[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        int[][] rotacionada = new int[colunas][linhas];
        
        for(int i = 0; i < linhas; i++) {
            for(int j = 0; j < colunas; j++) {
                rotacionada[j][linhas-1-i] = matriz[i][j];
            }
        }
        return rotacionada;
    }

    // Rotaciona a peça para a próxima posição
    public void rotacionar() {
        formaatual = (formaatual + 1) % 4;  // Alterna entre 0,1,2,3
        forma = formas[formaatual];
    }

    // Move o bloco uma célula para a esquerda
    public void esquerda() {
        x -= TamanhoBloco;
    }

    // Move o bloco uma célula para a direita
    public void direita() {
        x += TamanhoBloco;
    }

    // Move o bloco uma célula para baixo
    public void baixo() {
        y += TamanhoBloco;
    }

    // Retorna a matriz que define o formato do bloco
    public int[][] pegaforma() {
        return forma;
    }

    // Retorna a posição X atual do bloco
    public int pegax() {
        return x;
    }

    // Retorna a posição Y atual do bloco
    public int pegay() {
        return y;
    }


    // Define uma nova posição X
    public void setX(int newX) {
        x = newX;
    }

    // Define uma nova posição Y
    public void setY(int newY) {
        y = newY;
    }

    // Retorna a cor do bloco
    public Color pegacor() {
        return cor;
    }

    // Retorna a largura do bloco (número de colunas na matriz forma)
    public int largura() {
        return forma[0].length;
    }

    // Retorna a altura do bloco (número de linhas na matriz forma)
    public int altura() {
        return forma.length;
    }
    public int pegadireita(){
        return x;
    }
    public int pegaesquerda(){
    return x;
    }
}