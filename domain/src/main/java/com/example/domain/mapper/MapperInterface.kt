package com.example.domain.mapper

interface MapperInterface<OUT, IN> {

    fun toViewObject(dto: IN): OUT

    fun toViewObject(list: Collection<IN>): List<OUT> {
        val result = ArrayList<OUT>()
        list.mapTo(result) { toViewObject(it) }
        return result
    }
}