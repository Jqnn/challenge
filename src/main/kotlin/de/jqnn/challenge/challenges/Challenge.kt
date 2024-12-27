package de.jqnn.challenge.challenges

import de.jqnn.challenge.ChallengeSystem
import de.jqnn.challenge.config.ConfigAdapter
import de.jqnn.challenge.extensions.cmp
import net.axay.kspigot.items.itemStack
import net.kyori.adventure.text.Component
import org.bukkit.Material

abstract class Challenge(
    private val name: String,
    material: Material,
    private val description: List<Component>,
    private val colorCode: String = "§9"
) {

    val timer = ChallengeSystem.challengeSystem.timer
    val displayName = "${this.colorCode}${this.name}"
    val prefix = cmp("§8§l[${this.colorCode}${this.name}§8§l] §r")

    var enabled: Boolean
    protected val configAdapter: ConfigAdapter
    private val configName = (this.name.replace(" ", "_") + ".json").lowercase()

    init {
        this.configAdapter = ConfigAdapter("Challenges", this.configName)
        this.configAdapter.load()
        if (!(this.configAdapter.existsKey("Challenge.Enabled"))) {
            this.configAdapter.set("Challenge.Enabled", false)
            this.configAdapter.save()
        }

        this.enabled = configAdapter.getBoolean("Challenge.Enabled")
    }

    fun enable() {
        this.onEnable()
        this.enabled = true
    }

    fun disable() {
        this.onDisable()
        this.enabled = false
    }

    fun save() {
        this.configAdapter.set("Challenge.Enabled", this.enabled)
        this.onSave()
        this.configAdapter.save()
    }

    open fun onEnable() {}

    open fun onDisable() {}

    open fun onReset() {}

    open fun onSave() {}

    fun onTimerStart() {}

    fun onTimerTick() {}

    fun onTimerStop() {}

    val itemStack = itemStack(material) {
        this.editMeta {
            it.displayName(cmp("§8» $displayName"))
            it.lore(description)
        }
    }
}