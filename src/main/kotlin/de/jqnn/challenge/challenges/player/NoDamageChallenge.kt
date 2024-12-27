package de.jqnn.challenge.challenges.player

import de.jqnn.challenge.challenges.Challenge
import de.jqnn.challenge.timer.TimerState
import net.axay.kspigot.event.listen
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent

class NoDamageChallenge : Challenge("0.5 Herzen", Material.APPLE, emptyList(), "§c") {

    init {
        listen<PlayerDeathEvent> {
            if (!this.timer.isRunning()) return@listen
            if (!(this.enabled)) return@listen
            it.deathMessage(null)
            timer.updateState(TimerState.CHALLENGE_LOOSE, "§c" + it.entity.name + " §rhat §cSchaden §rerlitten§8.")
        }

        listen<PlayerJoinEvent> {
            if (!(this.enabled)) return@listen
            it.player.maxHealth = 0.5
            it.player.health = 0.5
        }
    }

    override fun onEnable() {
        Bukkit.getOnlinePlayers().forEach {
            it.maxHealth = 0.5
            it.health = 0.5
        }
    }

    override fun onDisable() {
        Bukkit.getOnlinePlayers().forEach {
            it.maxHealth = 20.0
            it.health = 20.0
        }
    }
}