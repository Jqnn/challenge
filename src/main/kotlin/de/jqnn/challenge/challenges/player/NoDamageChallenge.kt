package de.jqnn.challenge.challenges.player

import de.jqnn.challenge.challenges.Challenge
import org.bukkit.Bukkit
import org.bukkit.Material

class NoDamageChallenge : Challenge("0.5 Herzen", Material.APPLE, emptyList(), "§c") {

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