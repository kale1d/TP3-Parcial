package com.ort.edu.parcialtp3.model

data class Character (val id: Int, val name: String, val status: String, val species: String, val type: String, val gender: String, val image: String)

//id	int	The id of the character.
//name	string	The name of the character.
//status	string	The status of the character ('Alive', 'Dead' or 'unknown').
//species	string	The species of the character.
//type	string	The type or subspecies of the character.
//gender	string	The gender of the character ('Female', 'Male', 'Genderless' or 'unknown').
//origin	object	Name and link to the character's origin location.
//location	object	Name and link to the character's last known location endpoint.
//image	string (url)	Link to the character's image. All images are 300x300px and most are medium shots or portraits since they are intended to be used as avatars.
//episode	array (urls)	List of episodes in which this character appeared.
//url	string (url)	Link to the character's own URL endpoint.
//created	string	Time at which the character was created in the database.