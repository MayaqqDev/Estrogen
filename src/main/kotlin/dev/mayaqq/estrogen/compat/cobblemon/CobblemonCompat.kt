package dev.mayaqq.estrogen.compat.cobblemon

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Gender
import net.minecraft.world.entity.Entity

object CobblemonCompat {
    fun toFemale(entity: Entity) {
        if (entity is PokemonEntity) {
            entity.pokemon.gender = Gender.FEMALE
        }
    }

    fun changeGender(entity: Entity): Boolean {
        if (entity is PokemonEntity) {
            when (entity.pokemon.gender) {
                Gender.MALE -> {
                    entity.pokemon.gender = Gender.FEMALE
                    return true
                }

                Gender.FEMALE -> {
                    entity.pokemon.gender = Gender.MALE
                    return true
                }

                else -> {
                    return false
                }
            }
        }
        return false
    }
}