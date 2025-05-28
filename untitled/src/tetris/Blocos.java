package tetris;
import java.awt.*;

public class Blocos {
    private int[][] forma;
    private Color cor;
    private int x;        // removido static
    private int y;        // removido static
    private int gridCelula;

    public Blocos(int[][] forma, Color cor, int gridCelula) {
        this.forma = forma;
        this.cor = cor;
        this.gridCelula = gridCelula;
        spawn();         // chama spawn no construtor
    }

    private void spawn() {
        x = (200 / 2) - (largura() * gridCelula / 2); // centraliza horizontalmente
        y = -altura() * gridCelula;                    // inicia acima do grid
    }

    public int pegay() {  // não é mais static
        return y;
    }

    public int[][] pegaforma() {return forma;}

    public Color pegacor() {return cor;}

    public int altura() {return forma.length;}

    public int largura() {return forma[0].length;}

    public int pegax() {return x;}

    public void baixo() {y += gridCelula;}

    public void direita() {x += gridCelula;}

    public void esquerda() {x -= gridCelula;}

    public int chao(){return y + pegay();}
}