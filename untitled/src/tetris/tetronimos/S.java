package tetris.tetronimos;
import tetris.Blocos;
import java.awt.Color;

public class S extends Blocos {
    public S(int TamanhoBloco) {
        super(new int[][]{
                {0, 1, 1,},
                {1, 1, 0}
        }, Color.YELLOW, TamanhoBloco);
    }
}