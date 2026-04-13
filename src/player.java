public class player {
    int ranking;
    String name;

    public player(String name , int ranking){
        this.name = name;
        this.ranking = ranking;
    }

    // Getters e Setters
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
