package fr.lewon.dofus.bot.core.model.move

import fr.lewon.dofus.bot.core.model.maps.DofusMap

data class Move(val direction: Direction, val fromMap: DofusMap, val toMap: DofusMap)