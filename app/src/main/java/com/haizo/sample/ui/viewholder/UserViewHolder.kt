package com.haizo.sample.ui.viewholder

import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.sample.BR
import com.haizo.sample.R
import com.haizo.sample.callbacks.UserActionCallback
import com.haizo.sample.databinding.RowUserCardBinding
import com.haizo.sample.model.User

//####################################################//
//#################### Contract ######################//
//####################################################//

val USER_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = UserViewHolder::class.java,
    layoutResId = R.layout.row_user_card,
    itemName = "ITEM_USER_CARD",
    UserActionCallback::class.java
)

//####################################################//
//################## ViewHolder ######################//
//####################################################//

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
    }
}