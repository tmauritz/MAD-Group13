package com.example.mad_group13.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mad_group13.data.TeslaStockRepository
import com.example.mad_group13.model.RetrofitInstance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TeslaAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var teslaStockRepository: TeslaStockRepository

    override fun onReceive(context: Context, intent: Intent) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("TeslaAlarmReceiver", "Running daily Tesla check")
            teslaStockRepository.fetchAndSaveTeslaPriceOnly()
        }
    }
}