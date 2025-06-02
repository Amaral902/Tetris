package tetris.tetronimos;
import tetris.Blocos;
import java.awt.Color;

public class T extends Blocos {
    public T(int TamanhoBloco) {
        super(new int[][]{
                {0, 1, 0,},
                {1, 1, 1}
        }, Color.PINK, TamanhoBloco);
    }
}