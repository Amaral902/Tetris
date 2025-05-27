package tetris;
import javax.swing.JFrame;
public class Gameform extends javax.swing.JFrame{
    public Gameform() {
        initComponents();
       this.add(new GameArea());

    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gameform().setVisible(true);
            }
        });
    }

    private void initComponents() {
        // Additional initialization code, e.g., preparing GUI components, can be added here.
    }
}
