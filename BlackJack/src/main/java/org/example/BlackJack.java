package org.example;

import SwingComponents.Message;
import SwingComponents.MyButton;
import SwingComponents.MyButton2;
import com.formdev.flatlaf.FlatDarkLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BlackJack extends JFrame {

    private Game game;
    private JPanel mainPanel;
    private MigLayout migLayout;

    private final int MAX_PLAYERS = 4;
    private int CURRENT_PLAYERS = 0;

    public static boolean gameIsStarted = false;
    private static HashMap<Integer, Boolean> list;
    private boolean isPlayerOne, isPlayerTwo, isPlayerThree, isPlayerFour = false;


    public BlackJack() {
        init();
        initComponents();
        list = new HashMap<>();
        revalidate();
    }

    private void initComponents() {
        migLayout = new MigLayout("insets 0 0 0 0,fill");
        mainPanel = new JPanel(migLayout) {
            private final Image tableImage = new ImageIcon(getClass().getResource("/Image/table.jpg")).getImage();
            private final Image dealerImage = new ImageIcon(getClass().getResource("/Image/dealer.png")).getImage();
            private final Image cardBack = new ImageIcon(getClass().getResource("/Image/back.jpg")).getImage();

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g2d.drawImage(tableImage, 0, 0, getWidth(), getHeight(), this);
                g2d.drawImage(dealerImage, 570, 50, 100, 100, this);
                g2d.drawImage(cardBack, 500, 50, 70, 70, this);

                g2d.dispose();
            }
        };

        initPlayersTable();
        optionButton();


        add(mainPanel);
    }

    public void optionButton() {
        MyButton2 start = new MyButton2("START") {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                setEnabled(!gameIsStarted);
            }
        };
        start.addActionListener(e -> {
            if (CURRENT_PLAYERS < 1) {
                JOptionPane.showMessageDialog(this, "NO PLAYERS TO START");
            } else {
                gameIsStarted = true;
                game = new Game(mainPanel, migLayout, list, gameIsStarted);
            }
        });
        mainPanel.add(start, "pos 100 100");

        MyButton2 restart = new MyButton2("RESTART") {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                setEnabled(!gameIsStarted);
            }
        };
        restart.addActionListener(e -> {
            gameIsStarted = false;
            if (game != null) {
                game.resetGame();
            }
        });
        mainPanel.add(restart, "pos 200 100");
    }

    private void initPlayersTable() {
        JPanel panel = new JPanel(new MigLayout("fill", "[]20[]20[]20[]"));
        for (int i = 0; i < MAX_PLAYERS; i++) {
            MyButton playerButton = getButton(i + 1);
            panel.add(playerButton, "al center");
        }
        mainPanel.add(panel, "south, h 10%");
    }

    private MyButton getButton(int playerNumber) {
        MyButton player = new MyButton("SIT");
        player.setPreferredSize(new Dimension(250, 50));
        player.addActionListener(e -> {
            activePlayer(playerNumber, player);
        });
        return player;
    }

    private void activePlayer(int playerNumber, MyButton myButton) {
        boolean activePlayer = false;
        switch (playerNumber) {
            case 1 -> activePlayer = isPlayerOne;
            case 2 -> activePlayer = isPlayerTwo;
            case 3 -> activePlayer = isPlayerThree;
            case 4 -> activePlayer = isPlayerFour;
        }

        if (activePlayer) {
            int choice = JOptionPane.showConfirmDialog(this, "DO YOU WANT TO EXIT", "CONFIRMATION", JOptionPane.YES_NO_OPTION);
            if (choice == 0) {
                sitPlayer(playerNumber, myButton, false);
                switch (playerNumber) {
                    case 1 -> game.quitPlayerOne();
                    case 2 -> game.quitPlayerTwo();
                    case 3 -> game.quitPlayerThree();
                    case 4 -> game.quitPlayerFour();
                }
                game.allPlayerQuit();
                game.allPlayersStand();
            }
        } else {
            sitPlayer(playerNumber, myButton, true);
        }
    }

    private void sitPlayer(int playerNumber, MyButton myButton, boolean sit) {
        switch (playerNumber) {
            case 1 -> isPlayerOne = sit;
            case 2 -> isPlayerTwo = sit;
            case 3 -> isPlayerThree = sit;
            case 4 -> isPlayerFour = sit;
        }
        myButton.setText(sit ? "EXIT" : "SIT");
        myButton.setImage("/Image/player" + playerNumber + ".png");
        CURRENT_PLAYERS += sit ? 1 : -1;
        if (!sit) list.remove(playerNumber);
        else list.put(playerNumber, true);
    }


    private void init() {
        setSize(1300, 900);
        setTitle("BLACK JACK");
        setIconImage(new ImageIcon(getClass().getResource("/Image/stackofcards.png")).getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(BlackJack::new);
    }
}
