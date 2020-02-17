package it.pabich.yourturn.widgets

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import it.pabich.yourturn.R
import kotlinx.android.synthetic.main.list_item_my_activity.view.*


class ActivitySample: RelativeLayout {

    // Variables
    private var _title: String? = null
    private var _nextUser: String? = null
    private var _previousUser: String? = null
    private var _previousUserDate: String? = null
    private var _buttonColor: Int? = null

    // Getters and Setters
    var title: String?
        get() = _title
        set(value) {
            _title = value
            li_my_activity_title.text = _title
        }

    var nextUser: String?
        get() = _nextUser
        set(value) {
            _nextUser = value
            li_my_activity_next_turn.text = _nextUser
        }

    var previousUser: String?
        get() = _previousUser
        set(value) {
            _previousUser = value
            li_my_activity_last_done_by.text = _previousUser
        }

    var previousUserDate: String?
        get() = _previousUserDate
        set(value) {
            _previousUserDate = value
            li_my_activity_last_done_by_date.text = _previousUserDate
        }

    var buttonColor: Int?
        get() = _buttonColor
        set(value) {
            _buttonColor = value
            if (_buttonColor != null && _buttonColor != -1) {
                //li_my_activity_add_button.backgroundTintList = ColorStateList.valueOf(_buttonColor!!)
                //li_my_activity_last_done_by.setTextColor(_buttonColor!!)
                //li_my_activity_last_done_by_date.setTextColor(_buttonColor!!)
                li_my_activity_details_arrow.backgroundTintList = ColorStateList.valueOf(_buttonColor!!)
            }
            else {
                //li_my_activity_add_button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimary))
                //li_my_activity_last_done_by.setTextColor(ContextCompat.getColor(context, R.color.ms_white))
                //li_my_activity_last_done_by_date.setTextColor(ContextCompat.getColor(context, R.color.ms_white))
                li_my_activity_details_arrow.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimary))
            }
        }


    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.list_item_my_activity, this, true)

        // Load attributes
        val a = context.obtainStyledAttributes(attrs, R.styleable.ActivitySample, defStyle, 0)

        // Assign attributes
        title = a.getString(R.styleable.ActivitySample_title)
        nextUser = a.getString(R.styleable.ActivitySample_nextUser)
        previousUser = a.getString(R.styleable.ActivitySample_previousUser)
        previousUserDate = a.getString(R.styleable.ActivitySample_previousUserDate)
        buttonColor = a.getInt(R.styleable.ActivitySample_buttonColor, -1)

        // Recycle
        a.recycle()
    }
}