package de.jqnn.challenge.timer

import de.jqnn.challenge.ChallengeSystem
import de.jqnn.challenge.extensions.cmp
import de.jqnn.challenge.extensions.plus
import net.kyori.adventure.title.TitlePart
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Sound

class Timer {

    private val challengeSystem = ChallengeSystem.challengeSystem
    private val configAdapter = this.challengeSystem.configAdapter
    private val systemPrefix = this.challengeSystem.prefix

    val prefix = cmp("§8§l[§9Timer§8§l] §r")
    var timerWay: TimerWay
    var seconds: Int
    var timerState: TimerState

    init {
        if (!(this.configAdapter.existsKey("Timer.Way"))) {
            this.configAdapter.set("Timer.Way", TimerWay.ADJECTIVE.name)
            this.configAdapter.set("Timer.Time", "0")
            this.configAdapter.save()
        }

        this.timerState = TimerState.PAUSED
        this.timerWay = TimerWay.valueOf(this.configAdapter.getString("Timer.Way"))
        this.seconds = this.configAdapter.getInt("Timer.Time")

        Bukkit.getScheduler().scheduleSyncRepeatingTask(ChallengeSystem.challengeSystem, {
            if (!(this.isRunning())) {
                Bukkit.getOnlinePlayers().forEach { it.sendActionBar(this.prefix.plus(cmp("Pausiert"))) }
                return@scheduleSyncRepeatingTask
            }

            Bukkit.getOnlinePlayers()
                .forEach { it.sendActionBar(this.prefix.plus(cmp(TimeConverter.convert(this.seconds)))) }

            this.challengeSystem.challengeManager.challenges.forEach { it.onTimerTick() }
            if (this.timerWay == TimerWay.ADJECTIVE) this.seconds += 1
            else {
                this.seconds -= 1
                if (this.seconds <= 0) this.updateState(TimerState.CHALLENGE_LOOSE, "Die Zeit ist §babgelaufen§8.")
            }
        }, 20, 20)
    }

    fun updateState(timerState: TimerState, message: String? = null) {
        if (this.timerState == timerState) return
        this.timerState = timerState

        if (this.timerState == TimerState.RUNNING) {
            Bukkit.getOnlinePlayers().forEach {
                it.sendTitlePart(TitlePart.TITLE, this.prefix.plus(cmp("§8» §aGestartet")))
                it.playSound(it.location, Sound.ENTITY_PLAYER_LEVELUP, 2F, 2F)
            }
            this.challengeSystem.challengeManager.challenges.forEach { it.onTimerStart() }
            return
        }

        if (this.timerState == TimerState.PAUSED) {
            Bukkit.getOnlinePlayers().forEach {
                it.sendTitlePart(TitlePart.TITLE, this.prefix.plus(cmp("§8» §cPausiert")))
                it.playSound(it.location, Sound.BLOCK_NOTE_BLOCK_BASS, 2F, 2F)
            }
            this.challengeSystem.challengeManager.challenges.forEach { it.onTimerStop() }
            return
        }

        if (this.timerState == TimerState.CHALLENGE_WIN) {
            Bukkit.getOnlinePlayers().forEach {
                it.sendMessage(" ")
                it.sendMessage(this.systemPrefix.plus(cmp("Die §bChallenge §rwurde §bgemeistert§8.")))
                it.sendMessage(this.systemPrefix.plus(cmp(message ?: "§rHerzlichen Glückwunsch!")))
                it.sendMessage(this.systemPrefix.plus(cmp("Zeit §8» §r${TimeConverter.convert(this.seconds)}")))
                it.sendMessage(this.systemPrefix.plus(cmp("Der §bSeed §rwar §b${it.world.seed}")))
                it.sendMessage(" ")
            }
            this.challengeSystem.challengeManager.challenges.forEach { it.onTimerStop() }
            return
        }

        Bukkit.getOnlinePlayers().forEach {
            it.gameMode = GameMode.SPECTATOR
            it.sendMessage(" ")
            it.sendMessage(this.systemPrefix.plus(cmp("Die §bChallenge §rwurde §nnicht§r §bgemeistert§8.")))
            it.sendMessage(this.systemPrefix.plus(cmp(message ?: "§rSchade!")))
            it.sendMessage(this.systemPrefix.plus(cmp("Zeit §8» §r${TimeConverter.convert(this.seconds)}")))
            it.sendMessage(this.systemPrefix.plus(cmp("Der §bSeed §rwar §b${it.world.seed}")))
            it.sendMessage(" ")
        }
        this.challengeSystem.challengeManager.challenges.forEach { it.onTimerStop() }
    }

    fun save() {
        configAdapter.set("Timer.Time", "${this.seconds}")
        configAdapter.set("Timer.Way", this.timerWay.name)
        configAdapter.save()
    }

    fun isRunning() = this.timerState == TimerState.RUNNING
}