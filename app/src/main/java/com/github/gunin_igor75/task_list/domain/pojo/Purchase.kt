package com.github.gunin_igor75.task_list.domain.pojo

data class Purchase(
    val name: String,
    var count: Int,
    var id: Int = NOTING_VALUE,
    var enabled: Boolean
) {
    companion object{
        const val NOTING_VALUE = -1
    }
}
