package com.github.gunin_igor75.task_list.data.exception

import java.lang.RuntimeException

class PurchaseNotFoundException: RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
}