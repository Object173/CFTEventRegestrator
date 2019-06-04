package com.object173.cfteventregestrator.feature.participant.domain.model

import java.util.*

data class Participant (
    var id: Long,
    var phone: String,
    var city: String,
    var company: String,
    var position: String,
    var addition: String,
    var registeredDate: Date,
    var isVisited: Boolean,
    var agreedByManager: String,
    var lastName: String,
    var firstName: String,
    var patronymic: String,
    var email: String
)