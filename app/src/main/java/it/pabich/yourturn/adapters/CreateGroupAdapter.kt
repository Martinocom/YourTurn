package it.pabich.yourturn.adapters

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import it.pabich.yourturn.R


class CreateGroupAdapter (fm: FragmentManager, context: Context) : AbstractFragmentStepAdapter(fm, context) {

    private val stepFragments = listOf<Step>(
        //FirstStep.newInstance(),
        //SecondStep.newInstance(),
        //ThirdStep.newInstance()
    )

    override fun getViewModel(position: Int): StepViewModel {
        val builder = StepViewModel.Builder(context)
        builder.setBackButtonVisible(true)
        builder.setBackButtonLabel(context.getText(R.string.cancel))
        builder.setBackButtonStartDrawableResId(R.drawable.ic_cancel)

        when (position) {
            0 -> {
                builder.setTitle(R.string.first_step_title)
                builder.setSubtitle(R.string.first_step_subtitle)
            }
            1 -> {
                builder.setTitle(R.string.second_step_title)
                builder.setSubtitle(R.string.second_step_subtitle)
            }
            2 -> {
                builder.setTitle(R.string.third_step_title)
                builder.setSubtitle(R.string.third_step_subtitle)
            }
        }

        return builder.create()
    }

    override fun createStep(position: Int): Step {
        if (position < stepFragments.size)
            return stepFragments[position]
        else
            throw IllegalArgumentException("Unsupported position: " + position)
    }

    override fun getCount(): Int {
        return stepFragments.size
    }
}