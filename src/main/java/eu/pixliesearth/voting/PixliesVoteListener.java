package eu.pixliesearth.voting;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PixliesVoteListener implements Listener {

    @EventHandler
    public void onVote(VotifierEvent event) {
        Vote vote = event.getVote();
        //TODO
    }

}
