package com.example.data.mapper

import android.content.Context
import com.example.data.R
import com.example.data.database.model_db.PokemonDetailsModelDb
import com.example.domain.models.mapper.MapperInterface
import com.example.domain.models.model_vo.LowLevelItem
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.domain.models.model_vo.TopLevelItem
import javax.inject.Inject

class PokemonDetailsDbToDetailsVoMapper @Inject constructor(private val context: Context) :
    MapperInterface<PokemonDetailsModelVo, PokemonDetailsModelDb> {

    override fun toOutObject(inObject: PokemonDetailsModelDb): PokemonDetailsModelVo {
        val itemDetailsMap = with(inObject) {
            hashMapOf(
                TopLevelItem(context.getString(R.string.abilities)) to abilities.map { LowLevelItem(detailName = it) },
                TopLevelItem( context.getString(R.string.forms)) to forms.map { LowLevelItem(detailName = it) },
                TopLevelItem( context.getString(R.string.stats)) to stats.map { LowLevelItem(detailName = it) }
            )
        }
        return with(inObject) {
            PokemonDetailsModelVo(
                name = pokemonName,
                avatarUrl = pokemonAvatarUrl,
                weight = weight,
                height = height,
                detailsRecyclerViewMap = itemDetailsMap
            )
        }
    }
}