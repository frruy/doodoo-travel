package org.doodoo.travel.core.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

internal fun createHttpClient(): HttpClient {
    return HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        engine {
            https {
                // Disable SSL verification
                createInsecureSslContext()
            }
        }
    }
}

fun createInsecureSslContext(): SSLContext {
    val trustAllCertificates = arrayOf<javax.net.ssl.TrustManager>(TrustAllCertificates)
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAllCertificates, java.security.SecureRandom())
    return sslContext
}

object TrustAllCertificates : X509TrustManager {
    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>?, authType: String?) {}
    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>?, authType: String?) {}
    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
}