package de.jqnn.challenge.listener

import de.jqnn.challenge.ChallengeSystem
import de.jqnn.challenge.extensions.cmp
import de.jqnn.challenge.extensions.plus
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.toLegacyString
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

object InventoryClickListener {

    private val challengeSystem = ChallengeSystem.challengeSystem
    private val challengeManager = this.challengeSystem.challengeManager
    private val prefix = this.challengeSystem.prefix

    init {
        listen<InventoryClickEvent> {
            if (it.currentItem == null) return@listen
            if (!it.currentItem!!.hasItemMeta()) return@listen
            if (it.currentItem!!.itemMeta.displayName() == null) return@listen

            val player = it.whoClicked as Player
            val displayName = it.currentItem!!.itemMeta.displayName()!!.toLegacyString()
            val title = it.view.title().toLegacyString()

            if (title.contains("§6Challenges")) {
                it.isCancelled = true

                if (displayName.contains("§cZurück")) {
                    // TODO Open settings inventory
                    player.closeInventory()
                    return@listen
                }

                if (displayName.contains("§aNächste Seite")) {
                    val page = title.split(" §8┃ §7")[1].toInt()
                    this.challengeManager.challengeInventory.openPage(player, page + 1)
                    player.playSound(player.location, Sound.ITEM_BOOK_PAGE_TURN, 2f, 2f)
                    return@listen
                }

                if (displayName.contains("§cLetzte Seite")) {
                    val page = title.split(" §8┃ §7")[1].toInt()
                    this.challengeManager.challengeInventory.openPage(player, page - 1)
                    player.playSound(player.location, Sound.ITEM_BOOK_PAGE_TURN, 2f, 2f)
                    return@listen
                }

                if (it.currentItem!!.type == Material.BLACK_STAINED_GLASS_PANE) return@listen
                val challenge = this.challengeManager.getChallenge(it.currentItem!!) ?: return@listen
                val inventory = it.clickedInventory ?: return@listen

                if (challenge.enabled) challenge.disable() else challenge.enable()
                inventory.setItem(
                    it.slot,
                    challenge.itemStack.apply {
                        if (challenge.enabled)
                            this.editMeta { meta -> meta.setEnchantmentGlintOverride(true) }
                    })

                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                player.sendMessage(this.prefix.plus(cmp("Die Challenge ${challenge.displayName} §rwurde ${if (challenge.enabled) "§aaktiviert" else "§cdeaktiviert"}§8.")))
            }
        }
    }

}