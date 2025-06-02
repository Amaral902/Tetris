package tetris.tetronimos;
import tetris.Blocos;
import java.awt.Color;

public class J extends Blocos {
    public J(int TamanhoBloco) {
        super(new int[][]{
                {0, 1},
                {0, 1},
                {1, 1}
        }, Color.GREEN, TamanhoBloco);
    }
}