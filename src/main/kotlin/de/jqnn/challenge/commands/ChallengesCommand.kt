package de.jqnn.challenge.commands

import de.jqnn.challenge.ChallengeSystem
import net.axay.kspigot.commands.command
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