package de.jqnn.challenge.challenges.player

import de.jqnn.challenge.challenges.Challenge
import de.jqnn.challenge.timer.TimerState
import net.axay.kspigot.event.listen
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent

class NoFallDamageChallenge : Challenge("No Fall Damage", Material.LEATHER_BOOTS, emptyList(), "§c") {

    init {
        listen<EntityDamageEvent> {
            if (!this.timer.isRunning()) return@listen
            if (!(this.enabled)) return@listen
            if (it.entity !is Player) return@listen
            if (it.cause != EntityDamageEvent.DamageCause.FALL) return@listen

            val player = it.entity as Player
            if (player.gameMode != GameMode.SURVIVAL) return@listen
            timer.updateState(TimerState.CHALLENGE_LOOSE, "§c" + player.name + " §rhat §cFallschaden §rerlitten§8.")
        }
    }

}