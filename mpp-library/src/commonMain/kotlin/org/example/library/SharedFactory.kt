/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.russhwolf.settings.Settings
import dev.icerock.moko.crashreporting.crashlytics.CrashlyticsLogger
import dev.icerock.moko.crashreporting.napier.CrashReportingAntilog
import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.network.errors.NetworkErorrsTexts
import dev.icerock.moko.network.errors.registerAllNetworkMappers
import dev.icerock.moko.network.exceptionfactory.HttpExceptionFactory
import dev.icerock.moko.network.exceptionfactory.parser.ErrorExceptionParser
import dev.icerock.moko.network.exceptionfactory.parser.ValidationExceptionParser
import dev.icerock.moko.network.exceptions.ErrorException
import dev.icerock.moko.network.exceptions.ValidationException
import dev.icerock.moko.network.features.ExceptionFeature
import dev.icerock.moko.network.features.TokenFeature
import dev.icerock.moko.network.generated.apis.NewsApi
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import org.example.library.feature.auth.di.AuthFactory

class SharedFactory(
    settings: Settings,
    antilog: Antilog,
    baseUrl: String,
    httpClientEngine: HttpClientEngine?
) {
    // special for iOS call side we not use argument with default value
    constructor(
        settings: Settings,
        antilog: Antilog,
        baseUrl: String,
    ) : this(
        settings = settings,
        antilog = antilog,
        baseUrl = baseUrl,
        httpClientEngine = null
    )

    private val keyValueStorage: KeyValueStorage by lazy { KeyValueStorage(settings) }

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    private val httpClient: HttpClient by lazy {
        // resolve class properties into local variables to pass them into freeze lambda
        val json: Json = json
        val keyValueStorage: KeyValueStorage = keyValueStorage

        val config: HttpClientConfig<*>.() -> Unit = {
            install(ExceptionFeature) {
                exceptionFactory = HttpExceptionFactory(
                    defaultParser = ErrorExceptionParser(json),
                    customParsers = mapOf(
                        HttpStatusCode.UnprocessableEntity.value to ValidationExceptionParser(json)
                    )
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message = message)
                    }
                }
                level = LogLevel.HEADERS
            }
            install(TokenFeature) {
                tokenHeaderName = "Authorization"
                tokenProvider = object : TokenFeature.TokenProvider {
                    override fun getToken(): String? = keyValueStorage.token
                }
            }

            // disable standard BadResponseStatus - exceptionfactory do it for us
            expectSuccess = false
        }

        if (httpClientEngine != null) HttpClient(httpClientEngine, config)
        else HttpClient(config)
    }

    private val newsApi: NewsApi by lazy {
        NewsApi(
            basePath = baseUrl,
            httpClient = httpClient,
            json = json
        )
    }

    // init factories here
    val authFactory: AuthFactory by lazy {
        AuthFactory()
    }

    init {
        Napier.base(antilog)
        Napier.base(CrashReportingAntilog(CrashlyticsLogger()))

        ExceptionMappersStorage
            .registerAllNetworkMappers(errorsTexts = NetworkErorrsTexts())
            .register<ErrorException, StringDesc> {
                it.description?.desc() ?: MR.strings.unknown_error.desc()
            }
            .register<ValidationException, StringDesc> {
                it.errors.firstOrNull()?.message?.desc()
                    ?: MR.strings.unknown_error.desc()
            }
    }
}
