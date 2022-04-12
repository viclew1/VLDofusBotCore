package fr.lewon.dofus.bot.core.model.spell

data class DofusSpell(
    val id: Int = -1,
    val iconId: Int = -1,
    val name: String = "CUSTOM_SPELL",
    val levels: List<DofusSpellLevel> = listOf(DofusSpellLevel())
)