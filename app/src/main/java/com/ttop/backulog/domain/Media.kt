package com.ttop.backulog.domain

sealed class Media(open val titre: String, open val etat: Status) {
    data class Jeu(override val titre: String, override val etat: Status): Media(titre, etat)
    data class Film(override val titre: String, override val etat: Status): Media(titre, etat)
}

enum class Status {
    PLANNED, ONGOING, FINISHED
}

enum class Category {
    JEU, FILM
}