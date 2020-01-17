package it.pabich.yourturn.db.dbmodel

import com.otaliastudios.firestore.FirestoreClass
import com.otaliastudios.firestore.FirestoreDocument

@FirestoreClass
class DBActivityCheck : FirestoreDocument() {
    var user: String by this
    var signedBy: String by this
    var dateOfSignal: String by this
    var date: String by this
    var photo: String by this
}