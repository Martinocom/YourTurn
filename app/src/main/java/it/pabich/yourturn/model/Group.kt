package it.pabich.yourturn.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Group() : Parcelable {
    var name: String = ""
    var color: String = ""
    var administrators: MutableList<String> = mutableListOf()
    var activities: MutableList<Map<String, Any>> = mutableListOf()
    var members: MutableList<Map<String, Any>> = mutableListOf()

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        color = parcel.readString()
        administrators = parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(color)
        parcel.writeStringList(administrators)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Group> {
        override fun createFromParcel(parcel: Parcel): Group {
            return Group(parcel)
        }

        override fun newArray(size: Int): Array<Group?> {
            return arrayOfNulls(size)
        }
    }

    enum class ActivityKeys(name: String) {
        ACTIVITY_ID("activityID"),
        ACTIVITY_NAME("activityName"),
        DATE_OF_PREVIOUS_USER("dateOfPreviousUser"),
        NAME_OF_NEXT_USER("nameOfNextUser"),
        NAME_OF_PREVIOUS_USER("nameOfPreviousUser"),
        PHOTO_OF_PREVIOUS_USER("photoOfPreviousUser");
    }

    enum class MembersKeys(name: String) : Serializable {
        MEMBER_ID("memberID"),
        MEMBER_NAME("memberName");
    }
}