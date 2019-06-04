package com.object173.cfteventregestrator.feature.base.presentation.list

data class FilterItem<V> (
    val id : Int,
    val action : (List<V>) -> List<V>
)