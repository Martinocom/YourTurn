package it.pabich.yourturn.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import it.pabich.yourturn.R
import it.pabich.yourturn.adapters.GroupSelectionAdapter
import it.pabich.yourturn.db.DataBase
import it.pabich.yourturn.model.User
import kotlinx.android.synthetic.main.activity_group_selection.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroupSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_selection)

        window.setBackgroundDrawableResource(android.R.color.transparent)

        // Start load groups
        activity_group_selection_rcv_data.visibility = View.GONE
        activity_group_selection_lnl_no_groups.visibility = View.GONE
        activity_group_selection_lnl_loading.visibility = View.VISIBLE

        GlobalScope.launch {
            if (DataBase.checkConnection() == User.UserStatus.AUTHENTICATED) {
                val user = DataBase.getUserData(FirebaseAuth.getInstance().currentUser!!.uid)

                if (user != null) {
                    if (user.memberOf.isEmpty()) {
                        runOnUiThread {
                            activity_group_selection_rcv_data.visibility = View.GONE
                            activity_group_selection_lnl_no_groups.visibility = View.VISIBLE
                            activity_group_selection_lnl_loading.visibility = View.GONE
                        }
                    } else {
                        runOnUiThread {
                            activity_group_selection_rcv_data.layoutManager = LinearLayoutManager(this@GroupSelectionActivity)
                            activity_group_selection_rcv_data.adapter = GroupSelectionAdapter(user.memberOf)
                            activity_group_selection_rcv_data.visibility = View.VISIBLE
                            activity_group_selection_lnl_no_groups.visibility = View.GONE
                            activity_group_selection_lnl_loading.visibility = View.GONE
                        }
                    }
                }
            }
        }


    }
}
