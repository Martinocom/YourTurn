package it.pabich.yourturn.fragments.createGroup


import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import it.pabich.yourturn.R
import it.pabich.yourturn.helpers.toDP
import it.pabich.yourturn.widgets.ActivitySample
import kotlinx.android.synthetic.main.fragment_create_group_2.view.*


/**
 * A simple [Fragment] subclass.
 */
class SecondStep : Fragment() {

    private var activitySample: ActivitySample? = null
    private var currentSelectedButton: Button? = null
    private var currentSelectedColor: Int? = null

    private lateinit var onProceedListener: SecondStepListener

    companion object {
        fun newInstance() : SecondStep {
            return SecondStep()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SecondStepListener) {
            onProceedListener = context
        } else {
            throw IllegalStateException("Activity must implement OnProceedListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_group_2, container, false)

        activitySample = view.fragment_second_activity_sample
        val colorContainer = view.fragment_second_color_container

        val colors = resources.getIntArray(R.array.colors)
        var isFirst = true
        colors.forEach { color ->
            //val buttonContainer = LinearLayout(context)
            val button = Button(context)

            if (isFirst) {
                val viewParams = LinearLayout.LayoutParams(50.toDP(context!!), 50.toDP(context!!))
                viewParams.setMargins(16, 8, 16, 8)
                button.layoutParams =  viewParams
                currentSelectedButton = button
                currentSelectedColor = color
                isFirst = false
            } else {
                val viewParams = LinearLayout.LayoutParams(40.toDP(context!!), 40.toDP(context!!))
                viewParams.setMargins(16, 8, 16, 8)
                button.layoutParams = viewParams

            }

            button.background = ContextCompat.getDrawable(context!!, R.drawable.classic_button_white)
            button.backgroundTintList = ColorStateList.valueOf(color)

            button.setOnClickListener {
                if (currentSelectedButton != null)
                    reduceButton(currentSelectedButton!!)
                increaseButton(it as Button)
                currentSelectedButton = it
                currentSelectedColor = color
                activitySample?.buttonColor = color

                println(color)
                println(ColorStateList.valueOf(color))
                println(color.toString())
                println(ColorStateList.valueOf(color).defaultColor)
                println(ColorStateList.valueOf(color).toString())
                println(ColorStateList.valueOf(color).defaultColor.toString())
            }

            /*val viewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            viewParams.setMargins(16,8,16,8)
            buttonContainer.layoutParams = viewParams
            buttonContainer.setPadding(3, 3, 3, 3)
            buttonContainer.background = ContextCompat.getDrawable(context!!, R.drawable.classic_button_white)
            buttonContainer.addView(button)

            colorContainer.addView(buttonContainer)*/
            colorContainer.addView(button)
        }

        view.fragment_second_flt_colors.postDelayed({ view.fragment_second_flt_colors.fullScroll(HorizontalScrollView.FOCUS_RIGHT) }, 300L)
        view.fragment_second_flt_colors.postDelayed({ view.fragment_second_flt_colors.fullScroll(HorizontalScrollView.FOCUS_LEFT) }, 300L)

        view.fragment_second_step_btn_next.setOnClickListener {
            onProceedListener.onSecondStepNextPressed(currentSelectedColor!!)
        }

        return view
    }

    interface SecondStepListener {
        fun onSecondStepNextPressed(color: Int)
    }

    private fun reduceButton(v: Button) {
        val anim = ValueAnimator.ofInt(v.measuredWidth, 40.toDP(context!!))
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams: LinearLayout.LayoutParams = v.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = `val`
            layoutParams.height = `val`
            v.layoutParams = layoutParams
        }
        anim.duration = 70
        anim.start()
    }

    private fun increaseButton(v: Button) {
        val anim = ValueAnimator.ofInt(v.measuredWidth, 50.toDP(context!!))
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams: LinearLayout.LayoutParams = v.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = `val`
            layoutParams.height = `val`
            v.layoutParams = layoutParams
        }
        anim.duration = 70
        anim.start()
    }

}
