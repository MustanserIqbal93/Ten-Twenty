package com.tentwenty.assignment.dependencyInjection

import com.tentwenty.assignment.utils.DialogUtil
import com.tentwenty.assignment.utils.DialogUtilImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class DialogModule {

    @Binds
    abstract fun bindDialogModule(impl: DialogUtilImpl): DialogUtil
}