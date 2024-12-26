package de.jqnn.challenge.extensions

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import java.net.URL
import java.util.*

fun skullItem(texture: String, displayName: String, itemMetaBuilder: ItemMeta.() -> Unit = {}): ItemStack {
    val url = if(texture.startsWith("http")) texture else "https://textures.minecraft.net/texture/$texture"

    val itemStack = ItemStack(Material.PLAYER_HEAD)
    itemStack.editMeta(SkullMeta::class.java) {
        val profile = Bukkit.createProfile(UUID.randomUUID())
        profile.setTextures(profile.textures.apply { this.skin = URL(url) })

        it.playerProfile = profile
        it.displayName(cmp(displayName))
        it.apply(itemMetaBuilder)
    }
    return itemStack
}