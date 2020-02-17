package it.pabich.yourturn.activities

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import it.pabich.yourturn.R
import it.pabich.yourturn.fragments.createGroup.FirstStep
import it.pabich.yourturn.fragments.createGroup.SecondStep
import it.pabich.yourturn.fragments.createGroup.ThirdStep
import it.pabich.yourturn.helpers.toDP
import it.pabich.yourturn.model.Group
import kotlinx.android.synthetic.main.activity_create_group.*


class CreateGroup : AuthenticatedActivity(), FirstStep.FirstStepListener, SecondStep.SecondStepListener {

    private val firstFragment = FirstStep.newInstance()
    private val secondFragment = SecondStep.newInstance()

    private var currentFragmentCount = 0
    private val group: Group = Group()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        activity_create_group_btn_back.setOnClickListener { onBackPressed() }
        goToNextStep(firstFragment)

        val animation = ObjectAnimator.ofFloat(activity_create_group_btn_back, "translationY", (0).toDP(this).toFloat())
        animation.duration = 500
        animation.start()
    }

    override fun onFirstStepNextPressed(groupName: String) {
        group.name = groupName
        goToNextStep(secondFragment)
    }

    override fun onSecondStepNextPressed(color: Int) {
        group.color = java.lang.String.format("#%06X", 0xFFFFFF and color)
        goToNextStep(ThirdStep.newInstance(group, color))
    }

    private fun goToNextStep(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.create_group_frame_layout, fragment)
            .addToBackStack(fragment::javaClass.name)
            .commit()

        currentFragmentCount++
    }

    override fun onBackPressed() {
        currentFragmentCount--

        when (currentFragmentCount) {
            0 -> finish()
            else -> super.onBackPressed()
        }
    }
}