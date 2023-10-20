data class Note(override val name: String, val text: String) : File {
    override fun toString(): String {
        return "$name: $text"
    }
}