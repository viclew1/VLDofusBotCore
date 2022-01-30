package fr.lewon.dofus.bot.core.model.spell

data class DofusSpellEffect(
    val min: Int,
    val max: Int,
    val rawZone: DofusEffectZone,
    val effectType: DofusSpellEffectType,
    val target: DofusSpellTarget
)