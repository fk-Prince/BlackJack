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

public class PlayerOne extends Player {
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
    private Stack<JLabel> stack = new Stack<>();

    public PlayerOne(JPanel mainPanel, MigLayout migLayout) {
        this.mainPanel = mainPanel;
        this.migLayout = migLayout;
        cardDistribute();
    }

    private void cardDistribute() {
        label = numberGenerator();
        stack.add(label);
        this.setCardCount(1);


        timer = new Timer(1, evt -> {
            CARD_POSITION_X -= 8;
            CARD_POSITION_Y += 10;
            if (label.getParent() != null) migLayout.setComponentConstraints(label, "pos " + CARD_POSITION_X + " " + CARD_POSITION_Y);
            else timer.stop();

            if (CARD_POSITION_Y == 500 && getCardCount() == 1) {
                timer.stop();
                CARD_POSITION_Y = 50;
                CARD_POSITION_X = 500;
                cardDistribute();
            }


            if ((CARD_POSITION_Y == 490 && getCardCount() == 2) ||
                    (CARD_POSITION_Y == 480 && getCardCount() == 3) ||
                    (CARD_POSITION_Y == 470 && getCardCount() == 4) ||
                    (CARD_POSITION_Y == 460 && getCardCount() == 5) ||
                    (CARD_POSITION_Y == 450 && getCardCount() == 6) ||
                    (CARD_POSITION_Y == 440 && getCardCount() == 7) ||
                    (CARD_POSITION_Y == 430 && getCardCount() == 8)) {
                timer.stop();
                getTotalScore();
            }

            if (!timer.isRunning()) {
                CARD_POSITION_Y = 50;
                CARD_POSITION_X = 500;
                displayScore();
                displayChoice();
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });
        timer.start();
    }

    private void displayScore() {
        if (field == null) {
            field = new JTextField(String.valueOf(this.getScore()));
            field.setFocusable(false);
            field.setPreferredSize(new Dimension(40, 40));
            field.setHorizontalAlignment(SwingConstants.CENTER);
            field.setFont(new Font("Arial", Font.BOLD, 17));
            mainPanel.add(field, "pos 135 600");
        } else if (getScore() > 21) {
            field.setText("BUST");
            removeChoices();
            mainPanel.repaint();
            mainPanel.revalidate();
        } else if (getScore() == 21 && getCardCount() == 2) {
            field.setText("BJ");
            removeChoices();
            mainPanel.repaint();
            mainPanel.revalidate();
        } else {
            field.setText(String.valueOf(getScore()));
            if (getScore() > 17) {
                getTotalScore();
                removeChoices();
            }
        }

    }

    public void removeChoices() {
        if (game.getPlayers().get(1) != null) {
            if (hit != null && stand != null) {
                mainPanel.remove(hit);
                mainPanel.remove(stand);
                mainPanel.repaint();
                mainPanel.revalidate();
                stand = null;
                hit = null;
            }
        }

    }

    private void displayChoice() {
        if (this.getScore() < 17) {
            hit = new MyButton("HIT");
            hit.addActionListener(e -> {
                removeChoices();
                cardDistribute();
            });
            hit.setPreferredSize(new Dimension(100, 40));
            mainPanel.add(hit, "pos 60 680");

            stand = new MyButton("STAND");
            stand.addActionListener(e -> {
                removeChoices();
                setStand(true);
                game.allPlayersStand();
            });
            stand.setPreferredSize(new Dimension(100, 40));
            mainPanel.add(stand, "pos 160 680");
        } else {
            if (hit != null && stand != null) {
                removeChoices();
            }
            setStand(true);
            game.allPlayersStand();
        }
    }

    private JLabel numberGenerator() {
        int randomNumber = new Random().nextInt(10) + 1;
        totalNumber1.add(randomNumber);
        JLabel label = new JLabel();
        Image image = new Cards().getCard(randomNumber).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
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
        if (timer != null && timer.isRunning()){
            timer.stop();
        }
        if (hit != null && stand != null) {
            removeChoices();
        }
        if (field != null) {
            mainPanel.remove(field);
            field = null;
        }
        for (JLabel i : stack) {
            mainPanel.remove(i);
        }
        reset();
        totalNumber1.clear();
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
