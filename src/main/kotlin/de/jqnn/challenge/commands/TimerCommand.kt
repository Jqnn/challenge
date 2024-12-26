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

object TimerCommand {

    private val challengeSystem = ChallengeSystem.challengeSystem
    private val timer = this.challengeSystem.timer
    private val prefix = this.timer.prefix

    init {
        command("timer") {
            this.literal("time") {
                this.argument<Int>("seconds") {
                    this.runs {
                        val seconds = this.getArgument<Int>("seconds")
                        if (seconds < 0)
                            return@runs this.player.sendMessage(prefix.plus(cmp("Die Zeit darf nicht negativ sein§8.")))
                        timer.seconds = seconds
                        return@runs this.player.sendMessage(prefix.plus(cmp("Du hast die Zeit auf §b$seconds §rSekunden gesetzt§8.")))
                    }
                }
            }

            this.literal("resume") {
                this.runs {
                    if (timer.isRunning())
                        return@runs this.player.sendMessage(prefix.plus(cmp("Der Timer läuft bereits§8.")))

                    timer.updateState(TimerState.RUNNING)
                    return@runs this.player.sendMessage(prefix.plus(cmp("Du hast den Timer §bgestartet§8.")))
                }
            }

            this.literal("pause") {
                this.runs {
                    if (!(timer.isRunning()))
                        return@runs this.player.sendMessage(prefix.plus(cmp("Der Timer läuft nicht§8.")))

                    timer.updateState(TimerState.PAUSED)
                    return@runs this.player.sendMessage(prefix.plus(cmp("Du hast den Timer §bpausiert§8.")))
                }
            }

            this.literal("reset") {
                this.runs {
                    timer.updateState(TimerState.PAUSED)
                    timer.timerWay = TimerWay.ADJECTIVE
                    timer.seconds = 0
                    return@runs this.player.sendMessage(prefix.plus(cmp("Du hast den Timer §bzurückgesetzt§8.")))
                }
            }

            this.literal("reverse") {
                this.runs {
                    if (timer.timerWay == TimerWay.ADJECTIVE) {
                        timer.timerWay = TimerWay.REVERSE
                        return@runs this.player.sendMessage(prefix.plus(cmp("Der Timer läuft nun §bRückwärts§8.")))
                    }

                    timer.timerWay = TimerWay.ADJECTIVE
                    return@runs this.player.sendMessage(prefix.plus(cmp("Der Timer läuft nun §bVorwärts§8.")))
                }
            }
        }
    }

}