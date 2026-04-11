package laundry;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BubblePanel extends JPanel {

    class Bubble {
        float x, y;
        int size;
        float speed;
        float alpha;

        Bubble(float x, float y, int size, float speed, float alpha) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
            this.alpha = alpha;
        }
    }

    private ArrayList<Bubble> bubbles = new ArrayList<>();
    private Random rand = new Random();
    private Timer timer;

    public BubblePanel() {
        setBackground(new Color(204, 204, 255));

        // generate bubbles
        for (int i = 0; i < 25; i++) {
            bubbles.add(createBubble());
        }

        // animation timer (smooth)
        timer = new Timer(30, e -> updateBubbles());
        timer.start();
    }

    private Bubble createBubble() {
        return new Bubble(
            rand.nextInt(800),
            rand.nextInt(600),
            rand.nextInt(40) + 20,
            rand.nextFloat() * 1.5f + 0.5f, // speed
            rand.nextFloat() * 0.5f + 0.3f  // transparency
        );
    }

    private void updateBubbles() {
        for (Bubble b : bubbles) {
            b.y -= b.speed; // move upward

            // konting side movement (para realistic)
            b.x += Math.sin(b.y * 0.05) * 0.5;

            // kapag nawala sa taas, balik sa baba
            if (b.y + b.size < 0) {
                b.x = rand.nextInt(getWidth());
                b.y = getHeight() + b.size;
                b.size = rand.nextInt(40) + 20;
                b.speed = rand.nextFloat() * 1.5f + 0.5f;
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        for (Bubble b : bubbles) {

            // soft glow effect
            RadialGradientPaint glow = new RadialGradientPaint(
                new Point((int)b.x + b.size/2, (int)b.y + b.size/2),
                b.size,
                new float[]{0f, 1f},
                new Color[]{
                    new Color(255, 255, 255, (int)(b.alpha * 255)),
                    new Color(255, 255, 255, 0)
                }
            );

            g2d.setPaint(glow);
            g2d.fillOval((int)b.x, (int)b.y, b.size, b.size);

            // outline
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.drawOval((int)b.x, (int)b.y, b.size, b.size);

            // highlight (shine ✨)
            g2d.setColor(new Color(255, 255, 255, 200));
            g2d.fillOval((int)b.x + b.size/4, (int)b.y + b.size/4,
                         b.size/6, b.size/6);
        }
    }
}