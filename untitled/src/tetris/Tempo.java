package tetris;

public class Tempo extends Thread {
    private GameArea ga;
    private Gameform gf;
    private int velocidade = 1000;
    private int velocidadeBase = 1000;
    private boolean running = true;

    public Tempo(GameArea ga, Gameform gf) {
        this.ga = ga;
        this.gf = gf;
        ga.setGameform(gf);
    }

    @Override
    public void run() {
        while (running) {
            if (!ga.moveDown()) {
                running = false; // Para o loop quando o jogo acabar
                break;
            }
            
            // Calcula nova velocidade baseada no nível
            velocidade = velocidadeBase - ((ga.getLevel() - 1) * 100);
            if (velocidade < 100) velocidade = 100;

            try {
                Thread.sleep(velocidade);
            } catch (InterruptedException ex) {
                running = false;
                return;
            }
        }
    }

    public void stopGame() {
        running = false;
    }
}