package com.example.bankcleancodetest.interactor

import com.example.bankcleancodetest.Constants.Companion.BASE_STATMENT_URL
import com.example.bankcleancodetest.MainContract
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class MainInteractor: MainContract.Interactor {
    override fun loadInvoiceExtract(
        userId: Int,
        interactorMainOutput: (result: Result<Json, FuelError>) -> Unit
    ) {
        "$BASE_STATMENT_URL$userId"
            .httpGet()
            .responseJson { _, _, result ->
                interactorMainOutput(result)
            }
    }
}