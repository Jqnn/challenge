package de.jqnn.challenge.challenges.blocks

import de.jqnn.challenge.challenges.Challenge
import de.jqnn.challenge.timer.TimerState
import net.axay.kspigot.event.listen
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent

class NoBlockBreakChallenge :
    Challenge("No Block Break", Material.IRON_PICKAXE, emptyList(), "§3") {

    init {
        listen<BlockBreakEvent> {
            if (!this.timer.isRunning()) return@listen
            if (!(this.enabled)) return@listen

            val player = it.player
            if (player.gameMode != GameMode.SURVIVAL) return@listen
            timer.updateState(TimerState.CHALLENGE_LOOSE, "§3" + player.name + " §rhat einen Block §3abgebaut§8.")
        }
    }
}