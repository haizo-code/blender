package com.haizo.sample.ui.viewholder.row

import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.sample.BR
import com.haizo.sample.callbacks.UserActionCallback
import com.haizo.sample.databinding.RowUserCardBinding
import com.haizo.sample.model.User

class UserViewHolder constructor(
    private val binding: RowUserCardBinding,
    actionCallback: UserActionCallback?
) : BaseBindingViewHolder<User>(binding, actionCallback) {

    init {
        binding.ivProfile.setOnClickListener { actionCallback?.onAvatarClicked(listItem) }
        binding.btnCall.setOnClickListener { actionCallback?.onCallClicked(listItem) }
    }

    override fun onBind(listItem: User) {
        binding.setVariable(BR.model, listItem)
        binding.executePendingBindings()
    }
}