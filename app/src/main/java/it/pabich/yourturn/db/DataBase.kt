package it.pabich.yourturn.db

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import it.pabich.yourturn.model.Group
import it.pabich.yourturn.model.MyActivity
import it.pabich.yourturn.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.concurrent.thread

class DataBase private constructor() {

    companion object {

        suspend fun getActivitiesOfGroup(groupID: String): List<Map<String, Any>>? {
            val group = FirebaseFirestore.getInstance()
                .collection(DBNames.Collections.GROUPS.path).document(groupID)
                .get().await().toObject(Group::class.java)

            return if (group != null) {
                if (group.members.any { groupMember -> groupMember.containsValue(FirebaseAuth.getInstance().currentUser!!.uid) }) {
                    group.activities
                } else {
                    null
                }
            } else {
                null
            }
        }

        suspend fun getUserData(userID: String): User? {
            return FirebaseFirestore.getInstance()
                .collection(DBNames.Collections.USERS.path).document(userID)
                .get().await().toObject(User::class.java)
        }



        suspend fun checkIfUserHasAllData(user: FirebaseUser?) : User.UserStatus {
            return when(val userStatus = checkConnection()) {
                User.UserStatus.AUTHENTICATED -> {
                    val dbUser = FirebaseFirestore.getInstance()
                        .collection(DBNames.Collections.USERS.path).document(user!!.uid)
                        .get().await().toObject(User::class.java)

                    if (dbUser != null)
                        User.UserStatus.FULLY_REGISTERED
                    else
                        userStatus
                }
                else -> userStatus
            }
        }


        fun checkConnection() : User.UserStatus {
            return if (FirebaseAuth.getInstance().currentUser != null) {
                if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                    User.UserStatus.AUTHENTICATED
                } else {
                    User.UserStatus.MAIL_NOT_VERIFIED
                }
            } else {
                User.UserStatus.NOT_LOGGED_IN
            }
        }

        fun getActivitiesOfGroup(groupID: String, onResult: (List<Map<String, Any>>) -> Unit, onError: (Errors, String?) -> Unit) {
            GlobalScope.launch {
                if (checkConnection(onError)) {
                    try {
                        val group = FirebaseFirestore.getInstance()
                            .collection(DBNames.Collections.GROUPS.path).document(groupID)
                            .get().await()
                            .toObject(Group::class.java)

                        if (group != null) {
                            if (group.members.any { groupMember ->
                                    groupMember.containsValue(
                                        FirebaseAuth.getInstance().currentUser!!.uid
                                    )
                                }) {
                                onResult(group.activities)
                            } else {
                                // Not in this group
                                onError(Errors.USER_NOT_AUTHORIZED, "User is not in this group")
                            }
                        }
                    } catch (exception: Exception) {
                        onError(Errors.GENERIC_ERROR, exception.message)
                    }
                }
            }
        }

        fun getActivityDetailsFromID(activityID: String, onResult: (MyActivity) -> Unit, onError: (Errors, String?) -> Unit) {
            GlobalScope.launch {
                if (checkConnection(onError)) {
                    try {
                        val activity = FirebaseFirestore.getInstance()
                            .collection(DBNames.Collections.ACTIVITIES.path).document(activityID)
                            .get().await()
                            .toObject(MyActivity::class.java)

                        if (activity != null) {
                            onResult(activity)
                        } else {
                            onError(Errors.NOT_FOUND, "Activity not found")
                        }
                    }
                    catch (exception: Exception) {
                        onError(Errors.GENERIC_ERROR, exception.message)
                    }

                }
                // Connection check finish
            }
        }



        fun registerUser(user: FirebaseUser?, onSuccess: () -> Unit, onError: (Errors, String?) -> Unit) {
            thread {
                if (checkConnection(onError)) {
                    val dbUser = User()
                    dbUser.displayName = user!!.displayName!!
                    dbUser.mail = user.email!!

                    FirebaseFirestore.getInstance().collection(DBNames.Collections.USERS.path).document(user.uid).set(dbUser)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(Errors.GENERIC_ERROR, it.message) }
                }
            }
        }

        fun createGroup(group: Group, onSuccess: (DocumentReference) -> Unit, onError: (Errors, String?) -> Unit) {
            GlobalScope.launch {
                if (checkConnection(onError)) {
                    FirebaseFirestore.getInstance().collection(DBNames.Collections.GROUPS.path).add(group)
                        .addOnSuccessListener { documentReference ->  onSuccess(documentReference) }
                        .addOnFailureListener { onError(Errors.GENERIC_ERROR, it.message) }
                }
            }
        }

        fun deleteGroup(groupDocRef: String, onSuccess: () -> Unit, onError: (Errors, String?) -> Unit) {
            GlobalScope.launch {
                if (checkConnection(onError)) {
                    FirebaseFirestore.getInstance().collection(DBNames.Collections.GROUPS.path).document(groupDocRef).delete()
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(Errors.GENERIC_ERROR, it.message) }
                }
            }
        }

        fun addGroupToUser(groupRef: Map<String, Any>, userRef: String, onSuccess: () -> Unit, onError: (Errors, String?) -> Unit) {
            GlobalScope.launch {
                if (checkConnection(onError)) {
                    FirebaseFirestore.getInstance()
                        .collection(DBNames.Collections.USERS.path)
                        .document(userRef)
                        .update(DBNames.UserData.MEMBER_OF.path, FieldValue.arrayUnion(groupRef))
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(Errors.GENERIC_ERROR, it.message) }
                }
            }
        }

        fun setUserHomePref(userRef: String, newProperties: Map<String, Any>, onSuccess: () -> Unit, onError: (Errors, String?) -> Unit) {
            GlobalScope.launch {
                if (checkConnection(onError)) {
                    FirebaseFirestore.getInstance()
                        .collection(DBNames.Collections.USERS.path)
                        .document(userRef)
                        .update(DBNames.UserData.HOME_PREFS.path, newProperties)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(Errors.GENERIC_ERROR, it.message) }
                }
            }
        }

        fun checkIfUserIsRegistered(user: FirebaseUser?, onResult: (Boolean) -> Unit, onError: (Errors, String?) -> Unit) {
            thread {
                if (checkConnection(onError)) {
                    if (FirebaseAuth.getInstance().currentUser == user) {
                        FirebaseFirestore.getInstance().collection((DBNames.Collections.USERS.path)).document(user!!.uid).get()
                            .addOnSuccessListener { onResult(it?.exists() ?: false) }
                            .addOnFailureListener { onError(Errors.NOT_FOUND, null) }
                    }
                    else {
                        onError(Errors.USER_NOT_AUTHORIZED, "Request came from another user")
                    }
                }
            }
        }

        fun getUserData(userID: String, onResult: (User) -> Unit, onError: (Errors, String?) -> Unit) {
            thread {
                if (checkConnection(onError)) {
                    FirebaseFirestore.getInstance().collection(DBNames.Collections.USERS.path).document(userID).get()
                        .addOnSuccessListener {
                            try {
                                val user = it.toObject(User::class.java)
                                if (user != null) {
                                    onResult(user)
                                } else {
                                    onError(Errors.NOT_FOUND, null)
                                }
                            } catch (exception: Exception) {
                                onError(Errors.GENERIC_ERROR, exception.message)
                            }
                        }
                        .addOnFailureListener {
                            onError(Errors.GENERIC_ERROR, it.message)
                        }
                }
            }
        }

        private fun checkConnection(onError: (Errors, String?) -> Unit) : Boolean {
            if (FirebaseAuth.getInstance().currentUser != null) {
                return if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                    true
                } else {
                    onError(Errors.USER_NOT_VERIFIED, null)
                    false
                }
            }

            onError(Errors.USER_NOT_LOGGED_IN, null)
            return false
        }
    }


}