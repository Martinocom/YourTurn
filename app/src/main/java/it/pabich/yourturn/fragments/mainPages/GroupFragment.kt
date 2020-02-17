package it.pabich.yourturn.fragments.mainPages

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import it.pabich.yourturn.R
import it.pabich.yourturn.activities.AuthenticatedActivity
import it.pabich.yourturn.activities.CreateGroup
import it.pabich.yourturn.db.Errors


class GroupFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_groups, container, false)
        setHasOptionsMenu(true)

        if (activity is AuthenticatedActivity) {
            with(activity as AuthenticatedActivity) {
                // Loading now!
                //switchStatus(root,
                 //   GroupsFragmentStatus.LOADING
                //)

                //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                // Set listeners
                //root!!.fragment_groups_btn_create_group.setOnClickListener {
                    //val intent = Intent(context!!, CreateGroup::class.java)
                    //startActivity(intent)
                //}

                /*root.fragment_activities_rtl_group_selection.setOnClickListener {
                    val intent = Intent(this, GroupSelectionActivity::class.java)
                    startActivity(intent)
                }*/

                // Check if some group was opened before
                /*val lastOpenedGroup = AppPreferences.getInstance(context!!).lastOpenedGroup
                if (lastOpenedGroup != null && lastOpenedGroup.isNotEmpty()) {
                    this.launchAuthenticated {
                        GlobalScope.launch {
                            val activities = DataBase.getActivitiesOfGroup(lastOpenedGroup)

                            if (activities.isNullOrEmpty()) {
                                switchStatus(root,
                                    ActivitiesFragmentStatus.NO_ACTIVITIES
                                )
                            } else {
                                root.fragment_activities_rcv_activities.layoutManager = LinearLayoutManager(context!!)
                                root.fragment_activities_rcv_activities.adapter = ActivityAdapter(activities)
                                switchStatus(root,
                                    ActivitiesFragmentStatus.ACTIVITIES_PRESENT
                                )
                            }
                        }
                    }
                } else {
                    switchStatus(root,
                        ActivitiesFragmentStatus.NO_ACTIVITIES
                    )
                }*/
            }
        }

        return root
    }

    private fun switchStatus(root: View, activitiesFragmentStatus: GroupsFragmentStatus, error: Errors? = null, message: String? = null) {
        when (activitiesFragmentStatus) {
            GroupsFragmentStatus.LOADING -> {
                /*root.fragment_activities_fab_add.visibility = View.INVISIBLE
                root.fragment_activities_rcv_activities.visibility = View.INVISIBLE
                root.fragment_activities_lnl_no_activities.visibility = View.GONE
                root.fragment_activities_rtl_loading.visibility = View.VISIBLE
                root.fragment_activities_txv_error_title.visibility = View.GONE
                root.fragment_activities_txv_error_text.visibility = View.GONE*/
            }

            GroupsFragmentStatus.NO_ACTIVITIES -> {
                /*root.fragment_activities_fab_add.visibility = View.INVISIBLE
                root.fragment_activities_rcv_activities.visibility = View.INVISIBLE
                root.fragment_activities_lnl_no_activities.visibility = View.VISIBLE
                root.fragment_activities_rtl_loading.visibility = View.GONE

                if (error != null || message != null) {
                    root.fragment_activities_txv_error_title.visibility = View.VISIBLE
                    root.fragment_activities_txv_error_text.visibility = View.VISIBLE
                    root.fragment_activities_txv_error_text.text = "$error: $message"
                } else {
                    root.fragment_activities_txv_error_title.visibility = View.GONE
                    root.fragment_activities_txv_error_text.visibility = View.GONE
                }*/
            }

            GroupsFragmentStatus.ACTIVITIES_PRESENT -> {
                /*root.fragment_activities_fab_add.visibility = View.VISIBLE
                root.fragment_activities_rcv_activities.visibility = View.VISIBLE
                root.fragment_activities_lnl_no_activities.visibility = View.GONE
                root.fragment_activities_rtl_loading.visibility = View.GONE
                root.fragment_activities_txv_error_title.visibility = View.GONE
                root.fragment_activities_txv_error_text.visibility = View.GONE*/
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_groups, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_groups_add_group -> {
                val intent = Intent(context!!, CreateGroup::class.java)
                startActivity(intent)
            }
            R.id.toolbar_groups_join_group -> {
                Toast.makeText(context!!, "Join!", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }



    enum class GroupsFragmentStatus {
        LOADING, NO_ACTIVITIES, ACTIVITIES_PRESENT
    }
}