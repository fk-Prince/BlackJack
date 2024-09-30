package Players;

import SwingComponents.MyButton;
import net.miginfocom.swing.MigLayout;
import org.example.Cards;
import org.example.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Dealer extends Player {
    private int CARD_POSITION_X = 500;
    private int CARD_POSITION_Y = 50;

    private final JPanel mainPanel;
    private final MigLayout migLayout;
    private Game game;

    private static ArrayList<Integer> totalNumber1 = new ArrayList<>();

    private Timer timer;
    private static JTextField field;
    private static MyButton hit, stand;
    private JLabel label;
    private static Stack<JLabel> stack = new Stack<>();

    private int CARD_ONE = 0;
    private int CARD_SECOND = 0;

    public Dealer(JPanel mainPanel, MigLayout migLayout) {
        this.mainPanel = mainPanel;
        this.migLayout = migLayout;
        cardDistribute();

    }

    public void cardDistribute() {

        if (getScore() > 17) {
            revealSecondCard();
            displayScore();
            return;
        }
        this.setCardCount(1);
        label = numberGenerator();
        stack.push(label);
        getTotalScore();


        timer = new Timer(1, evt -> {
            CARD_POSITION_X += 12;
            if (label.getParent() != null) migLayout.setComponentConstraints(label, "pos " + CARD_POSITION_X + " " + CARD_POSITION_Y);
            else timer.stop();


            if (CARD_POSITION_X == 680 && getCardCount() == 1) {
                timer.stop();
                CARD_POSITION_Y = 50;
                CARD_POSITION_X = 500;
                cardDistribute();
            }

            if (CARD_POSITION_X == 692 && getCardCount() == 2) {
                timer.stop();
                getTotalScore();
            }
            if ((CARD_POSITION_X == 704 && getCardCount() == 3) ||
                    (CARD_POSITION_X == 716 && getCardCount() == 4) ||
                    (CARD_POSITION_X == 728 && getCardCount() == 5) ||
                    (CARD_POSITION_X == 740 && getCardCount() == 6) ||
                    (CARD_POSITION_X == 752 && getCardCount() == 7) ||
                    (CARD_POSITION_X == 764 && getCardCount() == 8)) {

                if (getScore() < 17) {
                    Timer delayTimer = new Timer(300, e -> cardDistribute());
                    delayTimer.setRepeats(false);
                    delayTimer.start();
                }

                revealSecondCard();
                timer.stop();
            }

            if (getCardCount() == 3){
                revealSecondCard();
            }


            if (!timer.isRunning()) {
                CARD_POSITION_Y = 50;
                CARD_POSITION_X = 500;
                displayScore();
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });
        timer.start();
    }

    private void displayScore() {
        if (field == null) {
            field = new JTextField(String.valueOf(CARD_ONE));
            field.setFocusable(false);
            field.setPreferredSize(new Dimension(40, 40));
            field.setHorizontalAlignment(SwingConstants.CENTER);
            field.setFont(new Font("Arial", Font.BOLD, 17));
            mainPanel.add(field, "pos 680 150");
        } else {
            if (getScore() > 21) {
                field.setText("BUST");
            } else if (getScore() == 21 && getCardCount() == 2) {
                field.setText("BJ");
            } else {
                field.setText(String.valueOf(getScore()));
            }
        }
    }

    private void revealSecondCard() {
        JLabel revealSecondCard = stack.get(1);
        Image image = new Cards().getCard(CARD_SECOND).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        revealSecondCard.setIcon(new ImageIcon(image));
        revealSecondCard.revalidate();
        mainPanel.repaint();
        mainPanel.revalidate();
    }


    private JLabel numberGenerator() {
        int randomNumber = new Random().nextInt(10) + 1;
        totalNumber1.add(randomNumber);
        if (getCardCount() == 1) {
            CARD_ONE = randomNumber;
        } else if (getCardCount() == 2) {
            CARD_SECOND = randomNumber;
        }
        JLabel label = new JLabel();
        Image image;
        if (getCardCount() == 2) {
            image = new ImageIcon(getClass().getResource("/Image/back.jpg")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } else {
            image = new Cards().getCard(randomNumber).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        }
        label.setIcon(new ImageIcon(image));
        mainPanel.add(label);
        return label;
    }

    private void getTotalScore() {
        int total = 0;
        for (Integer i : totalNumber1) {
            total += i;
        }
        this.setScore(total);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void restart() {
        if (hit != null && stand != null) {
            mainPanel.remove(hit);
            mainPanel.remove(stand);
        }
        if (field != null) {
            mainPanel.remove(field);
            field = null;
        }
        for (JLabel i : stack) {
            mainPanel.remove(i);
        }
        stack.clear();
        label = null;
        CARD_ONE = 0;
        CARD_SECOND = 0;
        setCardCount(0);
        totalNumber1.clear();
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
