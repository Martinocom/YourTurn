package it.pabich.yourturn.db

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.otaliastudios.firestore.FirestoreList
import com.otaliastudios.firestore.toFirestoreDocument
import it.pabich.yourturn.db.dbmodel.DBActivity
import it.pabich.yourturn.db.dbmodel.DBGroup
import it.pabich.yourturn.model.MyActivity
import it.pabich.yourturn.model.User
import kotlin.concurrent.thread

class DataBase private constructor() {

    companion object {
        fun getActivitiesOfGroup(groupID: String, onResult: (FirestoreList<String>) -> Unit, onError: (Errors, String?) -> Unit) {

            thread {
                if (checkConnection(onError)) {
                    // Chache Firebase user and DB
                    val user = FirebaseAuth.getInstance().currentUser
                    val db = FirebaseFirestore.getInstance()

                    // Check if user get part of this specific requested group
                    db.collection(DBCollections.GROUPS.path).document(groupID).get()
                        .addOnSuccessListener {
                            if (it.exists()) {
                                // Check memberlist
                                val dbGroup: DBGroup = it.toFirestoreDocument()

                                if (dbGroup.members.contains(user!!.uid)) {
                                    onResult(dbGroup.activities)
                                } else {
                                    // I'm not in this group :(
                                    onError(Errors.USER_NOT_AUTHORIZED, null)
                                }
                            } else {
                                onError(Errors.NOT_FOUND, null)
                            }
                        }
                        .addOnFailureListener {
                            // Failed to fetch data
                            onError(Errors.GENERIC_ERROR, it.message)
                        }
                }
            }
        }

        fun getActivityDetailsFromID(activityID: String, onResult: (DBActivity) -> Unit, onError: (Errors, String?) -> Unit) {
            thread {
                if (checkConnection(onError)) {
                    // Chache Firebase user and DB
                    FirebaseFirestore.getInstance().collection(DBCollections.ACTIVITIES.path).document(activityID).get()
                        .addOnSuccessListener {
                            if (it.exists()) {
                                onResult(it.toFirestoreDocument())
                            } else {
                                onError(Errors.NOT_FOUND, null)
                            }
                        }
                        .addOnFailureListener {
                            onError(Errors.GENERIC_ERROR, it.message)
                        }
                }
            }
        }

        fun registerUser(user: FirebaseUser?, onSuccess: () -> Unit, onError: (Errors, String?) -> Unit) {
            thread {
                if (checkConnection(onError)) {
                    val dbUser = User(user!!.email!!, user.displayName!!)

                    FirebaseFirestore.getInstance().collection(DBCollections.USERS.path).document(user.uid).set(dbUser)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(Errors.GENERIC_ERROR, it.message) }
                }
            }
        }

        fun checkIfUserIsRegistered(user: FirebaseUser?, onResult: (Boolean) -> Unit, onError: (Errors, String?) -> Unit) {
            thread {
                if (checkConnection(onError)) {
                    if (FirebaseAuth.getInstance().currentUser == user) {
                        FirebaseFirestore.getInstance().collection((DBCollections.USERS.path)).document(user!!.uid).get()
                            .addOnSuccessListener { onResult(it.exists()) }
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
                    FirebaseFirestore.getInstance().collection(DBCollections.USERS.path).document(userID).get()
                        .addOnSuccessListener {
                            if (it.exists()) {
                                onResult(User("Utente@esempio", "Utente Esempio"))
                            }
                            else {
                                onError(Errors.NOT_FOUND, null)
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