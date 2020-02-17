package it.pabich.yourturn.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.pabich.yourturn.R
import it.pabich.yourturn.helpers.inflate

class ActivityAdapter(private val groupActivities: List<Map<String, Any>>) : RecyclerView.Adapter<ActivityAdapter.ActivityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        // Takes an Extension from extension/Extensions.kt
        return ActivityHolder(parent.inflate(R.layout.list_item_my_activity, false))
    }

    override fun getItemCount(): Int {
        return groupActivities.size
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        holder.bindActivityData(groupActivities[position], position)
    }

    class ActivityHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var groupActivity: Map<String, Any> = emptyMap()

        fun bindActivityData(activity: Map<String, Any>, position: Int) {
            this.groupActivity = activity

            /*
            if (myActivity.lastCheck != null) {
                view.li_my_activity_image.setImageBitmap(myActivity.lastCheck.photo)
                view.li_my_activity_next_turn.text = myActivity.lastCheck.user.displayName
                view.li_my_activity_last_done_by.text = myActivity
            } else {

            }

            view.txv_item_score_number.text = (position + 1).toString()
            view.txv_item_score_accuracy.text = myActivity.accuracy.getStringFloatValue()
            view.txv_item_score_presentation.text = myActivity.presentation.getStringFloatValue()
            view.txv_item_score_total.text = myActivity.totalScore.getStringFloatValue()
            view.txv_item_score_date.text = myActivity.dateTime.getDateString()
            view.txv_item_score_time.text = myActivity.dateTime.getTimeString()*/
        }
    }

   /* private fun getStringFromResponse(nextEnchargedUserReturn: MyActivity.NextEnchargedUserReturn, nextTurnTextView: TextView, context: Context) : String {

        when(nextEnchargedUserReturn.nextEnchargedUserReturnType) {
            MyActivity.NextEnchargedUserReturnType.USER_FOUND -> { }
            MyActivity.NextEnchargedUserReturnType.LIST_EMPTY -> { }
            MyActivity.Ne
        }


        if (nextEnchargedUserReturn.nextEnchargedUserReturnType == MyActivity.NextEnchargedUserReturnType.USER_FOUND) {
            DataBase.getUserData(nextEnchargedUserReturn.enchargedUser!!.uid, {user ->
                nextTurnTextView.text = user.displayName
            }, {errors, s ->
                nextTurnTextView.text = context.getString(R.string.no_data)
            })
            return context.getString(R.string.loading)
        }

        if (nextEnchargedUserReturn.nextEnchargedUserReturnType == MyActivity.NextEnchargedUserReturnType.LIST_EMPTY)
            return context.getString(R.string.nobody_assigned_to_activity)

        if (nextEnchargedUserReturn.nextEnchargedUserReturnType == MyActivity.NextEnchargedUserReturnType.MORE_USERS_FOUND)
            return context.getString(R.string.more_than_one_user_found)
        return  ""
    }*/
}