package it.pabich.yourturn.db.dbmodel

import com.otaliastudios.firestore.FirestoreClass
import com.otaliastudios.firestore.FirestoreDocument
import com.otaliastudios.firestore.FirestoreList

@FirestoreClass
class DBActivity : FirestoreDocument() {
    var name: String by this
    var partOfGroup: String by this
    var checks: FirestoreList<String> by this
    var enchargedUsers: FirestoreList<String> by this
}