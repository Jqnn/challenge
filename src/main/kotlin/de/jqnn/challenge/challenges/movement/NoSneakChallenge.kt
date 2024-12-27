package de.jqnn.challenge.challenges.movement

import de.jqnn.challenge.challenges.Challenge
import de.jqnn.challenge.timer.TimerState
import net.axay.kspigot.event.listen
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.player.PlayerToggleSneakEvent

class NoSneakChallenge : Challenge("No Sneak", Material.GOLDEN_BOOTS, emptyList(), "§a") {

    init {
        listen<PlayerToggleSneakEvent> {
            if (!this.timer.isRunning()) return@listen
            if (!(this.enabled)) return@listen

            val player = it.player
            if (player.gameMode != GameMode.SURVIVAL) return@listen
            timer.updateState(TimerState.CHALLENGE_LOOSE, "§a" + player.name + " §rhat §agesneakt§8.")
        }
    }

}