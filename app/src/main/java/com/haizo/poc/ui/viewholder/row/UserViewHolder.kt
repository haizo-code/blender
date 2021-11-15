package com.haizo.poc.ui.viewholder.row

import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.BR
import com.haizo.poc.callbacks.UserActionCallback
import com.haizo.poc.databinding.RowUserCardBinding
import com.haizo.poc.model.User

class UserViewHolder constructor(
    private val binding: RowUserCardBinding,
    actionCallback: UserActionCallback?
) : BaseBindingViewHolder<User>(binding, actionCallback) {

    init {
        binding.ivProfile.setOnClickListener {
            actionCallback?.onAvatarClicked(listItem)
        }

        binding.btnCall.setOnClickListener {
            actionCallback?.onCallClicked(listItem)
        }
    }

    override fun onBind(listItem: User) {
        binding.setVariable(BR.model, listItem)
        binding.executePendingBindings()
    }
}