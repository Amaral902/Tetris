package tetris.tetronimos;
import tetris.Blocos;
import java.awt.Color;

public class Z extends Blocos {
    public Z(int TamanhoBloco) {
        super(new int[][]{
                {1, 1, 0,},
                {0, 1, 1}
        }, Color.MAGENTA, TamanhoBloco);
    }
}