package com.barisguneri.aiartcreator.model

data class RequestModel(
    val prompt: String,
    val guidance: Int,
    val steps: Int,
    val sampler: String,
    val width: Int,
    val upscale: Int,
    val c: String,
    val height: Int,
    val model: String,
)
