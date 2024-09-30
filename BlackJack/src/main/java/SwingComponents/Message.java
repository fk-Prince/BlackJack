package SwingComponents;

import javax.swing.*;
import java.awt.*;

public class Message extends JPanel {

    private String text;
    private boolean bln;

    public Message(String text, boolean bln) {
        this.text = text;
        this.bln = bln;
        init();
    }

    public Message() {

    }

    public void setText(String text) {
        this.text = text;
    }

    public void isWinner(String text, boolean bln) {
        this.bln = bln;
        this.text = text;
    }


    public void init() {
        setLayout(new BorderLayout());
        setBackground(new Color(6, 12, 16));
        setOpaque(false);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillOval(0, 0, getWidth(), getHeight());

        g2d.setColor(bln ? new Color(86, 204, 201) : new Color(243, 6, 49));
        g2d.setStroke(new BasicStroke(10));

        g2d.drawLine(0, 0, 0, getHeight());
        g2d.drawLine(getWidth(), 0, getWidth(), getHeight());

        FontMetrics fm = g2d.getFontMetrics();
        g2d.setFont(new Font("Arial", Font.PLAIN,17));
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawString(text, (getWidth() - fm.stringWidth(text) - 17) / 2, (getHeight() + fm.getAscent()) / 2);

        g2d.dispose();

    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 50);
    }

}
