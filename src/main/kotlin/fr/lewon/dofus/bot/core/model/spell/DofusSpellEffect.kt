package fr.lewon.dofus.bot.core.model.spell

data class DofusSpellEffect(
    var min: Int = 0,
    var max: Int = 0,
    var rawZone: DofusEffectZone = DofusEffectZone(DofusEffectZoneType.POINT, 1),
    var effectType: DofusSpellEffectType = DofusSpellEffectType.TELEPORT,
    var target: DofusSpellTarget = DofusSpellTarget.EVERYBODY
)