package com.example.data.mapper

import android.content.Context
import com.example.data.R
import com.example.domain.models.mapper.MapperInterface
import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.LowLevelItem
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.domain.models.model_vo.TopLevelItem

class PokemonDetailsDtoToDetailsVoMapper constructor(private val context: Context):
    MapperInterface<PokemonDetailsModelVo, PokemonDto> {

    override fun toOutObject(inObject: PokemonDto): PokemonDetailsModelVo {
        val itemDetailsMap = with(inObject) {
            hashMapOf(
                TopLevelItem(context.getString(R.string.abilities)) to abilities.map { LowLevelItem(detailName = it.ability.name) },
                TopLevelItem( context.getString(R.string.forms)) to forms.map { LowLevelItem(detailName = it.name) },
                TopLevelItem( context.getString(R.string.stats)) to stats.map { LowLevelItem(detailName = "${it.stat.name} = ${it.baseStat}") }
            )
        }

        return with(inObject) {
            PokemonDetailsModelVo(
                name = name,
                avatarUrl = sprites.frontDefaultUrl,
                weight = weight,
                height = height,
                detailsRecyclerViewMap = itemDetailsMap
            )
        }
    }
}