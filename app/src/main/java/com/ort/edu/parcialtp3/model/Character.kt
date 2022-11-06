package com.ort.edu.parcialtp3.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL


@Entity
data class CharacterDB(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String?,
    @ColumnInfo val status: String?,
    @ColumnInfo val image: String?,
    @ColumnInfo val species: String?,
    @ColumnInfo val origin: String?

)

data class Origin(val name: String?, val url: String?)

data class Character(val id: Int, val name: String?, val status: String?, val image: String?, val species: String?, val origin: Origin?) :
Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("origin")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(image)
        parcel.writeString(species)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}
// data class Character (val id: Int, val name: String, val status: String, val species: String, val type: String, val gender: String, val image: String)

//id	int	The id of the character.
//name	string	The name of the character.
//status	string	The status of the character ('Alive', 'Dead' or 'unknown').
//url	string (url)	Link to the character's own URL endpoint.

//species	string	The species of the character.
//type	string	The type or subspecies of the character.
//gender	string	The gender of the character ('Female', 'Male', 'Genderless' or 'unknown').
//origin	object	Name and link to the character's origin location.
//location	object	Name and link to the character's last known location endpoint.
//image	string (url)	Link to the character's image. All images are 300x300px and most are medium shots or portraits since they are intended to be used as avatars.
//episode	array (urls)	List of episodes in which this character appeared.
//created	string	Time at which the character was created in the database.