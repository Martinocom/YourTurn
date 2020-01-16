package it.pabich.yourturn.activities.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import it.pabich.yourturn.R

class ManageFragment : Fragment() {

    private lateinit var manageViewModel: ManageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manageViewModel =
            ViewModelProviders.of(this).get(ManageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_manage, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        manageViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}