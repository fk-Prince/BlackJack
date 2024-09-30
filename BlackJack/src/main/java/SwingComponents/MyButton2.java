package SwingComponents;

import javax.swing.*;
import java.awt.*;

public class MyButton2 extends JButton {

    private String text;
    private boolean isPlayer = false;
    private Image image;

    public MyButton2() {

    }

    public MyButton2(String text) {
        this.text = text;
        setText(text);
        setFont(new Font("Arial", Font.PLAIN, 15));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);
        setOpaque(false);
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getModel().isRollover() ? new Color(251, 251, 251) : getBackground());
        g2d.fillRoundRect(2, 3, getWidth() - 3, getHeight() - 4, 50, 50);


        setForeground(getModel().isRollover() ? Color.BLACK : Color.WHITE);


        g2d.dispose();
        super.paintComponent(g);
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 50);
    }



}
