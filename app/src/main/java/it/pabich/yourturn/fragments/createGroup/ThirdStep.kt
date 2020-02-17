package it.pabich.yourturn.fragments.createGroup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import it.pabich.yourturn.R
import it.pabich.yourturn.db.DataBase
import it.pabich.yourturn.model.Group
import it.pabich.yourturn.model.User
import kotlinx.android.synthetic.main.fragment_create_group_3.view.*

/**
 * A simple [Fragment] subclass.
 */
class ThirdStep : Fragment() {

    private var group: Group? = null
    private var color: Int? = null

    companion object {

        private const val GROUP = "group"
        private const val GROUP_COLOR = "groupColor"

        fun newInstance(group: Group, color: Int): ThirdStep {
            val fragment = ThirdStep()

            val bundle = Bundle().apply {
                putParcelable(GROUP, group)
                putInt(GROUP_COLOR, color)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_group_3, container, false)

        // Create the group
        with(arguments) {
            this?.getParcelable<Group>(GROUP)?.let { group = it }
            this?.getInt(GROUP_COLOR)?.let { color = it }
        }

        // Preview all
        if (group != null) {
            view.fragment_third_step_group_title.text = group!!.name
        }
        if (color != null) {
            view.fragment_third_activity_sample.buttonColor = color
        }

        // Ok, now it's ready
        switchStatus(view, ThirdStepStatuses.READY)

        // Create group button
        view.fragment_third_step_btn_create.setOnClickListener {
            it.isEnabled = false
            switchStatus(view, ThirdStepStatuses.LOADING)

            if (group != null) {
                // Prepare the group
                if (DataBase.checkConnection() == User.UserStatus.AUTHENTICATED) {
                    group!!.administrators.add(FirebaseAuth.getInstance().currentUser!!.uid)
                    group!!.members.add(mapOf(
                        Group.MembersKeys.MEMBER_ID.name to FirebaseAuth.getInstance().currentUser!!.uid,
                        Group.MembersKeys.MEMBER_NAME.name to FirebaseAuth.getInstance().currentUser!!.displayName!!)
                    )
                }
                // Create and send data
                DataBase.createGroup(group!!, { documentRef ->
                    DataBase.addGroupToUser(
                        mapOf(
                            User.MemberOfKeys.GROUP_NAME.name to group!!.name,
                            User.MemberOfKeys.GROUP_ID.name to documentRef,
                            User.MemberOfKeys.GROUP_COLOR.name to group!!.color),
                        FirebaseAuth.getInstance().currentUser!!.uid, {
                        // Group created successfully
                        // ¯\_(ツ)_/¯
                    }, { error, s ->
                        // Error when adding user to the group
                        switchStatus(view, ThirdStepStatuses.ERROR, "${error.name}: $s" )

                        // Rollback group creation
                        DataBase.deleteGroup(documentRef.id, {}, {_, _ ->} )
                    })
                }, { error, s ->
                    // Error when creating group
                    activity!!.runOnUiThread {
                        switchStatus(view, ThirdStepStatuses.ERROR, "${error.name}: $s" )
                    }
                })
            }
        }

        return view
    }

    private fun switchStatus(view: View, thirdStepStatus: ThirdStepStatuses, errorMessage: String? = null) {
        when(thirdStepStatus) {
            ThirdStepStatuses.READY -> {
                view.fragment_third_step_btn_create.setOnClickListener {
                    switchStatus(view, ThirdStepStatuses.LOADING)
                }
                view.fragment_third_step_btn_create_content.visibility = View.VISIBLE
                view.fragment_third_step_lnl_loading.visibility = View.GONE
                view.fragment_third_step_txv_error.visibility = View.INVISIBLE

            }

            ThirdStepStatuses.LOADING -> {
                view.fragment_third_step_btn_create.setOnClickListener {}
                view.fragment_third_step_btn_create_content.visibility = View.GONE
                view.fragment_third_step_lnl_loading.visibility = View.VISIBLE
                view.fragment_third_step_txv_error.visibility = View.INVISIBLE
            }

            ThirdStepStatuses.ERROR -> {
                view.fragment_third_step_btn_create.setOnClickListener {
                    switchStatus(view, ThirdStepStatuses.LOADING)
                }
                view.fragment_third_step_btn_create_content.visibility = View.VISIBLE
                view.fragment_third_step_lnl_loading.visibility = View.GONE
                view.fragment_third_step_txv_error.visibility = View.VISIBLE
                view.fragment_third_step_txv_error.text = errorMessage
            }
        }
    }

    enum class ThirdStepStatuses {
        READY, LOADING, ERROR
    }
}
