package it.pabich.yourturn.db.dbmodel

import com.otaliastudios.firestore.FirestoreClass
import com.otaliastudios.firestore.FirestoreDocument
import com.otaliastudios.firestore.FirestoreList

@FirestoreClass
class DBGroup : FirestoreDocument() {
    var administrator: String by this
    var activities: FirestoreList<String> by this
    var members: FirestoreList<String> by this
}