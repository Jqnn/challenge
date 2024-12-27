package de.jqnn.challenge.challenges.movement

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import de.jqnn.challenge.challenges.Challenge
import de.jqnn.challenge.timer.TimerState
import net.axay.kspigot.event.listen
import org.bukkit.GameMode
import org.bukkit.Material

class NoJumpChallenge : Challenge("No Jump", Material.RABBIT_FOOT, emptyList(), "§a") {

    init {
        listen<PlayerJumpEvent> {
            if (!this.timer.isRunning()) return@listen
            if (!(this.enabled)) return@listen

            val player = it.player
            if (player.gameMode != GameMode.SURVIVAL) return@listen
            timer.updateState(TimerState.CHALLENGE_LOOSE, "§a" + player.name + " §rist §agesprungen§8.")
        }
    }

}