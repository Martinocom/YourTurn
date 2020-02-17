package it.pabich.yourturn.model

class User {
    var displayName: String = ""
    var mail: String = ""
    var homePrefs: MutableMap<String, Any> = mutableMapOf()
    var memberOf: List<Map<String, Any>> = emptyList()

    enum class MemberOfKeys(val value: String) {
        GROUP_ID("groupID"),
        GROUP_COLOR("groupColor"),
        GROUP_NAME("groupName")
    }

    enum class HomePrefsKeys(val value: String) {
        IS_ACTIVITIES_FIRST("isActivitiesFirst")
    }

    enum class UserStatus {
        FULLY_REGISTERED, AUTHENTICATED, MAIL_NOT_VERIFIED, UNREGISTERED, NOT_LOGGED_IN
    }
}

