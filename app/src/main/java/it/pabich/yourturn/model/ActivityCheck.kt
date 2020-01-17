package it.pabich.yourturn.model

import android.graphics.Bitmap
import java.util.*

class ActivityCheck(val date: Calendar, val user: EnchargedUser, val photo: Bitmap, missedParams: ActivityCheckMissedParams) { }