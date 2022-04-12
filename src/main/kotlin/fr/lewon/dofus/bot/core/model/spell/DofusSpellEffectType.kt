package fr.lewon.dofus.bot.core.model.spell

enum class DofusSpellEffectType(val globalType: DofusSpellEffectGlobalType, private vararg val ids: Int) {
    WATER_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 96),
    EARTH_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 97),
    AIR_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 98),
    FIRE_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 99),
    NEUTRAL_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 100),
    WATER_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 91),
    EARTH_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 92),
    AIR_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 93),
    FIRE_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 94),
    NEUTRAL_LIFE_STEAL(DofusSpellEffectGlobalType.ATTACK, 95),
    MP_DECREASED_EARTH_DAMAGE(DofusSpellEffectGlobalType.ATTACK, 1016),
    TELEPORT(DofusSpellEffectGlobalType.MOVE, 4),
    PUSH(DofusSpellEffectGlobalType.MOVE, 5),
    PULL(DofusSpellEffectGlobalType.MOVE, 6),
    SWITCH_POSITIONS(DofusSpellEffectGlobalType.MOVE, 8),
    DASH(DofusSpellEffectGlobalType.MOVE, 1042),
    DAMAGE_BUFF(DofusSpellEffectGlobalType.BUFF, 112),
    CRITICAL_BUFF(DofusSpellEffectGlobalType.BUFF, 115),
    MP_BUFF(DofusSpellEffectGlobalType.BUFF, 128),
    POWER_BUFF(DofusSpellEffectGlobalType.BUFF, 138, 1054);

    companion object {
        fun fromEffectId(id: Int): DofusSpellEffectType? {
            return values().firstOrNull { it.ids.contains(id) }
        }
    }
}