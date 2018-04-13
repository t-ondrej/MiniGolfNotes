package sk.upjs.ics.minigolf.models;

public interface PlayerManager {
    Player findPlayerByPosition(int idx);
    Player findPlayerByName(String name);
    int getPlayerCount();
    void addPlayer(Player player);
    void removePlayerByPosition(int position);
}
