package scripts.accountcreator.data

import okhttp3.OkHttpClient
import scripts.data.accounts.AccountDetails
import scripts.data.network.ConnectionSettings
import scripts.data.structures.PrivateSingleton
import scripts.accountcreator.missions.accountcreator.AccountCreator
import scripts.web.RSCookieJar
import scripts.web.accountCreation.captcha.CaptchaSolver

class Variables(
    callingClass: Class<*>?,
    @JvmField val accountDetails: AccountDetails,
    @JvmField val connectionSettings: ConnectionSettings,
    @JvmField val captchaSolver: CaptchaSolver
) : PrivateSingleton(callingClass) {
    var isCreationCompleted: Boolean

    @JvmField
    val httpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        builder.cookieJar(RSCookieJar())
        if (connectionSettings.proxy != null) {
            builder.proxy(connectionSettings.proxy)
                .addInterceptor(connectionSettings.interceptor)
        }
        httpClient = builder.build()
        isCreationCompleted = false
    }

    override fun getAllowedClass(): Class<*> {
        return AccountCreator::class.java
    }
}