package com.haizo.poc.ui.viewholder.row

import android.widget.Toast
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.BR
import com.haizo.poc.databinding.RowUserCardBinding
import com.haizo.poc.model.UserCardModel
import com.haizo.poc.ui.screen.main.MainViewModel
import com.haizo.poc.ui.screen.main.MyActions

class UserCardViewHolder(private val viewDataBinding: RowUserCardBinding, actionCallback: MyActions?, val viewModel: MainViewModel) :
    BaseBindingViewHolder<UserCardModel>(viewDataBinding, actionCallback) {

    init {
        attachClickListener(itemView, viewDataBinding.ivProfile, viewDataBinding.tvName)

        viewDataBinding.ivProfile.setOnClickListener {
            actionCallback?.myAction1()
        }
        viewDataBinding.tvName.setOnClickListener {
            actionCallback?.myAction2()
        }
    }

    override fun onBackwardAction(vararg args: Any) {
        super.onBackwardAction(*args)
        Toast.makeText(context, "onBackwardAction: ${args.joinToString()}", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(listItem: UserCardModel) {
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}