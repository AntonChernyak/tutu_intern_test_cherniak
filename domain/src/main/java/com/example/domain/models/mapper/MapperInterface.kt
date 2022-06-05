package com.example.domain.models.mapper

interface MapperInterface<OUT, IN> {

    fun toOutObject(inObject: IN): OUT

    fun toOutObject(list: Collection<IN>): List<OUT> {
        val result = ArrayList<OUT>()
        list.mapTo(result) { toOutObject(it) }
        return result
    }
}