package it.pabich.yourturn.fragments.mainPages

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import it.pabich.yourturn.R
import it.pabich.yourturn.db.DataBase
import it.pabich.yourturn.model.User
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private var user: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        switchStatus(HomeFragmentStatus.LOADING)

        GlobalScope.launch {
            getUserDataAndSetView(root)
        }

        return root
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }*/

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    //putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_home_swap -> {
                switchStatus(HomeFragmentStatus.LOADING)

                FirebaseAuth.getInstance().currentUser?.let {firebaseUser ->
                    user?.let { myUser ->
                        myUser.homePrefs[User.HomePrefsKeys.IS_ACTIVITIES_FIRST.value] = !(myUser.homePrefs[User.HomePrefsKeys.IS_ACTIVITIES_FIRST.value] as Boolean)
                        DataBase.setUserHomePref(
                            firebaseUser.uid,
                            myUser.homePrefs,
                            {
                                GlobalScope.launch {
                                    getUserDataAndSetView(view!!)
                                }
                            },
                            {_, str ->
                                view?.let {
                                    Snackbar.make(it, "Cannot change preferences: $str", Snackbar.LENGTH_SHORT)
                                    switchStatus(HomeFragmentStatus.NORMAL)
                                }

                            }
                        )
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun switchStatus(homeFragmentStatus: HomeFragmentStatus) {
        when (homeFragmentStatus) {
            HomeFragmentStatus.NORMAL -> {
                view?.let {
                    it.fragment_home_list.visibility = View.VISIBLE
                    it.fragment_home_loading.visibility = View.GONE
                }
            }

            HomeFragmentStatus.LOADING -> {
                view?.let {
                    it.fragment_home_list.visibility = View.GONE
                    it.fragment_home_loading.visibility = View.VISIBLE
                }
            }
            HomeFragmentStatus.EDIT -> {

            }
        }
    }

    enum class HomeFragmentStatus {
        LOADING, EDIT, NORMAL
    }

    private suspend fun getUserDataAndSetView(root: View) {
        user = DataBase.getUserData(FirebaseAuth.getInstance().currentUser!!.uid)
        user?.let {
            (it.homePrefs[User.HomePrefsKeys.IS_ACTIVITIES_FIRST.value] as Boolean?)?.let {isActivitiesFirst ->
                if (isActivitiesFirst) {
                    activity?.runOnUiThread {
                        root.fragment_home_container_1.title = getString(R.string.activities)
                        root.fragment_home_container_1.buttonContent = getString(R.string.add_an_activity)
                        root.fragment_home_container_2.title = getString(R.string.groups)
                        root.fragment_home_container_2.buttonContent = getString(R.string.add_a_group)
                        switchStatus(HomeFragmentStatus.NORMAL)
                    }
                } else {
                    activity?.runOnUiThread {
                        root.fragment_home_container_1.title = getString(R.string.groups)
                        root.fragment_home_container_2.title = getString(R.string.activities)
                        switchStatus(HomeFragmentStatus.NORMAL)
                    }
                }
            }
        }
    }
}
