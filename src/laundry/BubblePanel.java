package laundry;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BubblePanel extends JPanel {

    class Bubble {
        int x, y, size;

        Bubble(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }
    }

    private ArrayList<Bubble> bubbles = new ArrayList<>();
    private Random rand = new Random();

    public BubblePanel() {
        setBackground(new Color(204, 204, 255));

        // gumawa ng static bubbles
        for (int i = 0; i < 20; i++) {
            bubbles.add(new Bubble(
                rand.nextInt(800),
                rand.nextInt(600),
                rand.nextInt(50) + 15
            ));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        for (Bubble b : bubbles) {

            // main bubble (transparent)
            g2d.setColor(new Color(255, 255, 255, 80));
            g2d.fillOval(b.x, b.y, b.size, b.size);

            // outline
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.drawOval(b.x, b.y, b.size, b.size);

            // highlight (shine effect ✨)
            g2d.setColor(new Color(255, 255, 255, 180));
            g2d.fillOval(b.x + b.size/4, b.y + b.size/4, b.size/6, b.size/6);
        }
    }
}