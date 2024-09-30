package org.example;

import Players.*;
import SwingComponents.Message;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Game {
    private final JPanel mainPanel;
    private final MigLayout migLayout;
    private static HashMap<Integer, Boolean> list;
    public static final HashMap<Integer, Player> players = new HashMap<>();

    private PlayerOne one;
    private PlayerTwo two;
    private PlayerThree three;
    private PlayerFour four;
    private Dealer dealer;

    private boolean isStarted;
    private boolean d= false;


    public Game(JPanel mainPanel, MigLayout migLayout, HashMap<Integer, Boolean> list, boolean isStarted) {
        this.mainPanel = mainPanel;
        this.isStarted = isStarted;
        this.migLayout = migLayout;
        this.list = list;
        distributeCards();
    }

    public HashMap<Integer, Player> getPlayers(){
        return players;
    }

    public Dealer getDealer(){
        return dealer;
    }

    public void quitPlayerOne() {
        if (one != null) {
            one.restart();
            list.remove(1);
            players.remove(1);
            one = null;
        }
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public void quitPlayerTwo() {
        if (two != null) {
            two.restart();
            list.remove(2);
            players.remove(2);
            two = null;
        }
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public void quitPlayerThree() {
        if (three != null) {
            three.restart();
            list.remove(3);
            players.remove(3);
            three = null;
        }
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public void quitPlayerFour() {
        if (four != null) {
            four.restart();
            list.remove(4);
            players.remove(4);
            four = null;
        }
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public void allPlayerQuit() {
        if (one == null && two == null && three == null && four == null) resetGame();
    }

    public void resetGame() {
        if (one != null) {
            one.restart();
        }
        if (two != null) {
            two.restart();
        }
        if (three != null) {
            three.restart();
        }
        if (four != null) {
            four.restart();
        }
        if (dealer != null) {
            dealer.restart();
        }
        BlackJack.gameIsStarted = !isStarted;
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    private void distributeCards() {
        for (Integer number : list.keySet()) {
            if (list.get(number)) {
                Player player = (Player) createPlayer(number);
                if (player != null) {
                    players.put(number, player);
                    player.setGame(this);
                }
            }
        }
        dealer = new Dealer(mainPanel, migLayout);
        dealer.setGame(this);
    }

    private Object createPlayer(Integer number) {
        return switch (number) {
            case 1 -> one = new PlayerOne(mainPanel, migLayout);
            case 2 -> two = new PlayerTwo(mainPanel, migLayout);
            case 3 -> three = new PlayerThree(mainPanel, migLayout);
            case 4 -> four = new PlayerFour(mainPanel, migLayout);
            default -> null;
        };
    }

    public boolean allPlayersStand() {
        for (Player player : players.values()) {
            if (!player.isStand()) {
                return false;
            }
        }
        dealer.cardDistribute();
        Timer timer = new Timer(3000, evt -> declareWinner());
        timer.setRepeats(false);
        timer.start();
        return true;
    }

    private void declareWinner() {
        if (players.get(1) != null) {
            Player player = players.get(1);
            String text = winnerjudge(player);
            winnerAnnounce(130, text);
        }
        if (players.get(2) != null) {
            Player player = players.get(2);
            String text = winnerjudge(player);
            winnerAnnounce(455, text);
        }
        if (players.get(3) != null) {
            Player player = players.get(3);
            String text = winnerjudge(player);
            winnerAnnounce(780, text);
        }
        if (players.get(4) != null) {
            Player player = players.get(4);
            String text = winnerjudge(player);
            winnerAnnounce(1090, text);
        }
    }

    private String winnerjudge(Player player) {
        if (player.getScore() > 21 && dealer.getScore() > 21) {
            return "YOU LOSE";
        }
        if (player.getScore() == dealer.getScore() && player.getScore() <= 21 && dealer.getScore() <= 21) {
            return "TIE";
        }
        if (player.getScore() == dealer.getScore()) {
            return "TIE";
        }
        if (player.getScore() > dealer.getScore() && player.getScore() <= 21) {
            return "YOU WON";
        }
        if (dealer.getScore() > player.getScore() && dealer.getScore() <= 21) {
            return "YOU LOSE";
        }
        if (dealer.getScore() > 21 && player.getScore() < 21) {
            return "YOU WON";
        }
        if (player.getScore() > 21 && dealer.getScore() <= 21) {
            return "YOU LOSE";
        }
        if (dealer.getScore() > player.getScore()) {
            return "YOU LOSE";
        }
        if (player.getScore() > dealer.getScore()) {
            return "YOU WON";
        }
        return "UNDETERMINED";
    }

    private void winnerAnnounce(int x, String result) {

        Timer timer = new Timer(0, b -> {

            Message msg = new Message();

            if (result.equals("YOU WON")) {
                msg.isWinner(result, true);
                msg.repaint();
                mainPanel.add(msg, "pos " + (x - 70) + " 680");
            } else if (result.equals("YOU LOSE")) {
                msg.isWinner(result, false);
                msg.repaint();
                mainPanel.add(msg, "pos " + (x - 80) + " 680");
            } else if (result.equals("TIE")) {
                msg.isWinner(result, true);
                msg.repaint();
                mainPanel.add(msg, "pos " + (x - 70) + " 680");
            } else {
                msg.isWinner("ERROR", false);
                msg.repaint();
                mainPanel.add(msg, "pos " + (x - 30) + " 680");
            }

            mainPanel.repaint();
            mainPanel.revalidate();


            Timer close = new Timer(5000, a -> {
                mainPanel.remove(msg);
                mainPanel.repaint();
                mainPanel.revalidate();
                resetGame();
            });
            close.setDelay(0);
            close.setRepeats(false);
            close.start();

        });
        timer.setDelay(0);
        timer.setRepeats(false);
        timer.start();

    }
}
