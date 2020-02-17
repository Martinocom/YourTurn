package it.pabich.yourturn.fragments.createGroup


import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import it.pabich.yourturn.R
import kotlinx.android.synthetic.main.fragment_create_group_1.view.*


/**
 * A simple [Fragment] subclass.
 */
class FirstStep : Fragment() {

    private var edtGroupName: EditText? = null
    private lateinit var onProceedListener: FirstStepListener

    companion object {
        fun newInstance() : FirstStep {
            return FirstStep()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FirstStepListener) {
            onProceedListener = context
        } else {
            throw IllegalStateException("Activity must implement OnProceedListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_group_1, container, false)

        edtGroupName = view!!.fragment_first_step_edt_group_name
        edtGroupName!!.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                onAccept()
            }
            false
        }

        view.fragment_first_step_btn_next.setOnClickListener { onAccept() }

        return view
    }

    private fun onAccept() {
        if (edtGroupName?.text.isNullOrEmpty() || edtGroupName?.text.isNullOrBlank()) {
            edtGroupName?.startAnimation(AnimationUtils.loadAnimation(context!!, R.anim.shake))
            edtGroupName?.error = getText(R.string.group_name_error)
        } else {
            edtGroupName!!.error = null
            onProceedListener.onFirstStepNextPressed(edtGroupName!!.text.toString())
        }
    }

    interface FirstStepListener {
        fun onFirstStepNextPressed(groupName: String)
    }
}


