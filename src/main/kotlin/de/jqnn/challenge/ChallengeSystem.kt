package de.jqnn.challenge

import de.jqnn.challenge.challenges.ChallengeManager
import de.jqnn.challenge.commands.ChallengesCommand
import de.jqnn.challenge.commands.PositionCommand
import de.jqnn.challenge.commands.ResetCommand
import de.jqnn.challenge.commands.TimerCommand
import de.jqnn.challenge.config.ConfigAdapter
import de.jqnn.challenge.extensions.cmp
import de.jqnn.challenge.listener.InventoryClickListener
import de.jqnn.challenge.positions.Position
import de.jqnn.challenge.positions.PositionManager
import de.jqnn.challenge.timer.Timer
import de.jqnn.challenge.timer.TimerState
import net.axay.kspigot.main.KSpigot
import java.io.File

class ChallengeSystem : KSpigot() {

    companion object {
        lateinit var challengeSystem: ChallengeSystem
    }

    val prefix = cmp("§8§l[§9Challenge§8§l] §r")
    lateinit var configAdapter: ConfigAdapter
    lateinit var timer: Timer
    lateinit var challengeManager: ChallengeManager
    lateinit var positionManager: PositionManager

    var reset = false

    override fun load() {
        challengeSystem = this
        this.configAdapter = ConfigAdapter("", "config.json")
        this.configAdapter.load()
    }

    override fun startup() {
        this.timer = Timer()
        this.challengeManager = ChallengeManager()
        this.positionManager = PositionManager()

        ChallengesCommand
        PositionCommand
        ResetCommand
        TimerCommand

        InventoryClickListener
    }

    override fun shutdown() {
        this.challengeManager.saveChallenges()
        this.positionManager.savePositions()
        this.timer.updateState(TimerState.PAUSED)
        this.timer.save()

        if (!(this.reset)) return
        File("world").deleteRecursively()
        File("world_nether").deleteRecursively()
        File("world_the_end").deleteRecursively()

        this.configAdapter.set("Timer.Time", "0")
        this.configAdapter.set("Positions", emptyList<Position>())
        this.configAdapter.save()
    }
}
