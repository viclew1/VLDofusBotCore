package fr.lewon.dofus.bot.core.model.spell

enum class DofusSpellTarget(val targetMask: String) {
    ALLIES_ONLY("a"),
    ENEMIES_ONLY("A"),
    EVERYBODY("a,A");

    companion object {
        fun fromString(targetMask: String): DofusSpellTarget? {
            return values().firstOrNull { it.targetMask == targetMask }
        }
    }
}