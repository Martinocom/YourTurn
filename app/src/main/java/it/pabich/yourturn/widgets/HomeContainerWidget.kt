package it.pabich.yourturn.widgets

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import it.pabich.yourturn.R
import kotlinx.android.synthetic.main.widget_home_container.view.*

class HomeContainerWidget<A: RecyclerView.ViewHolder>: LinearLayout {

    // Variables
    private var _title: String? = null
    private var _buttonContent: String? = null
    private var _isEditModeEnabled: Boolean = false
    private var _recyclerAdapter: RecyclerView.Adapter<A>? = null
    private var currentStatus: HomeContainerWidgetStatus = HomeContainerWidgetStatus.NORMAL

    // Getters and Setters
    var title: String?
        get() = _title
        set(value) {
            _title = value
            widget_home_title.text = _title
        }

    var buttonContent: String?
        get() = _buttonContent
        set(value) {
            _buttonContent = value
            widget_home_button_add.text = _buttonContent
        }

    var recyclerAdapter: RecyclerView.Adapter<A>?
        get() = _recyclerAdapter
        set(value) {
            _recyclerAdapter = value
            widget_home_items.adapter = _recyclerAdapter
            checkListVisibility()
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
        LayoutInflater.from(context).inflate(R.layout.widget_home_container, this, true)

        // Load attributes
        //val a = context.obtainStyledAttributes(attrs, R.styleable.HomeContainerWidget, defStyle, 0)

        // Assign attributes
        //title = a.getString(R.styleable.HomeContainerWidget_title)
        //buttonContent = a.getString(R.styleable.HomeContainerWidget_buttonContent)

        changeStatus(HomeContainerWidgetStatus.NORMAL)
        widget_home_button_edit.setOnClickListener {
            when (currentStatus) {
                HomeContainerWidgetStatus.NORMAL -> {
                    changeStatus(HomeContainerWidgetStatus.EDITING)
                }

                HomeContainerWidgetStatus.EDITING -> {
                    changeStatus(HomeContainerWidgetStatus.NORMAL)
                }
            }

        }

        // Check if button or items are visible
        checkListVisibility()

        // Recycle
        //a.recycle()
    }

    private fun changeStatus(homeContainerWidgetStatus: HomeContainerWidgetStatus) {
        when(homeContainerWidgetStatus) {
            HomeContainerWidgetStatus.NORMAL -> {
                widget_home_button_edit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_edit))
                widget_home_button_add.visibility = View.GONE
                checkListVisibility()
            }

            HomeContainerWidgetStatus.EDITING -> {
                widget_home_button_edit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check))
                widget_home_button_add.visibility = View.VISIBLE
                widget_home_empty_list.visibility = View.GONE
                widget_home_items.visibility = View.VISIBLE
            }
        }

        currentStatus = homeContainerWidgetStatus
    }

    enum class HomeContainerWidgetStatus {
        NORMAL, EDITING
    }

    private fun checkListVisibility() {
        if (recyclerAdapter != null) {
            if (recyclerAdapter!!.itemCount > 0) {
                //widget_home_button.visibility = View.GONE
                widget_home_empty_list.visibility = View.GONE
                widget_home_items.visibility = View.VISIBLE
            } else {
                //widget_home_button.visibility = View.VISIBLE
                widget_home_empty_list.visibility = View.VISIBLE
                widget_home_items.visibility = View.GONE
            }
        } else {
            //widget_home_button.visibility = View.VISIBLE
            widget_home_empty_list.visibility = View.VISIBLE
            widget_home_items.visibility = View.GONE
        }
    }
}