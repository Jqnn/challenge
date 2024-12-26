package de.jqnn.challenge.commands

import de.jqnn.challenge.ChallengeSystem
import de.jqnn.challenge.extensions.cmp
import de.jqnn.challenge.extensions.plus
import de.jqnn.challenge.timer.TimerState
import de.jqnn.challenge.timer.TimerWay
import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.literal
import net.axay.kspigot.commands.runs

object ChallengesCommand {

    private val challengeSystem = ChallengeSystem.challengeSystem
    private val challengeManager = this.challengeSystem.challengeManager
    private val challengeInventory = this.challengeManager.challengeInventory

    init {
        command("challenges") {
            this.runs {
                challengeInventory.openPage(this.player, 1)
            }
        }
    }

}