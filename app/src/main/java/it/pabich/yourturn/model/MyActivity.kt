package it.pabich.yourturn.model

import io.opencensus.common.Timestamp

class MyActivity {
    var name: String = ""
    var partOfGroupID: String = ""
    var partOfGroupName: String = ""
    var enchargedUsers: List<Map<EnchargedUserKeys, Any>> = emptyList()
    var checks: List<Map<ChecksKeys, Any>> = emptyList()
    var nextUserDisplayName: String = ""
    var nextUserID: String = ""
    var previousUserTimestamp: Timestamp = Timestamp.create(0, 0)
    var previousUserDisplayName: String = ""
    var previousUserID: String = ""

    enum class EnchargedUserKeys(name: String) {
        USER_ID("userID"),
        DISPLAY_NAME("displayName")
    }

    enum class ChecksKeys(name: String) {
        USER_ID("userID"),
        USER_DISPLAY_NAME("userDisplayName"),
        SIGNED_BY_ID("signedBy"),
        SIGNED_BY_DISPLAY_NAME("signedByDisplayName"),
        DATE_OF_CREATION("dateOfCreation"),
        DATE_OF_EFFECTIVE_CHECK("dateOfEffectiveCheck"),
        PHOTO("photo")
    }
}

/*
class MyActivity(val title: String, val activityChecks: List<ActivityCheck>, val enchargedUsers: List<EnchargedUser>) {
    val lastCheck: ActivityCheck? = activityChecks.maxBy { it.date }
    private var _cyclesCount = 1
    val cyclesCount = _cyclesCount

    fun getDescriptionForNextEnchargedUser() : NextEnchargedUserReturn {

        if (enchargedUsers.isNotEmpty()) {
            // If nobody never made this activity
            if (lastCheck == null) {
                return NextEnchargedUserReturn(NextEnchargedUserReturnType.USER_FOUND, enchargedUsers.minBy { it.orderNumber })
            }

            // If someone made this activity
            else {
                // Increment the next encharger count and check the size
                val nextEnchargedCount = lastCheck.user.orderNumber + 1

                // Get the first or the next user, based only for orderNumber
                val user = if (nextEnchargedCount > enchargedUsers.size) {
                    _cyclesCount++
                    enchargedUsers.minBy { it.orderNumber }
                } else {
                    enchargedUsers.first { it.checkCounter == nextEnchargedCount }
                }

                // Get all users that have count of checks minor than cyclesCount
                val usersWithMissing = getUsersWithMissingActivities()

                return if (checkIfMissingActivitiesAreOrdered(usersWithMissing)) {
                    // All missing users are ordered, no problems
                    NextEnchargedUserReturn(NextEnchargedUserReturnType.USER_FOUND, user)
                } else {
                    // There is user 2, 4 and 5. User 3 have done the check, but 2 no!
                    NextEnchargedUserReturn(NextEnchargedUserReturnType.MORE_USERS_FOUND)
                }
            }


        } else {
            return NextEnchargedUserReturn(NextEnchargedUserReturnType.LIST_EMPTY)
        }
    }

    fun getUsersWithMissingActivities() : List<EnchargedUser> {
        val listOfMissing = mutableListOf<EnchargedUser>()

        enchargedUsers.forEach {
            if (it.checkCounter < _cyclesCount)
                listOfMissing.add(it)
        }

        return listOfMissing
    }

    fun checkIfMissingActivitiesAreOrdered(enchargedMissing: List<EnchargedUser>) : Boolean {
        val orderedUsers = enchargedMissing.sortedBy { it.orderNumber }

        for (i in 0..orderedUsers.size) {
            if (orderedUsers[i].orderNumber < enchargedMissing.size) {
                if (orderedUsers[i].orderNumber + 1 != orderedUsers[i+1].orderNumber)
                    return false
            }
        }

        return true
    }


    data class NextEnchargedUserReturn(val nextEnchargedUserReturnType: NextEnchargedUserReturnType, val enchargedUser: EnchargedUser? = null)

    enum class NextEnchargedUserReturnType {
        USER_FOUND, LIST_EMPTY, MORE_USERS_FOUND
    }
}*/