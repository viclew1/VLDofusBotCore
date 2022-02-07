package fr.lewon.dofus.bot.core.model.spell

enum class DofusSpellTarget(val targetMask: String) {
    ALLIES_ONLY("a"),
    ENEMIES_ONLY("A"),
    EVERYBODY("a,A");

    companion object {
        fun fromString(targetMask: String): DofusSpellTarget? {
            val targets = targetMask.split(",")
            return if (targets.contains(ALLIES_ONLY.targetMask) && targets.contains(ENEMIES_ONLY.targetMask)) {
                EVERYBODY
            } else if (targets.contains(ALLIES_ONLY.targetMask)) {
                ALLIES_ONLY
            } else if (targets.contains(ENEMIES_ONLY.targetMask)) {
                ENEMIES_ONLY
            } else {
                null
            }
        }
    }
}