package de.jqnn.challenge.challenges

import de.jqnn.challenge.challenges.blocks.NoBlockBreakChallenge
import de.jqnn.challenge.challenges.inventory.ChallengeInventory
import de.jqnn.challenge.challenges.blocks.NoBlockPlaceChallenge
import de.jqnn.challenge.challenges.movement.NoJumpChallenge
import de.jqnn.challenge.challenges.movement.NoSneakChallenge
import org.bukkit.inventory.ItemStack
import org.slf4j.LoggerFactory

class ChallengeManager {

    private val logger = LoggerFactory.getLogger(this::class.java)
    val challenges = mutableListOf<Challenge>()
    val challengeInventory: ChallengeInventory

    init {
        this.logger.info("Loading challenges...")

        this.challenges.add(NoBlockBreakChallenge())
        this.challenges.add(NoBlockPlaceChallenge())

        this.challenges.add(NoJumpChallenge())
        this.challenges.add(NoSneakChallenge())

        this.logger.info("Loaded ${this.challenges.size} challenges.")
        this.challengeInventory = ChallengeInventory(this)
    }

    fun getChallenge(itemStack: ItemStack) = this.challenges.firstOrNull { it.itemStack.isSimilar(itemStack) }

    fun saveChallenges() {
        this.challenges.forEach { it.save() }
    }
}