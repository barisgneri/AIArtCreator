package com.barisguneri.aiartcreator.model

data class Favorites(
    val id: Int,
    val name: String,
    val image: ByteArray,
    var isFavorite: Boolean,
    ):java.io.Serializable