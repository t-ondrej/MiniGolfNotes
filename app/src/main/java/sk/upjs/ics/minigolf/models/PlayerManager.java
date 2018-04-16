package sk.upjs.ics.minigolf.models;

import java.util.List;

public interface PlayerManager {
    Player findPlayerByPosition(int idx);
    Player findPlayerByName(String name);
    int getPlayerCount();
    void addPlayer(Player player);
    void removePlayerByPosition(int position);
    List<Player> getPlayersSortedByScore();
    List<Player> getPlayers();
}
