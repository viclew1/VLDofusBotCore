package fr.lewon.dofus.bot.core.model.spell

enum class DofusSpellTarget {
    ALLIES_ONLY,
    ENEMIES_ONLY,
    EVERYBODY;

    companion object {
        fun fromString(targetMask: String): DofusSpellTarget? {
            val targets = targetMask.split(",")
            return if (targets.contains("a") && targets.contains("A") || targets.contains("L") && targets.contains("M")) {
                EVERYBODY
            } else if (targets.contains("a")) {
                ALLIES_ONLY
            } else if (targets.contains("A")) {
                ENEMIES_ONLY
            } else {
                null
            }
        }
    }
}