package it.pabich.yourturn.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.pabich.yourturn.R
import it.pabich.yourturn.helpers.inflate
import it.pabich.yourturn.model.User
import kotlinx.android.synthetic.main.list_item_group_selection.view.*

class GroupSelectionAdapter(private val groups: List<Map<String, Any>>) : RecyclerView.Adapter<GroupSelectionAdapter.ActivityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        // Takes an Extension from extension/Extensions.kt
        return ActivityHolder(parent.inflate(R.layout.list_item_group_selection, false))
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        holder.bindActivityData(groups[position], position)
    }

    class ActivityHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var group: Map<String, Any> = emptyMap()

        fun bindActivityData(activity: Map<String, Any>, position: Int) {
            this.group = activity

            view.li_group_selection_color.backgroundTintList = ColorStateList.valueOf(Color.parseColor(group[User.MemberOfKeys.GROUP_COLOR.name].toString()))
            view.li_group_selection_name.text = group[User.MemberOfKeys.GROUP_NAME.name].toString()
        }
    }
}