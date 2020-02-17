package it.pabich.yourturn.db

class DBNames {

    enum class Collections(val path: String) {
        ACTIVITIES("activities"),
        USERS("users"),
        GROUPS("groups")
    }

    enum class UserData(val path: String) {
        DISPLAY_NAME("displayName"),
        MAIL("maill"),
        MEMBER_OF("memberOf"),
        HOME_PREFS("homePrefs")
    }
}