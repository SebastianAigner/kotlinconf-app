package org.jetbrains.kotlinconf.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO as kxIO

actual val Dispatchers.App: CoroutineDispatcher
    get() = Dispatchers.App

actual val Dispatchers.IO: CoroutineDispatcher
    get() = kxIO
