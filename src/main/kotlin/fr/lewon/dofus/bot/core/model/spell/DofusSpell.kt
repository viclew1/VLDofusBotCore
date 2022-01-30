package fr.lewon.dofus.bot.core.model.spell

data class DofusSpell(
    val id: Int,
    val name: String,
    val levels: List<DofusSpellLevel>
)