package Players;


import org.example.Game;

public class Player {
    private boolean stand;
    private Game game;
    private int score;
    private int cardCount = 0;

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount += cardCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isStand() {
        return stand;
    }

    public void setStand(boolean stand) {
        this.stand = stand;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void reset(){
        cardCount = 0;
        stand = false;
        score = 0;
    }



}
