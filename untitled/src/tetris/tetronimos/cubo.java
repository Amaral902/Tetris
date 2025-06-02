package tetris.tetronimos;
import tetris.Blocos;
import java.awt.Color;

public class cubo extends Blocos {
    public cubo(int TamanhoBloco) {
        super(new int[][]{
                {1, 1,},
                {1, 1,}
        }, Color.GREEN, TamanhoBloco);
    }
}
