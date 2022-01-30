package fr.lewon.dofus.bot.core.model.spell

enum class DofusSpellEffectType(val id: Int, val globalType: DofusSpellEffectGlobalType) {
    WATER_DAMAGE(96, DofusSpellEffectGlobalType.ATTACK),
    EARTH_DAMAGE(97, DofusSpellEffectGlobalType.ATTACK),
    AIR_DAMAGE(98, DofusSpellEffectGlobalType.ATTACK),
    FIRE_DAMAGE(99, DofusSpellEffectGlobalType.ATTACK),
    NEUTRAL_DAMAGE(100, DofusSpellEffectGlobalType.ATTACK),
    WATER_LIFE_STEAL(91, DofusSpellEffectGlobalType.ATTACK),
    EARTH_LIFE_STEAL(92, DofusSpellEffectGlobalType.ATTACK),
    AIR_LIFE_STEAL(93, DofusSpellEffectGlobalType.ATTACK),
    FIRE_LIFE_STEAL(94, DofusSpellEffectGlobalType.ATTACK),
    NEUTRAL_LIFE_STEAL(95, DofusSpellEffectGlobalType.ATTACK),
    TELEPORT(4, DofusSpellEffectGlobalType.MOVE),
    PUSH(5, DofusSpellEffectGlobalType.MOVE),
    PULL(6, DofusSpellEffectGlobalType.MOVE),
    SWITCH_POSITIONS(8, DofusSpellEffectGlobalType.MOVE),
    MP_BUFF(128, DofusSpellEffectGlobalType.BUFF),
    DASH(1042, DofusSpellEffectGlobalType.MOVE);

    companion object {
        fun fromEffectId(id: Int): DofusSpellEffectType? {
            return values().firstOrNull { it.id == id }
        }
    }
}