package it.pabich.yourturn.fragments.mainPages

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import it.pabich.yourturn.R
import it.pabich.yourturn.activities.AuthenticatedActivity

class ManageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_manage, container, false)
        setHasOptionsMenu(true)

        if (activity is AuthenticatedActivity) {
            with(activity as AuthenticatedActivity) {

            }
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_manage_manage -> {
                Toast.makeText(context!!, "¯\\_(ツ)_/¯", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}