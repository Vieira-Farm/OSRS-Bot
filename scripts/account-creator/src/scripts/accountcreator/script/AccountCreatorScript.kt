package scripts.accountcreator.script

import org.tribot.api.General
import org.tribot.script.ScriptManifest
import org.tribot.script.interfaces.Painting
import org.tribot.script.interfaces.Starting
import scripts.data.accounts.AccountCreationDetails
import scripts.data.accounts.AccountDetails
import scripts.data.network.ConnectionSettings
import scripts.accountcreator.missions.accountcreator.AccountCreator
import scripts.scripting.frameworks.mission.missiontypes.Mission
import scripts.scripting.frameworks.mission.missiontypes.Mission.EarlyEndMission
import scripts.scripting.frameworks.mission.scriptTypes.MissionScript
import scripts.scripting.painting.scriptpaint.ScriptPaint
import scripts.web.accountCreation.captcha.CaptchaSolver

@ScriptManifest(
    authors = ["Letsmakemoneybitch"],
    category = "Vieira's Bot Farm",
    name = "[StartUp] - Account Creator",
    version = 1.0,
    description = "Creates accounts on OSRS website.",
    gameMode = 1
)
class AccountCreatorScript(
    private val accountEmail: String,
    private val accountPassword: String,
    private val proxyIP: String,
    private val proxyPort: String,
    private val proxyUsername: String,
    private val proxyPassword: String,
    private val captchaSolverCompany: String,
    private val captchaKey: String
) : MissionScript(), Starting, Painting {

    private var accountDetails: AccountDetails? = null
    private var connectionSettings: ConnectionSettings? = null
    private var captchaSolver: CaptchaSolver? = null

    override fun onStart() {
        this.loginBotState = false
    }

    override fun preScriptTasks() {
        setScriptPaint(
            ScriptPaint.Builder(
                ScriptPaint.hex2Rgb("#ffb140"), "Account Creator"
            ).addField("Version", 1.00.toString())
                .build()
        )
    }

    override fun getMission(): Mission {
        General.println("AccountCreatorScript: preScriptTasks")

        captchaSolver = CaptchaSolver.getCaptchaSolver(
            captchaSolverCompany,
            captchaKey
        )
        connectionSettings = ConnectionSettings(
            proxyIP,
            proxyPort.toInt(),
            proxyUsername,
            proxyPassword
        )
        accountDetails = AccountDetails(
            accountEmail,
            accountPassword,
            AccountCreationDetails.randomFactory(1)
        )
        return if (connectionSettings == null ||
            captchaSolver == null
        ) {
            EarlyEndMission("An invalid set of arguments were passed, please consult the script description to find valid arguments")
        } else AccountCreator(accountDetails, connectionSettings, captchaSolver)
    }
}