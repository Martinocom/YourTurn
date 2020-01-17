package it.pabich.yourturn.db.dbmodel

import com.otaliastudios.firestore.FirestoreClass
import com.otaliastudios.firestore.FirestoreDocument
import com.otaliastudios.firestore.FirestoreList

@FirestoreClass
class DBUser : FirestoreDocument() {
    var displayName: String by this
    var mail: String by this
    var activities: Activities by this
    var memberOf: MemberOf by this

    @FirestoreClass
    class Activities : FirestoreList<String>()

    @FirestoreClass
    class MemberOf : FirestoreList<String>()
}