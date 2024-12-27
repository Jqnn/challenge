package de.jqnn.challenge.commands

import com.mojang.brigadier.arguments.StringArgumentType
import de.jqnn.challenge.ChallengeSystem
import de.jqnn.challenge.extensions.cmp
import de.jqnn.challenge.extensions.plus
import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import org.bukkit.Bukkit

/********************************************************************************
 *    Urheberrechtshinweis                                                      *
 *    Copyright © Jan Scherping 2024                                            *
 *    Erstellt: 27.12.2024 / 02:14                                              *
 *                                                                              *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.          *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich ander gekennzeichnet,   *
 *    bei Jan Scherping. Alle Rechte vorbehalten.                               *
 *                                                                              *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,       *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                        *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Jan Scherping.    *
 ********************************************************************************/

object PositionCommand {

    private val challengeSystem = ChallengeSystem.challengeSystem
    private val positionManager = this.challengeSystem.positionManager
    private val prefix = this.positionManager.prefix

    init {
        command("position") {
            this.argument<String>("name", StringArgumentType.word()) {
                this.runs {
                    val locationName = this.getArgument<String>("name")

                    if (positionManager.getPosition(locationName) != null) {
                        val position = positionManager.getPosition(locationName) ?: return@runs
                        this.player.sendMessage(prefix.plus(cmp("§5$locationName §rist bei §5${position.x}§8/§5${position.y}§8/§5${position.z} §8[§5${position.world}§8]")))
                        return@runs
                    }

                    val location = this.player.location
                    positionManager.createLocation(locationName, location)
                    Bukkit.broadcast(prefix.plus(cmp("§5$locationName §rist bei §5${location.blockX}§8/§5${location.blockY}§8/§5${location.blockZ} §8[§5${location.world.name}§8]")))
                }
            }
        }
    }

}