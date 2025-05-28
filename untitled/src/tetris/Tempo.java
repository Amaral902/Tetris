package tetris;

public class Tempo extends Thread {
    private GameArea gameArea;
    
    public Tempo(GameArea gameArea) {
        this.gameArea = gameArea;
    }
    
    @Override
    public void run(){
        while (1==1) {
            try {
                this.gameArea.moveDown();
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }

}}