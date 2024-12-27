package de.jqnn.challenge.commands

import de.jqnn.challenge.ChallengeSystem
import de.jqnn.challenge.extensions.cmp
import de.jqnn.challenge.extensions.plus
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import org.bukkit.Bukkit
import org.bukkit.entity.Player

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

object ResetCommand {

    private val challengeSystem = ChallengeSystem.challengeSystem
    private val prefix = this.challengeSystem.prefix
    private val configAdapter = this.challengeSystem.configAdapter

    init {
        command("reset") {
            this.runs {
                configAdapter.set("ResetOnRestart", true)
                configAdapter.save()

                Bukkit.getOnlinePlayers().forEach {
                    it.kick(prefix.plus(cmp("Die §bWelten §rwerden jetzt §bzurückgesetzt§8.")))
                }
                Bukkit.shutdown()
                return@runs
            }
        }
    }

}