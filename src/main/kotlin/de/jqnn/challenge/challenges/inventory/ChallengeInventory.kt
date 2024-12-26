package de.jqnn.challenge.challenges.inventory

import de.jqnn.challenge.challenges.Challenge
import de.jqnn.challenge.challenges.ChallengeManager
import de.jqnn.challenge.extensions.cmp
import de.jqnn.challenge.extensions.skullItem
import net.axay.kspigot.items.itemStack
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class ChallengeInventory(private val challengeManager: ChallengeManager) {

    private val challenges = mutableListOf<Challenge>()
    private val pages = mutableMapOf<Int, Inventory>()
    private val inventoryItems = mutableMapOf<String, ItemStack>()

    init {
        challenges.addAll(this.challengeManager.challenges)
        this.inventoryItems["main-menu"] =
            skullItem("f84f597131bbe25dc058af888cb29831f79599bc67c95c802925ce4afba332fc", "§8» §cZurück")
        this.inventoryItems["close"] =
            skullItem("beb588b21a6f98ad1ff4e085c552dcb050efc9cab427f46048f18fc803475f7", "§8» §cSchließen")
        this.inventoryItems["next"] =
            skullItem("4ef356ad2aa7b1678aecb88290e5fa5a3427e5e456ff42fb515690c67517b8", "§8» §aNächste Seite")
        this.inventoryItems["back"] =
            skullItem("f84f597131bbe25dc058af888cb29831f79599bc67c95c802925ce4afba332fc", "§8» §cLetzte Seite")

        while (this.challenges.isNotEmpty())
            this.createPage()
    }

    private fun createPage() {
        if (this.challenges.isEmpty()) return
        val page = (pages.size + 1)

        val inventory = Bukkit.createInventory(null, 4 * 9, cmp("§8» §6Challenges §8┃ §7$page"))
        this.fillInventory(
            inventory,
            arrayOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25)
        )

        this.challenges.take(14).forEach {
            if (inventory.getItem(25) != null) return@forEach
            if (it.enabled)
                inventory.addItem(it.itemStack.apply { this.editMeta { meta -> meta.setEnchantmentGlintOverride(true) } })
            else
                inventory.addItem(it.itemStack)
            this.challenges.remove(it)
        }

        if (this.challenges.isNotEmpty()) inventory.setItem(35, inventoryItems["next"])
        if (page != 1) inventory.setItem(27, inventoryItems["back"])
        pages[page] = inventory
        inventory.setItem(0, inventoryItems["main-menu"])
    }

    fun openPage(player: Player, page: Int) {
        player.openInventory(pages[page] ?: return)
    }

    private fun fillInventory(inventory: Inventory, slots: Array<Int>) {
        val itemStack = itemStack(Material.BLACK_STAINED_GLASS_PANE) {
            this.editMeta { it.displayName(cmp("")) }
        }

        for (i in 0..<inventory.size) {
            if (slots.contains(i)) continue
            inventory.setItem(i, itemStack)
        }
    }

}