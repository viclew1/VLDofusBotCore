package fr.lewon.dofus.bot.core.model.spell

import fr.lewon.dofus.bot.core.fighter.IDofusFighter
import fr.lewon.dofus.bot.core.fighter.PlayerType

enum class DofusSpellTargetType(
    private val key: Char,
    private val isTargetValidChecker: (Int?, IDofusFighter, IDofusFighter) -> Boolean
) {
    ALLIES('a', { _, caster, target -> caster.getFighterTeamId() == target.getFighterTeamId() }),
    ENEMIES('A', { _, caster, target -> caster.getFighterTeamId() != target.getFighterTeamId() }),
    CASTER('C', { _, caster, target -> caster.getFighterTeamId() == target.getFighterTeamId() }),
    ALLIED_SUMMON('F', { id, _, target -> target.getPlayerType() != PlayerType.HUMAN && target.getBreed() == id }),
    SWITCHABLE_ENEMIES('i', { _, caster, target ->
        caster.getFighterTeamId() != target.getFighterTeamId()
                && target.getPlayerType() != PlayerType.SIDEKICK
                && target.isSummon()
                && !target.isStaticElement()
    }),
    PLAYER_SUMMON('P', { _, caster, target ->
        target.getFighterId() == caster.getFighterId()
                || target.isSummon() && target.getSummonerId() == caster.getFighterId()
                || !target.isSummon() && caster.getSummonerId() == target.getSummonerId()
                || !caster.isSummon() && caster.getSummonerId() == target.getFighterId()
    }),
    WITHOUT_STATE('e', { id, _, target -> id != null && !target.hasState(id) }),
    WITH_STATE('E', { id, _, target -> id != null && target.hasState(id) }),
    ;

    companion object {
        fun fromString(targetChar: Char): DofusSpellTargetType? {
            return values().firstOrNull { it.key == targetChar }
        }
    }

    fun canHitTarget(id: Int?, caster: IDofusFighter, target: IDofusFighter): Boolean {
        return isTargetValidChecker(id, caster, target)
    }

}