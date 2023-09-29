package com.github.gunin_igor75.task_list.domain.pojo

data class Purchase(
    val name: String,
    var count: Int,
    var enabled: Boolean,
    var id: Int = NOTING_VALUE
) {
    companion object{
        const val NOTING_VALUE = -1
    }
}

