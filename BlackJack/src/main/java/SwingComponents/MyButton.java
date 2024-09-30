package SwingComponents;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    private String text;
    private boolean isPlayer = false;
    private Image image;

    public MyButton() {

    }

    public MyButton(String text) {
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
        g2d.fillRoundRect(2, 3, getWidth() - 3, getHeight() - 4, 20, 20);


        setForeground(getModel().isRollover() ? Color.BLACK : Color.WHITE);
        g2d.setColor(getModel().isRollover() ? getBackground() : new Color(251, 251, 251) );
        g2d.drawRoundRect(2, 3, getWidth() - 3, getHeight() - 4, 20, 20);

        if (isPlayer) {
            g2d.drawImage(image, 10, 0, 50, 50, this);
        }

        g2d.dispose();
        super.paintComponent(g);
    }


    public void setImage(String image) {
        this.image = new ImageIcon(getClass().getResource(image)).getImage();
        isPlayer = !isPlayer;
    }

}
