package com.object173.cfteventregestrator.di

import com.object173.cfteventregestrator.feature.event.di.EvenFragmentModule
import com.object173.cfteventregestrator.feature.event.presentation.EventListFragment
import com.object173.cfteventregestrator.feature.participant.di.ParticipantFragmentModule
import com.object173.cfteventregestrator.feature.participant.item.presentation.ParticipantItemFragment
import com.object173.cfteventregestrator.feature.participant.list.presentation.ParticipantListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [EvenFragmentModule::class, ParticipantFragmentModule::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun eventListFragment() : EventListFragment

    @ContributesAndroidInjector
    abstract fun participantListFragment() : ParticipantListFragment

    @ContributesAndroidInjector
    abstract fun participantItemFragment() : ParticipantItemFragment
}