package fr.lewon.dofus.bot.core.d2o.managers.spell

import fr.lewon.dofus.bot.core.VldbManager
import fr.lewon.dofus.bot.core.d2o.D2OUtil
import fr.lewon.dofus.bot.core.d2o.managers.characteristic.BreedManager
import fr.lewon.dofus.bot.core.model.spell.DofusSpellVariant

object SpellVariantManager : VldbManager {

    private lateinit var variantsByBreedId: HashMap<Int, ArrayList<DofusSpellVariant>>

    override fun initManager() {
        variantsByBreedId = HashMap()
        D2OUtil.getObjects("SpellVariants").forEach {
            val id = it["id"].toString().toInt()
            val breedId = it["breedId"].toString().toInt()
            val spellIds = it["spellIds"] as List<Int>
            val spells = spellIds.map { SpellManager.getSpell(it) }
            val variants = variantsByBreedId.computeIfAbsent(breedId) { ArrayList() }
            variants.add(DofusSpellVariant(id, spells, BreedManager.getBreed(breedId)))
        }
    }

    override fun getNeededManagers(): List<VldbManager> {
        return listOf(BreedManager, SpellManager)
    }

    fun getSpellVariants(breedId: Int): List<DofusSpellVariant> {
        return variantsByBreedId[breedId] ?: error("No variant for breed : $breedId")
    }

}