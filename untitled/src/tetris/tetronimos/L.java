package tetris.tetronimos;
import tetris.Blocos;
import java.awt.Color;

public class L extends Blocos {
    public L(int TamanhoBloco) {
        super(new int[][]{
                {1, 0},
                {1, 0},
                {1, 1}
        }, Color.RED, TamanhoBloco);
    }
}