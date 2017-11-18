import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * Author: Linus Lagerhjelm
 * File: MainWindow
 * Created: 2017-11-16
 * Description:
 */
class MainWindow {
    private static MainWindow instance = null;
    JFrame frame;
    JPanel mainPanel;

    private MainWindow() {
        /* defeat initialization */
    }

    static MainWindow getInstance() {
        if (instance == null) instance = new MainWindow("Ants", 1280, 800);
        return instance;
    }

    private MainWindow(String title, int width, int height) {
        frame = new JFrame(title);
        mainPanel = new JPanel();
        SwingUtilities.invokeLater(() -> {
            frame.setPreferredSize(new Dimension(width, height));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.add(mainPanel);
            frame.pack();
            frame.setVisible(true);
        });
    }

    void painGraph(Set<City> cities, Set<SimpleEdge> edges) {
        mainPanel.removeAll();
        mainPanel.updateUI();

        Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
        g2d.setColor(Color.MAGENTA);

        cities.forEach(c -> {
            SwingUtilities.invokeLater(() -> g2d.fillOval((int)(c.getLat()/1.5), (int)(c.getLon()/1.5), 10, 10));
        });

        edges.forEach(e -> {
            SwingUtilities.invokeLater(() -> {
                Color c = new Color(0, 1, 0, Math.min((float) e.getPheromone(), 1));
                g2d.setColor(c);
                g2d.drawLine((int)(e.getStart().getLat()/1.5),
                            (int)(e.getStart().getLon()/1.5),
                            (int)(e.getEnd().getLat()/1.5),
                            (int)(e.getEnd().getLon()/1.5));
            });
        });
    }
}
