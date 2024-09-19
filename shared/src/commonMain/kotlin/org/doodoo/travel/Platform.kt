package org.doodoo.travel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform