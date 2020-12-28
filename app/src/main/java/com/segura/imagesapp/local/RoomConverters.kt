package com.segura.imagesapp.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.segura.imagesapp.model.Urls
import com.segura.imagesapp.model.User

class RoomConverters {

    companion object {
        private val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun urlsToString(urls: Urls?): String {
            return gson.toJson(urls)
        }

        @TypeConverter
        @JvmStatic
        fun userToJson(user: User): String {
            return gson.toJson(user)
        }

        @TypeConverter
        @JvmStatic
        fun jsonToToURL(string: String?): Urls {
            return gson.fromJson(string, Urls::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun jsonToUser(string: String?): User {
            return gson.fromJson(string, User::class.java)
        }

    }
}