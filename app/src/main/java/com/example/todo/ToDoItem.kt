package com.example.todo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ToDoItem(

    @SerializedName("id")
    @Expose
    var id: String,
    @Expose
    @SerializedName("importance")
    var importance: String,
    @Expose
    @SerializedName("done")
    var completed: Boolean,
    @Expose
    @SerializedName("text")
    var description: String,
    @Expose(serialize = false,deserialize = false)
    var deadline: Long?,
    @Expose
    @SerializedName("created_at")
    val created_at: Long = 0,
    @Expose
    @SerializedName("changed_at")
    var changed_at: Long? = 0,
    @Expose
    @SerializedName("last_updated_by")
    var last_updated_by: String = "MIIV"
)

data class ToDoItemTest(
    @SerializedName("id")
    var id: String,
    @SerializedName("importance")
    var importance: String,
    @SerializedName("done")
    var completed: Boolean,
    @SerializedName("text")
    var description: String,
    @SerializedName("created_at")
    val created_at: Long,
    @SerializedName("changed_at")
    var changed_at: Long?,
    @SerializedName("last_updated_by")
    var last_updated_by: String = "MIIV"
)

