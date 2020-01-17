package it.pabich.yourturn.model

data class User(val mail: String,
                val displayName: String,
                val memberOf: List<Group> = emptyList(),
                val myActivities: List<MyActivity> = emptyList())
