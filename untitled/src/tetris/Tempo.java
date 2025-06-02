package tetris;

public class Tempo extends Thread {
    // Referência à área do jogo
    private GameArea ga;
    // Referência ao formulário do jogo
    private Gameform gf;
    // Velocidade atual de queda dos blocos (em milissegundos)
    private int velocidade = 700;
    // Velocidade inicial/base do jogo
    private int velocidadeBase = 700;
    // Controla se o jogo está rodando
    private boolean running = true;

    public Tempo(GameArea ga, Gameform gf) {
        this.ga = ga;
        this.gf = gf;
        ga.setGameform(gf);
    }

    @Override
    public void run() {
        // Loop principal do jogo
        while (running) {
            // Tenta mover o bloco para baixo
            if (!ga.moveDown()) {
                running = false; // Se não conseguir, jogo acabou
                break;
            }
            
            // Aumenta a velocidade baseado no nível
            velocidade = velocidadeBase - ((ga.getLevel() - 1) * 100);
            // Limita a velocidade mínima
            if (velocidade < 100) velocidade = 100;

            try {
                // Pausa entre movimentos
                Thread.sleep(velocidade);
            } catch (InterruptedException ex) {
                running = false;
                return;
            }
        }
    }

    // Método para parar o jogo
    public void stopGame() {
        running = false;
    }
}