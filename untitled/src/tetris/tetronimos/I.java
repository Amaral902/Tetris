package tetris.tetronimos;
import tetris.Blocos;
import java.awt.Color;

public class I extends Blocos {
    public I(int TamanhoBloco) {
        super(new int[][]{
            {1, 1, 1, 1}
        }, Color.cyan, TamanhoBloco);
    }
}