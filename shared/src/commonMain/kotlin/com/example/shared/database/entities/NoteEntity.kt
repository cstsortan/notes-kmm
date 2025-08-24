package com.example.shared.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@Entity(tableName = "notes")
@ObjCName("FruittieEntityObjc")
data class NoteEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
)
