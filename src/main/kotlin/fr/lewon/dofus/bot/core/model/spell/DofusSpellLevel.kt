package fr.lewon.dofus.bot.core.model.spell

data class DofusSpellLevel(
    var id: Int = 0,
    var spellId: Int = 0,
    var criticalHitProbability: Int = 0,
    var needFreeCell: Boolean = false,
    var needTakenCell: Boolean = false,
    var maxRange: Int = 0,
    var minRange: Int = 0,
    var castInLine: Boolean = false,
    var rangeCanBeBoosted: Boolean = false,
    var apCost: Int = 0,
    var castInDiagonal: Boolean = false,
    var initialCooldown: Int = 0,
    var castTestLos: Boolean = false,
    var minCastInterval: Int = 0,
    var maxStack: Int = 0,
    var grade: Int = 0,
    var minPlayerLevel: Int = 0,
    var maxCastPerTarget: Int = 0,
    var maxCastPerTurn: Int = 0,
    var forClientOnly: Boolean = false,
    var effects: List<DofusSpellEffect> = listOf(DofusSpellEffect()),
    var criticalEffects: List<DofusSpellEffect> = effects.toList(),
)