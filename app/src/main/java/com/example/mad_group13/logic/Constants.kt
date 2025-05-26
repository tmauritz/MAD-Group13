package com.example.mad_group13.logic

object Constants {
    const val UPDATE_INTERVAL_MS: Long = 20000 //TODO: change to 1h = 1000(ms) * 60(min) * 60(sec) for production
    const val PET_HUNGER_LOSS_PER_INTERVAL: Float = .05f
    const val PET_ACTIVITY_LOSS_PER_INTERVAL: Float = .05f
    const val PET_HEALTH_LOSS_PER_INTERVAL: Float = .1f
    const val ONE_DAY: Int = 86400
    const val PET_MATURITY_INTERVAL: Int = 20 //TODO: change to 1 day = 86400 for production
    const val PET_MATURITY_TEEN: Int = 1 * PET_MATURITY_INTERVAL
    const val PET_MATURITY_ADULT: Int = 2 * PET_MATURITY_INTERVAL
    const val PET_MATURITY_DEATH: Int = 7 * PET_MATURITY_INTERVAL
}