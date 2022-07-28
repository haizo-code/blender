package com.haizo.sample.callbacks

import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.sample.model.User

interface UserActionCallback : BaseActionCallback {
    fun onAvatarClicked(user: User)
    fun onCallClicked(user: User)
}