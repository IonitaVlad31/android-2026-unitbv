package cst.unitbvfmi2026.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class AddressWithUsers(
    @Embedded val address: AddressEntity,//propr principala embeded
    @Relation(
        parentColumn = AddressEntity.ARG_ID,
        entityColumn = UserEntity.ARG_ADDRESS_ID //ne-am asigurat ca odata modif numele din db nu se strica relatia dintre cele 2
    )//pt a aduce toti userii prin primary key
    val users: List<UserEntity>
)
