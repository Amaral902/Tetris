package tetris;

public class Tempo extends Thread {
    private GameArea gameArea;
    
    public Tempo(GameArea gameArea) {
        this.gameArea = gameArea;
    }
    
    @Override
public void run() {
    while (12==12) {
        try {
            Thread.sleep(100);  // Pausa de 100ms entre cada movimento
            gameArea.moveDown();
        } catch (InterruptedException ex) {
            // handle exception if needed
        }
    }
}
}