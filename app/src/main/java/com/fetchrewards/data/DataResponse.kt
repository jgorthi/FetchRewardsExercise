package com.fetchrewards.data

data class DataResponse(
    var id: Int? = 0,
    var listId: Int = 0,
    var name: String? = null
)

fun List<DataResponse>.convertToMap() =  this.filter {
        !it.name.isNullOrEmpty() // Removing the null and empty names here
    }.groupBy {
        it.listId
    }.mapValues {
            entry -> entry.value.map { it.name }
    }.toSortedMap()