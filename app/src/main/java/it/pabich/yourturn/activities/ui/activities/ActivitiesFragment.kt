package it.pabich.yourturn.activities.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import it.pabich.yourturn.R
import it.pabich.yourturn.db.DataBase
import it.pabich.yourturn.helpers.AppPreferences

class ActivitiesFragment : Fragment() {

    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_activities, container, false)
        //activitiesViewModel = ViewModelProviders.of(this).get(ActivitiesViewModel::class.java)
        //val textView: TextView = root.findViewById(R.id.fragment_activities_txv_group)
        //activitiesViewModel.groupName.observe(this, Observer { textView.text = it })

        /*
        if (AppPreferences.getInstance(context!!).lastOpenedGroup != null) {
            DataBase.getActivitiesOfGroup(
                currentUser!!.uid,
                AppPreferences.getInstance(context!!).lastOpenedGroup!!,
                { activities ->

                }, { errors, s ->

                })
        }*/

        return root
    }
}