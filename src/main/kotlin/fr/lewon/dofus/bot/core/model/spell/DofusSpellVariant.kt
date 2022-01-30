package fr.lewon.dofus.bot.core.model.spell

import fr.lewon.dofus.bot.core.model.charac.DofusBreed

data class DofusSpellVariant(
    val id: Int,
    val spells: List<DofusSpell>,
    val breed: DofusBreed
)