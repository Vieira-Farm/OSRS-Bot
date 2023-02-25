package scripts.tutorial.data

import scripts.data.structures.PrivateSingleton
import scripts.tutorial.missions.TutorialIsland

class Variables(callingClass: Class<*>?) : PrivateSingleton(callingClass) {
    private var pickedGender = false
    @JvmField
    var username: String? = null
    override fun getAllowedClass(): Class<*> {
        return TutorialIsland::class.java
    }

    fun hasPickedGender(): Boolean {
        return pickedGender
    }

    fun setPickedGender(pickedGender: Boolean) {
        this.pickedGender = pickedGender
    }
}