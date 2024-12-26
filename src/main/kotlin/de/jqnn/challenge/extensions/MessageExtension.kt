package de.jqnn.challenge.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.TextDecoration

fun cmp(
    text: String,
    bold: Boolean = false,
    italic: Boolean = false,
    strikethrough: Boolean = false,
    underlined: Boolean = false
): Component {
    return Component.text(text)
        .decorations(
            mapOf(
                TextDecoration.BOLD to TextDecoration.State.byBoolean(bold),
                TextDecoration.ITALIC to TextDecoration.State.byBoolean(italic),
                TextDecoration.STRIKETHROUGH to TextDecoration.State.byBoolean(strikethrough),
                TextDecoration.UNDERLINED to TextDecoration.State.byBoolean(underlined)
            )
        )
}

fun Component.addHover(display: Component): Component {
    return hoverEvent(asHoverEvent().value(display))
}

fun Component.addRunCommand(command: String): Component {
    return clickEvent(ClickEvent.runCommand(command))
}

fun Component.addCopyToClipboard(command: String): Component {
    return clickEvent(ClickEvent.copyToClipboard(command))
}

fun Component.addSuggestCommand(command: String): Component {
    return clickEvent(ClickEvent.suggestCommand(command))
}

operator fun Component.plus(other: Component): Component {
    return append(other)
}

fun Boolean.formatted(formatter: BooleanFormatting = BooleanFormatting.DEFAULT): String {
    return when (this) {
        true -> formatter.toggleTrue
        false -> formatter.toggleFalse
    }
}

enum class BooleanFormatting(val toggleTrue: String, val toggleFalse: String) {

    DEFAULT("§aAn", "§cAus")

}