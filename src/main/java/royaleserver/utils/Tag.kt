package royaleserver.utils

class Tag {
	private val TAG_CHARACTERS = "0289PYLQGRJCUV".toCharArray()
	private val id: Long

	constructor(id: Long) {
		this.id = id
	}

	constructor(tag: String) {
		var parsedId: Long = 0

		for (char in tag) {
			val index = TAG_CHARACTERS.indexOf(char)
			parsedId *= TAG_CHARACTERS.size
			parsedId += index
		}

		val high = parsedId % 256
		val low = (parsedId - high) ushr 8

		id = (high shl 32) or low
	}

	override fun toString(): String {
		val high = (id shr 32) and 0xFFFFFFFF
		val low = id and 0xFFFFFFFF

		var parsedId = (low shl 8) + high
		val sb: StringBuilder = StringBuilder()

		while (parsedId != 0L) {
			val index = (parsedId % TAG_CHARACTERS.size).toInt()
			sb.append(TAG_CHARACTERS[index])
			parsedId /= TAG_CHARACTERS.size
		}

		return sb.toString().reversed()
	}
}