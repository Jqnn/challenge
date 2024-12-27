package de.jqnn.challenge.challenges.other

import de.jqnn.challenge.challenges.Challenge
import de.jqnn.challenge.extensions.cmp
import de.jqnn.challenge.extensions.plus
import de.jqnn.challenge.timer.TimerState
import net.axay.kspigot.event.listen
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDeathEvent

/********************************************************************************
 *    Urheberrechtshinweis                                                      *
 *    Copyright © Jan Scherping 2024                                            *
 *    Erstellt: 27.12.2024 / 01:59                                              *
 *                                                                              *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.          *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich ander gekennzeichnet,   *
 *    bei Jan Scherping. Alle Rechte vorbehalten.                               *
 *                                                                              *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,       *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                        *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Jan Scherping.    *
 ********************************************************************************/

class TwoMobKillChallenge : Challenge("Two Mob Kill", Material.IRON_SWORD, emptyList(), "§d") {

    private var killedMobs = 0

    init {
        if (this.configAdapter.existsKey("Challenge.KilledMobs"))
            killedMobs = this.configAdapter.getInt("Challenge.KilledMobs")
        listen<EntityDeathEvent> {
            if (!this.timer.isRunning()) return@listen
            if (!(this.enabled)) return@listen
            val entityType = it.entityType
            if (entityType == EntityType.ENDER_DRAGON || entityType == EntityType.END_CRYSTAL || entityType == EntityType.WITHER) return@listen
            if (it.entity.killer == null) return@listen
            if (it.entity.killer !is Player) return@listen

            val player = it.entity.killer ?: return@listen
            if (player.gameMode != GameMode.SURVIVAL) return@listen

            this.killedMobs++
            if (this.killedMobs > 2) {
                timer.updateState(TimerState.CHALLENGE_LOOSE, "${player.name} hat zu viele §dEntities §rgetötet§8.")
                return@listen
            }

            Bukkit.broadcast(this.prefix.plus(cmp("§d${entityType.name} §rwurde getötet§8. §rNoch übrige Kills§8: §d${2 - this.killedMobs}")))
        }
    }

    override fun onReset() {
        this.configAdapter.set("Challenge.KilledMobs", "0")
        this.configAdapter.save()
    }

    override fun onSave() {
        this.configAdapter.set("Challenge.KilledMobs", "$killedMobs")
    }
}