package fr.lewon.dofus.bot.core.model.spell

data class DofusEffectZone(
    var effectZoneType: DofusEffectZoneType = DofusEffectZoneType.POINT,
    var size: Int = 1
)