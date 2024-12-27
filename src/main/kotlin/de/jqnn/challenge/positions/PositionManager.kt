package de.jqnn.challenge.positions

import de.jqnn.challenge.ChallengeSystem
import de.jqnn.challenge.extensions.cmp
import net.kyori.adventure.text.BlockNBTComponent.Pos
import org.bukkit.Location

/********************************************************************************
 *    Urheberrechtshinweis                                                      *
 *    Copyright © Jan Scherping 2024                                            *
 *    Erstellt: 27.12.2024 / 02:28                                              *
 *                                                                              *
 *    Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.          *
 *    Das Urheberrecht liegt, soweit nicht ausdrücklich ander gekennzeichnet,   *
 *    bei Jan Scherping. Alle Rechte vorbehalten.                               *
 *                                                                              *
 *    Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,       *
 *    öffentlichen Zugänglichmachung oder andere Nutzung                        *
 *    bedarf der ausdrücklichen, schriftlichen Zustimmung von Jan Scherping.    *
 ********************************************************************************/

class PositionManager {

    private val challengeSystem = ChallengeSystem.challengeSystem
    private val configAdapter = this.challengeSystem.configAdapter
    private val gson = this.configAdapter.gson

    val prefix = cmp("§8§l[§5Positionen§8§l] §r")
    val positions = mutableListOf<Position>()

    init {
        if (this.configAdapter.existsKey("Positions")) {
            (this.configAdapter.getList("Positions")).forEach {
                this.positions.add(gson.fromJson(it.toString(), Position::class.java))
            }
        }
    }

    fun createLocation(name: String, location: Location) {
        this.positions.add(Position(name, location.world.name, location.blockX, location.blockY, location.blockZ))
    }

    fun getPosition(name: String) = this.positions.firstOrNull { it.name.equals(name, true) }

    fun savePositions() {
        this.configAdapter.set("Positions", positions)
    }

}