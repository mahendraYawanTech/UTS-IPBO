package Zoo;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ZooFrame frame = new ZooFrame();
            frame.setVisible(true);
        });
    }
}
