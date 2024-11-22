package graphql.schema

class Human(
    id: String,
    name: String,
    friends: List<String>,
    appearsIn: List<Int>,
    secretBackstory: String?,
    val homePlanet: String?,
) : Character(
    id,
    name,
    friends,
    appearsIn,
    secretBackstory
)
