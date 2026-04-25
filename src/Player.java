public class Player {
    private int ranking;
    private String name;

    public Player(String name , int ranking){
        this.name = name;
        this.ranking = ranking;
    }

    public String getName() {

        return name;
    }

    public void setName(String nickname) {

        this.name = nickname;
    }

    public int getRanking() {

        return ranking;
    }

    public void setRanking(int ranking) {

        this.ranking = ranking;
    }
}
